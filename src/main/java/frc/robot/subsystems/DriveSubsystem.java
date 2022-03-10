// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterTalonFX;
import frc.robot.MecanumDriveInfo;
import frc.robot.PIDSettings;

public class DriveSubsystem extends SubsystemBase {
  private Gyro gyro = new AHRS(SPI.Port.kMXP);

  private BetterTalonFX frontLeft = new BetterTalonFX(30);
  private BetterTalonFX frontRight = new BetterTalonFX(31);
  private BetterTalonFX backLeft = new BetterTalonFX(32);
  private BetterTalonFX backRight = new BetterTalonFX(33);

  public final MecanumDriveKinematics kinematics = new MecanumDriveKinematics(
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
  private static final double metersToRotations = 1.0 / rotationsToMeters;

  private final PIDSettings drivePid = new PIDSettings(0.1, 0.0005, 0);

  public DriveSubsystem() {
    setPID(new PIDSettings());

    frontLeft.configurePID(drivePid);
    frontRight.configurePID(drivePid);
    backLeft.configurePID(drivePid);
    backRight.configurePID(drivePid);

    // double secondsFromNeutralToFull = 0.5;
    // frontLeft.configOpenloopRamp(secondsFromNeutralToFull);
    // frontLeft.configClosedloopRamp(secondsFromNeutralToFull);
    // frontRight.configOpenloopRamp(secondsFromNeutralToFull);
    // frontRight.configClosedloopRamp(secondsFromNeutralToFull);
    // backLeft.configOpenloopRamp(secondsFromNeutralToFull);
    // backLeft.configClosedloopRamp(secondsFromNeutralToFull);
    // backRight.configOpenloopRamp(secondsFromNeutralToFull);
    // backRight.configClosedloopRamp(secondsFromNeutralToFull);

    frontRight.setInverted(true);
    backRight.setInverted(true);

    odometry = new MecanumDriveOdometry(kinematics, getRotation2d());
    reset();
  }

  // TODO: unused
  private PIDSettings lastPidSettings;
  public void setPID(PIDSettings pid) {
    // TODO: unused
    lastPidSettings = pid;
  }
  public PIDSettings getPID() {
    // TODO: unused
    return lastPidSettings;
  }

  public void drive(double ySpeed, double xSpeed, double zRotation) {
    double frontLeft = ySpeed + xSpeed + zRotation;
    double frontRight = ySpeed - xSpeed - zRotation;
    double backLeft = ySpeed - xSpeed + zRotation;
    double backRight = ySpeed + xSpeed - zRotation;
    driveWheelSpeeds(new MecanumDriveWheelSpeeds(
      frontLeft,
      frontRight,
      backLeft,
      backRight
    ));
  }

  public void drivePercent(double ySpeed, double xSpeed, double zRotation) {
    frontLeft.setPower(ySpeed + xSpeed + zRotation);
    frontRight.setPower(ySpeed - xSpeed - zRotation);
    backLeft.setPower(ySpeed - xSpeed + zRotation);
    backRight.setPower(ySpeed + xSpeed - zRotation);
  }

  public void driveWheelSpeeds(MecanumDriveWheelSpeeds mecanumDriveWheelSpeeds) {
    // TODO: remove temporary
    SmartDashboard.putNumber("Target Fonrt Left Speed", mecanumDriveWheelSpeeds.frontLeftMetersPerSecond);
    // meters/sec -> rotations/sec -> rotations/min
    frontLeft.setRPM(mecanumDriveWheelSpeeds.frontLeftMetersPerSecond * metersToRotations * 60.0);
    frontRight.setRPM(mecanumDriveWheelSpeeds.frontRightMetersPerSecond * metersToRotations * 60.0);
    backLeft.setRPM(mecanumDriveWheelSpeeds.rearLeftMetersPerSecond * metersToRotations * 60.0);
    backRight.setRPM(mecanumDriveWheelSpeeds.rearRightMetersPerSecond * metersToRotations * 60.0);
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

  public void resetEncoders() {
    // TODO
  }

  public void reset() {
    resetGyro();
    resetEncoders();
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
    // rotations/min -> meters/min -> meters/sec
    return new MecanumDriveWheelSpeeds(
      frontLeft.getRPM() * rotationsToMeters / 60.0,
      frontRight.getRPM() * rotationsToMeters / 60.0,
      backLeft.getRPM() * rotationsToMeters / 60.0,
      backRight.getRPM() * rotationsToMeters / 60.0
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

    SmartDashboard.putNumber("Front Left Speed", getWheelSpeeds().frontLeftMetersPerSecond);
    SmartDashboard.putNumber("Front Right Speed", getWheelSpeeds().frontRightMetersPerSecond);
    SmartDashboard.putNumber("Back Left Speed", getWheelSpeeds().rearLeftMetersPerSecond);
    SmartDashboard.putNumber("Back Right Speed", getWheelSpeeds().rearRightMetersPerSecond);

    // SmartDashboard.putNumber("FL Amps", frontLeft.getSupplyCurrent());
    // SmartDashboard.putNumber("FR Amps", frontRight.getSupplyCurrent());
    // SmartDashboard.putNumber("BL Amps", backLeft.getSupplyCurrent());
    // SmartDashboard.putNumber("BR Amps", backRight.getSupplyCurrent());
  }
}
