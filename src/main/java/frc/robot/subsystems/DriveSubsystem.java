// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_PigeonIMU;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.MecanumDriveInfo;
import frc.robot.PIDSettings;

public class DriveSubsystem extends SubsystemBase {
  private WPI_PigeonIMU pigeon = new WPI_PigeonIMU(1);

  private WPI_TalonFX frontLeft = new WPI_TalonFX(30);
  private WPI_TalonFX frontRight = new WPI_TalonFX(31);
  private WPI_TalonFX backLeft = new WPI_TalonFX(32);
  private WPI_TalonFX backRight = new WPI_TalonFX(33);

  public static final MecanumDriveKinematics kinematics = new MecanumDriveKinematics(
    // In meters from center of robot
    new Translation2d(0.26035, 0.2921),
    new Translation2d(0.26035, -0.2921),
    new Translation2d(-0.26035, 0.2921),
    new Translation2d(-0.26035, -0.2921)
  );

  private MecanumDrive mecanumDrive;
  private MecanumDriveOdometry odometry;

  private static final double wheelDiameter = 0.2032; // meters
  private static final double wheelCircumference = Math.PI * wheelDiameter;
  private static final double gearRatio = 8.45 / 1.0;
  private static final double unitsPerTurn = 2048.0;
  private static final double unitsToMeters = wheelCircumference / (unitsPerTurn * gearRatio);
  private static final double metersToUnits = 1.0 / unitsToMeters;

  public DriveSubsystem() {
    mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);

    setPID(new PIDSettings());

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

  public void drivePercent(double ySpeed, double xSpeed, double zRotation, boolean fieldOriented) {
    // TODO implement fieldOriented if it's used
    frontLeft.set(ControlMode.PercentOutput, ySpeed + xSpeed + zRotation);
    frontRight.set(ControlMode.PercentOutput, ySpeed - xSpeed - zRotation);
    backLeft.set(ControlMode.PercentOutput, ySpeed - xSpeed + zRotation);
    backRight.set(ControlMode.PercentOutput, ySpeed + xSpeed - zRotation);
    mecanumDrive.feed();
  }

  public void stop() {
    mecanumDrive.stopMotor();
  }

  public double getAngle() {
    return pigeon.getAngle();
  }

  public Rotation2d getRotation2d() {
    return pigeon.getRotation2d();
  }

  public void resetGyro() {
    pigeon.reset();
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
      frontLeft.getSelectedSensorPosition(),
      frontRight.getSelectedSensorPosition(),
      backLeft.getSelectedSensorPosition(),
      backRight.getSelectedSensorPosition()
    );
  }

  public MecanumDriveInfo getWheelVelocities() {
    // in raw sensor units
    return new MecanumDriveInfo(
      frontLeft.getSelectedSensorVelocity(),
      frontRight.getSelectedSensorVelocity(),
      backLeft.getSelectedSensorVelocity(),
      backRight.getSelectedSensorVelocity()
    );
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public MecanumDriveWheelSpeeds getWheelSpeeds() {
    // in m/s
    return new MecanumDriveWheelSpeeds(
      frontLeft.getSelectedSensorVelocity() * unitsToMeters * 10.0,
      frontRight.getSelectedSensorVelocity() * unitsToMeters * 10.0,
      backLeft.getSelectedSensorVelocity() * unitsToMeters * 10.0,
      backRight.getSelectedSensorVelocity() * unitsToMeters * 10.0
    );
  }

  @Override
  public void periodic() {
    odometry.update(getRotation2d(), getWheelSpeeds());

    SmartDashboard.putNumber("Pose X", getPose().getX());
    SmartDashboard.putNumber("Pose Y", getPose().getY());
    SmartDashboard.putNumber("Gyro", getAngle());

    SmartDashboard.putNumber("FL Amps", frontLeft.getSupplyCurrent());
    SmartDashboard.putNumber("FR Amps", frontRight.getSupplyCurrent());
    SmartDashboard.putNumber("BL Amps", backLeft.getSupplyCurrent());
    SmartDashboard.putNumber("BR Amps", backRight.getSupplyCurrent());
  }
}
