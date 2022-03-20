// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.WPI_PigeonIMU;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterTalonFX;
import frc.robot.MecanumDriveInfo;
import frc.robot.PIDSettings;

public class DriveSubsystem extends SubsystemBase {
  private Gyro gyro = new WPI_PigeonIMU(1);

  private BetterTalonFX frontLeft = new BetterTalonFX(30);
  private BetterTalonFX frontRight = new BetterTalonFX(31);
  private BetterTalonFX backLeft = new BetterTalonFX(32);
  private BetterTalonFX backRight = new BetterTalonFX(33);

  private final MecanumDriveKinematics kinematics = new MecanumDriveKinematics(
    // In meters from center of robot
    new Translation2d(0.26035, 0.2921),
    new Translation2d(0.26035, -0.2921),
    new Translation2d(-0.26035, 0.2921),
    new Translation2d(-0.26035, -0.2921)
  );

  private MecanumDriveOdometry odometry;

  private static final double wheelDiameter = 0.2032; // meters
  private static final double wheelCircumference = Math.PI * wheelDiameter;
  private static final double gearRatio = 8.45 / 1.0;
  private static final double rotationsToMeters = wheelCircumference / gearRatio;

  // Numbers found with sysid
  private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0.67281, 1.651, 0.20761);
  private final PIDSettings feedforwardPID = new PIDSettings(2.2101, 0, 0);

  public DriveSubsystem() {
    frontLeft.configureFeedforward(feedforward, feedforwardPID);
    frontRight.configureFeedforward(feedforward, feedforwardPID);
    backLeft.configureFeedforward(feedforward, feedforwardPID);
    backRight.configureFeedforward(feedforward, feedforwardPID);

    frontLeft.configureUnitsPerRevolution(rotationsToMeters);
    frontRight.configureUnitsPerRevolution(rotationsToMeters);
    backLeft.configureUnitsPerRevolution(rotationsToMeters);
    backRight.configureUnitsPerRevolution(rotationsToMeters);

    frontRight.configureInverted(true);
    backRight.configureInverted(true);

    odometry = new MecanumDriveOdometry(kinematics, getRotation2d());
    reset();
  }

  public MecanumDriveKinematics getKinematics() {
    return kinematics;
  }

  public SimpleMotorFeedforward getFeedforward() {
    return feedforward;
  }

  public PIDController getFeedforwardPID() {
    return feedforwardPID.toController();
  }

  public MecanumDriveInfo calculateMecanumDrive(double forward, double sideways, double rotation) {
    return new MecanumDriveInfo(
      forward + sideways + rotation,
      forward - sideways - rotation,
      forward - sideways + rotation,
      forward + sideways - rotation
    );
  }

  public void drivePercent(double forward, double sideways, double rotation) {
    drivePercent(calculateMecanumDrive(forward, sideways, rotation));
  }

  public void drivePercent(MecanumDriveInfo powers) {
    frontLeft.setPower(powers.frontLeft);
    frontRight.setPower(powers.frontRight);
    backLeft.setPower(powers.backLeft);
    backRight.setPower(powers.backRight);
  }

  public void driveWheelSpeeds(MecanumDriveWheelSpeeds mecanumDriveWheelSpeeds) {
    SmartDashboard.putNumber("Target Front Left Speed", mecanumDriveWheelSpeeds.frontLeftMetersPerSecond);
    SmartDashboard.putNumber("Target Front Right Speed", mecanumDriveWheelSpeeds.frontRightMetersPerSecond);
    SmartDashboard.putNumber("Target Back Left Speed", mecanumDriveWheelSpeeds.rearLeftMetersPerSecond);
    SmartDashboard.putNumber("Target Back Right Speed", mecanumDriveWheelSpeeds.rearRightMetersPerSecond);

    frontLeft.setLinearVelocityFeedforwardPID(mecanumDriveWheelSpeeds.frontLeftMetersPerSecond);
    frontRight.setLinearVelocityFeedforwardPID(mecanumDriveWheelSpeeds.frontRightMetersPerSecond);
    backLeft.setLinearVelocityFeedforwardPID(mecanumDriveWheelSpeeds.rearLeftMetersPerSecond);
    backRight.setLinearVelocityFeedforwardPID(mecanumDriveWheelSpeeds.rearRightMetersPerSecond);
  }

  public void driveWheelSpeeds(MecanumDriveInfo info) {
    driveWheelSpeeds(info.toWheelSpeeds());
  }

  public void driveVoltages(MecanumDriveMotorVoltages mecanumDriveMotorVoltages) {
    frontLeft.setVoltage(mecanumDriveMotorVoltages.frontLeftVoltage);
    frontRight.setVoltage(mecanumDriveMotorVoltages.frontRightVoltage);
    backLeft.setVoltage(mecanumDriveMotorVoltages.rearLeftVoltage);
    backRight.setVoltage(mecanumDriveMotorVoltages.rearRightVoltage);
  }

  public void stop() {
    frontLeft.stop();
    frontRight.stop();
    backLeft.stop();
    backRight.stop();
  }

  public double getAngle() {
    return gyro.getAngle();
  }

  public Rotation2d getRotation2d() {
    return gyro.getRotation2d();
  }

  public void resetGyro() {
    gyro.reset();
  }

  public void reset() {
    resetGyro();
    stop();
  }

  public MecanumDriveInfo getWheelPositions() {
    // in raw sensor units
    return new MecanumDriveInfo(
      frontLeft.getPositionSensorUnits(),
      frontRight.getPositionSensorUnits(),
      backLeft.getPositionSensorUnits(),
      backRight.getPositionSensorUnits()
    );
  }

  public MecanumDriveInfo getWheelRPM() {
    return new MecanumDriveInfo(
      frontLeft.getRPM(),
      frontRight.getRPM(),
      backLeft.getRPM(),
      backRight.getRPM()
    );
  }

  public MecanumDriveWheelSpeeds getWheelSpeeds() {
    return new MecanumDriveWheelSpeeds(
      frontLeft.getLinearVelocity(),
      frontRight.getLinearVelocity(),
      backLeft.getLinearVelocity(),
      backRight.getLinearVelocity()
    );
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public void resetOdometry(Pose2d pose) {
    odometry.resetPosition(pose, getRotation2d());
  }

  @Override
  public void periodic() {
    odometry.update(getRotation2d(), getWheelSpeeds());

    SmartDashboard.putNumber("Pose X", getPose().getX());
    SmartDashboard.putNumber("Pose Y", getPose().getY());
    SmartDashboard.putNumber("Gyro", getAngle());

    MecanumDriveWheelSpeeds wheelSpeeds = getWheelSpeeds();
    SmartDashboard.putNumber("Front Left Speed", wheelSpeeds.frontLeftMetersPerSecond);
    SmartDashboard.putNumber("Front Right Speed", wheelSpeeds.frontRightMetersPerSecond);
    SmartDashboard.putNumber("Back Left Speed", wheelSpeeds.rearLeftMetersPerSecond);
    SmartDashboard.putNumber("Back Right Speed", wheelSpeeds.rearRightMetersPerSecond);
  }
}
