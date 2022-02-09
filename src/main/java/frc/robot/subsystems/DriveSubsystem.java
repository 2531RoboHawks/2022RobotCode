// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  private Gyro gyro = new AHRS(SPI.Port.kMXP);

  private WPI_TalonFX frontLeft = new WPI_TalonFX(0);
  private WPI_TalonFX frontRight = new WPI_TalonFX(1);
  private WPI_TalonFX backLeft = new WPI_TalonFX(2);
  private WPI_TalonFX backRight = new WPI_TalonFX(3);

  public static final double kS = 0.62762;
  public static final double kV = 1.4371;
  public static final double kA = 0.20513;
  public static final double kP = 1.963;

  public static final double maxSpeed = 3;
  public static final double maxAcceleration = 3;

  public static final double kRamseteB = 2;
  public static final double kRamseteZeta = 0.7;

  public static final MecanumDriveKinematics kinematics = new MecanumDriveKinematics(
    new Translation2d(0.26035, 0.2921),
    new Translation2d(0.26035, -0.2921),
    new Translation2d(-0.26035, 0.2921),
    new Translation2d(-0.26035, -0.2921)
  );

  private MecanumDrive mecanumDrive;
  private MecanumDriveOdometry odometry;

  public DriveSubsystem() {
    frontLeft.setInverted(true);
    backLeft.setInverted(true);
    mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
    odometry = new MecanumDriveOdometry(kinematics, gyro.getRotation2d());
    calibrateGyro();
    resetEncoders();
  }

  public void fieldOriented(double ySpeed, double xSpeed, double zRotation) {
    mecanumDrive.driveCartesian(ySpeed, xSpeed, zRotation, 0);
    // SmartDashboard.putNumber("Gyro", gyro.getAngle());
  }

  public void resetGyro() {
    gyro.reset();
  }

  public void calibrateGyro() {
    gyro.calibrate();
  }

  public void stop() {
    mecanumDrive.stopMotor();
  }

  public void resetEncoders() {
    frontLeft.setSelectedSensorPosition(0);
    frontRight.setSelectedSensorPosition(0);
    backLeft.setSelectedSensorPosition(0);
    backRight.setSelectedSensorPosition(0);
  }

  private static final double wheelDiameter = 0.2032; // m
  private static final double wheelCircumference = Math.PI * wheelDiameter;
  private static final double gearRatio = 8.45;
  private static final double unitsPerTurn = 2048.0;
  private static final double unitsToMeters = wheelCircumference / (unitsPerTurn * gearRatio);
  public double getFrontLeftPosition() {
    return frontLeft.getSelectedSensorPosition() * unitsToMeters;
  }
  public double getFrontRightPosition() {
    return frontRight.getSelectedSensorPosition() * unitsToMeters;
  }
  public double getBackLeftPosition() {
    return backLeft.getSelectedSensorPosition() * unitsToMeters;
  }
  public double getBackRightPosition() {
    return backRight.getSelectedSensorPosition() * unitsToMeters;
  }
  public double getFrontLeftVelocity() {
    return frontLeft.getSelectedSensorVelocity() * unitsToMeters * 10;
  }
  public double getFrontRightVelocity() {
    return frontRight.getSelectedSensorVelocity() * unitsToMeters * 10;
  }
  public double getBackLeftVelocity() {
    return backLeft.getSelectedSensorVelocity() * unitsToMeters * 10;
  }
  public double getBackRightVelocity() {
    return backRight.getSelectedSensorVelocity() * unitsToMeters * 10;
  }

  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    odometry.resetPosition(pose, gyro.getRotation2d());
  }

  public MecanumDriveWheelSpeeds getWheelSpeeds() {
    return new MecanumDriveWheelSpeeds(
      getFrontLeftVelocity(),
      getFrontRightVelocity(),
      getBackLeftVelocity(),
      getBackRightVelocity()
    );
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public void driveVolts(MecanumDriveMotorVoltages voltages) {
    frontLeft.setVoltage(voltages.frontLeftVoltage);
    frontRight.setVoltage(voltages.frontRightVoltage);
    backLeft.setVoltage(voltages.rearLeftVoltage);
    backRight.setVoltage(voltages.rearRightVoltage);
    mecanumDrive.feed();
  }

  @Override
  public void periodic() {
    odometry.update(gyro.getRotation2d(), getWheelSpeeds());

    SmartDashboard.putNumber("fl", getFrontLeftPosition());
    SmartDashboard.putNumber("fr", getFrontRightPosition());
    SmartDashboard.putNumber("bl", getBackLeftPosition());
    SmartDashboard.putNumber("br", getBackRightPosition());
    SmartDashboard.putNumber("fl v", getFrontLeftVelocity());
    SmartDashboard.putNumber("fr v", getFrontRightVelocity());
    SmartDashboard.putNumber("bl v", getBackLeftVelocity());
    SmartDashboard.putNumber("br v", getBackRightVelocity());
  }
}
