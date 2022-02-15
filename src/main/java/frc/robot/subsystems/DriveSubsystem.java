// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  private AHRS navxGyro = new AHRS(SPI.Port.kMXP);
  private ADXRS450_Gyro analogGyro = new ADXRS450_Gyro();
  // private PigeonIMU pigeon = new PigeonIMU(0);

  private WPI_TalonFX frontLeft = new WPI_TalonFX(0);
  private WPI_TalonFX frontRight = new WPI_TalonFX(1);
  private WPI_TalonFX backLeft = new WPI_TalonFX(2);
  private WPI_TalonFX backRight = new WPI_TalonFX(3);

  private MecanumDrive mecanumDrive;

  private static class EncoderInfo {
    // both of these are in raw sensor units
    // zeroing is used for auto pathing -- NOT for teleop driving
    private double targetValue = 0;
    private double zero = 0;
    EncoderInfo() {
      reset();
    }
    double getTargetValue() {
      return targetValue;
    }
    void setZeroedTargetTo(double newTarget) {
      targetValue = newTarget - zero;
      System.out.println("Targeting: " + newTarget + " Zero: " + zero + " Final: " + targetValue);
    }
    void changeTargetBy(double delta, double currentActualValue) {
      targetValue += delta;
      // If we, for example, run directly into a wall, don't allow the tolerance
      // values to grow indefintitely.
      // TODO: need to test, would it be better to hard reset to actual????
      // TODO: we can probably use gyro to know whether we are up against wall or being pushed by robot and act accordingly????
      int maxError = 20480;
      double maximumAllowedTargetValue = currentActualValue + maxError;
      double minimumAllowedTargetValue = currentActualValue - maxError;
      if (targetValue < minimumAllowedTargetValue) {
        System.out.println("Exceeded minimum " + targetValue + " " + minimumAllowedTargetValue);
        targetValue = minimumAllowedTargetValue;
      }
      if (targetValue > maximumAllowedTargetValue) {
        System.out.println("Exceeded maximum " + targetValue + " " + maximumAllowedTargetValue);
        targetValue = maximumAllowedTargetValue;
      }
    }
    void reset() {
      targetValue = 0;
    }
    void setZero(double newValue) {
      zero = newValue;
      targetValue = newValue;
    }
    double getZero() {
      return zero;
    }
  }

  private EncoderInfo frontRightInfo = new EncoderInfo();
  private EncoderInfo frontLeftInfo = new EncoderInfo();
  private EncoderInfo backRightInfo = new EncoderInfo();
  private EncoderInfo backLeftInfo = new EncoderInfo();

  // TODO: tuning mandatory
  // private double teleopKp = 0.2;
  private double teleopKp = 0.02;
  private double teleopKd = 0.0;
  private double teleopKi = 0.0;

  public DriveSubsystem() {
    frontLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    frontLeft.config_kP(0, teleopKp);
    frontLeft.config_kD(0, teleopKd);
    frontLeft.config_kI(0, teleopKi);
    frontLeft.setSelectedSensorPosition(0);
    frontLeft.setInverted(false);

    frontRight.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    frontRight.config_kP(0, teleopKp);
    frontRight.config_kD(0, teleopKd);
    frontRight.config_kI(0, teleopKi);
    frontRight.setSelectedSensorPosition(0);
    frontRight.setInverted(true);

    backLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    backLeft.config_kP(0, teleopKp);
    backLeft.config_kD(0, teleopKd);
    backLeft.config_kI(0, teleopKi);
    backLeft.setSelectedSensorPosition(0);
    backLeft.setInverted(false);

    backRight.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    backRight.config_kP(0, teleopKp);
    backRight.config_kD(0, teleopKd);
    backRight.config_kI(0, teleopKi);
    backRight.setSelectedSensorPosition(0);
    backRight.setInverted(true);

    analogGyro.calibrate();
    navxGyro.calibrate();
    resetGyro();
    resetEncoders();

    mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
  }

  public void drivePercent(double ySpeed, double xSpeed, double zRotation, boolean fieldOriented) {
    // TODO: unused
    mecanumDrive.driveCartesian(
      ySpeed,
      xSpeed,
      zRotation,
      fieldOriented ? navxGyro.getAngle() : 0
    );
  }

  private void updateMotorsToTargetValues() {
    frontLeft.set(ControlMode.Position, frontLeftInfo.getTargetValue());
    frontRight.set(ControlMode.Position, frontRightInfo.getTargetValue());
    backLeft.set(ControlMode.Position, backLeftInfo.getTargetValue());
    backRight.set(ControlMode.Position, backRightInfo.getTargetValue());
    mecanumDrive.feed();
  }

  public void driveTeleop(double y, double x, double z, boolean fieldOriented) {
    // scale [-1, 1] to raw sensor units
    // TODO make this work better, use eg. m/s?
    x *= 2048;
    y *= 2048;
    z *= 2048;

    // Vector2d arguments are supposed to be backwards
    Vector2d vector = new Vector2d(y, x);
    if (fieldOriented) {
      vector.rotate(-navxGyro.getAngle());
    }

    // TODO: use gyro to correct rotation drift?
    frontLeftInfo.changeTargetBy(vector.x + vector.y + z, frontLeft.getSelectedSensorPosition());
    frontRightInfo.changeTargetBy(vector.x - vector.y - z, frontRight.getSelectedSensorPosition());
    backLeftInfo.changeTargetBy(vector.x - vector.y + z, backLeft.getSelectedSensorPosition());
    backRightInfo.changeTargetBy(vector.x + vector.y - z, backRight.getSelectedSensorPosition());
    updateMotorsToTargetValues();
  }

  public void driveFixedEncodedMeters(double x, double y, double z) {
    x *= metersToUnits;
    y *= metersToUnits;
    // z *= metersToUnits;
    z = 0;
    frontLeftInfo.setZeroedTargetTo(y + x + z);
    frontRightInfo.setZeroedTargetTo(y - x - z);
    backLeftInfo.setZeroedTargetTo(y - x + z);
    backRightInfo.setZeroedTargetTo(y + x - z);
    updateMotorsToTargetValues();
  }

  public void stop() {
    mecanumDrive.stopMotor();
  }

  public double getAngle() {
    return navxGyro.getAngle();
  }

  public void resetGyro() {
    navxGyro.reset();
  }

  public void resetEncoders() {
    frontLeftInfo.setZero(frontLeft.getSelectedSensorPosition());
    frontRightInfo.setZero(frontRight.getSelectedSensorPosition());
    backLeftInfo.setZero(backLeft.getSelectedSensorPosition());
    backRightInfo.setZero(backRight.getSelectedSensorPosition());
  }

  private static final double wheelDiameter = 0.2032; // meters
  private static final double wheelCircumference = Math.PI * wheelDiameter;
  private static final double gearRatio = 8.45 / 1.0;
  private static final double unitsPerTurn = 2048.0;
  private static final double unitsToMeters = wheelCircumference / (unitsPerTurn * gearRatio);
  private static final double metersToUnits = 1.0 / unitsToMeters;

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("fl", getFrontLeftPosition());
    // SmartDashboard.putNumber("fr", getFrontRightPosition());
    // SmartDashboard.putNumber("bl", getBackLeftPosition());
    // SmartDashboard.putNumber("br", getBackRightPosition());
    // SmartDashboard.putNumber("fl v", getFrontLeftVelocity());
    // SmartDashboard.putNumber("fr v", getFrontRightVelocity());
    // SmartDashboard.putNumber("bl v", getBackLeftVelocity());
    // SmartDashboard.putNumber("br v", getBackRightVelocity());
    SmartDashboard.putNumber("NavX Gyro", navxGyro.getAngle());
    SmartDashboard.putNumber("Analog Devices Gyro", analogGyro.getAngle());
    // SmartDashboard.putNumber("Pidgeon Gyro", pigeon.getFusedHeading());
  }
}
