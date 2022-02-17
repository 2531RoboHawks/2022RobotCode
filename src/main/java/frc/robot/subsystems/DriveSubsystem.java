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
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.TalonUtils;

public class DriveSubsystem extends SubsystemBase {
  private AHRS navxGyro = new AHRS(SPI.Port.kMXP);
  private ADXRS450_Gyro analogGyro = new ADXRS450_Gyro();
  // private PigeonIMU pigeon = new PigeonIMU(0);

  private WPI_TalonFX frontLeft = new WPI_TalonFX(0);
  private WPI_TalonFX frontRight = new WPI_TalonFX(1);
  private WPI_TalonFX backLeft = new WPI_TalonFX(2);
  private WPI_TalonFX backRight = new WPI_TalonFX(3);

  private MecanumDrive mecanumDrive;

  private static final double wheelDiameter = 0.2032; // meters
  private static final double wheelCircumference = Math.PI * wheelDiameter;
  private static final double gearRatio = 8.45 / 1.0;
  private static final double unitsPerTurn = 2048.0;
  private static final double unitsToMeters = wheelCircumference / (unitsPerTurn * gearRatio);
  private static final double metersToUnits = 1.0 / unitsToMeters;

  private static class EncoderInfo {
    private double targetValue = 0;
    private double zero = 0;
    double getTargetValueIncludingZero() {
      return targetValue + zero;
    }
    void setTarget(double newTarget) {
      targetValue = newTarget;
    }
    void changeTargetBy(double delta) {
      targetValue += delta;
    }
    private void setZero(double newZero) {
      zero = newZero;
      targetValue = 0;
    }
  }

  private EncoderInfo frontRightInfo = new EncoderInfo();
  private EncoderInfo frontLeftInfo = new EncoderInfo();
  private EncoderInfo backRightInfo = new EncoderInfo();
  private EncoderInfo backLeftInfo = new EncoderInfo();

  // TODO: tuning mandatory
  // TODO: move to separate replacable class so that auto and teleop can have different
  // private double teleopKp = 0.2;
  private double teleopKp = 0.02;
  private double teleopKd = 0.0;
  private double teleopKi = 0.0;

  public DriveSubsystem() {
    mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);

    TalonUtils.configurePID(frontLeft, teleopKp, teleopKd, teleopKi);
    TalonUtils.configurePID(frontRight, teleopKp, teleopKd, teleopKi);
    TalonUtils.configurePID(backLeft, teleopKp, teleopKd, teleopKi);
    TalonUtils.configurePID(backRight, teleopKp, teleopKd, teleopKi);

    frontRight.setInverted(true);
    backRight.setInverted(true);

    analogGyro.calibrate();
    navxGyro.calibrate();

    reset();
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
    frontLeft.set(ControlMode.Position, frontLeftInfo.getTargetValueIncludingZero());
    frontRight.set(ControlMode.Position, frontRightInfo.getTargetValueIncludingZero());
    backLeft.set(ControlMode.Position, backLeftInfo.getTargetValueIncludingZero());
    backRight.set(ControlMode.Position, backRightInfo.getTargetValueIncludingZero());
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
      vector.rotate(-getAngle());
    }

    // TODO: use gyro to correct rotation drift?
    frontLeftInfo.changeTargetBy(vector.x + vector.y + z);
    frontRightInfo.changeTargetBy(vector.x - vector.y - z);
    backLeftInfo.changeTargetBy(vector.x - vector.y + z);
    backRightInfo.changeTargetBy(vector.x + vector.y - z);
    updateMotorsToTargetValues();
  }

  public void driveAutoDelta(double x, double y, double z) {
    double calculatedX = x * metersToUnits;
    double calculatedY = y * metersToUnits;
    double calculatedZ = z * metersToUnits;

    if (false) {
      Vector2d vector = new Vector2d(calculatedX, calculatedY);
      vector.rotate(-getAngle());

      frontLeftInfo.changeTargetBy(vector.x + vector.y + calculatedZ);
      frontRightInfo.changeTargetBy(vector.x - vector.y - calculatedZ);
      backLeftInfo.changeTargetBy(vector.x - vector.y + calculatedZ);
      backRightInfo.changeTargetBy(vector.x + vector.y - calculatedZ);
    } else {
      frontLeftInfo.changeTargetBy(calculatedY + calculatedX + calculatedZ);
      frontRightInfo.changeTargetBy(calculatedY - calculatedX - calculatedZ);
      backLeftInfo.changeTargetBy(calculatedY - calculatedX + calculatedZ);
      backRightInfo.changeTargetBy(calculatedY + calculatedX - calculatedZ);
    }
    updateMotorsToTargetValues();
  }

  public void driveAutoFixed(double x, double y) {
    double calculatedX = x * metersToUnits;
    double calculatedY = y * metersToUnits;
    double calculatedZ = 0;
    frontLeftInfo.setTarget(calculatedY + calculatedX + calculatedZ);
    frontRightInfo.setTarget(calculatedY - calculatedX - calculatedZ);
    backLeftInfo.setTarget(calculatedY - calculatedX + calculatedZ);
    backRightInfo.setTarget(calculatedY + calculatedX - calculatedZ);
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
    // setSelectedSensorPosition has proven unreliable
    frontLeftInfo.setZero(frontLeft.getSelectedSensorPosition());
    frontRightInfo.setZero(frontRight.getSelectedSensorPosition());
    backLeftInfo.setZero(backLeft.getSelectedSensorPosition());
    backRightInfo.setZero(backRight.getSelectedSensorPosition());
  }

  public void reset() {
    resetGyro();
    resetEncoders();
    stop();
  }

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
