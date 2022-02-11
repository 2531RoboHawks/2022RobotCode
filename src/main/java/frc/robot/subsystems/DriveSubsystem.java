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
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  private AHRS gyro = new AHRS(SPI.Port.kMXP);

  private WPI_TalonFX frontLeft = new WPI_TalonFX(0);
  private WPI_TalonFX frontRight = new WPI_TalonFX(1);
  private WPI_TalonFX backLeft = new WPI_TalonFX(2);
  private WPI_TalonFX backRight = new WPI_TalonFX(3);

  // Auto-related constants
  public static final double kS = 0.62762;
  public static final double kV = 1.4371;
  public static final double kA = 0.20513;
  public static final double kP = 1.963;
  // in m/s
  public static final double maxSpeed = 3;
  public static final double maxAcceleration = 1;
  // in radians
  public static final double maxRotation = 1;
  public static final double maxRotationAcceleration = 1;
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

  private static class WheelTarget {
    // in raw sensor units
    private double value;
    WheelTarget() {
      reset();
    }
    double getValue() {
      return value;
    }
    void update(double delta, double currentActualValue) {
      value += delta;
      // If we, for example, run directly into a wall, don't allow the tolerance
      // values to grow indefintitely.
      // TODO: need to test, would it be better to hard reset to actual????
      // TODO: we can probably use gyro to know whether we are up against wall or being pushed by robot and act accordingly????
      int maxError = 20480;
      double maximumAllowedTargetValue = currentActualValue + maxError;
      double minimumAllowedTargetValue = currentActualValue - maxError;
      if (value < minimumAllowedTargetValue) {
        System.out.println("Exceeded minimum " + value + " " + minimumAllowedTargetValue);
        value = minimumAllowedTargetValue;
      }
      if (value > maximumAllowedTargetValue) {
        System.out.println("Exceeded maximum " + value + " " + maximumAllowedTargetValue);
        value = maximumAllowedTargetValue;
      }
    }
    void reset() {
      value = 0;
    }
  }

  private WheelTarget targetFrontRight = new WheelTarget();
  private WheelTarget targetFrontLeft = new WheelTarget();
  private WheelTarget targetBackRight = new WheelTarget();
  private WheelTarget targetBackLeft = new WheelTarget();

  // TODO: tuning mandatory
  private double teleopKp = 0.1;
  private double teleopKd = 0.0;
  private double teleopKi = 0.0;

  public DriveSubsystem() {
    frontLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    frontLeft.config_kP(0, teleopKp);
    frontLeft.config_kD(0, teleopKd);
    frontLeft.config_kI(0, teleopKi);
    frontLeft.setInverted(true);

    frontRight.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    frontRight.config_kP(0, teleopKp);
    frontRight.config_kD(0, teleopKd);
    frontRight.config_kI(0, teleopKi);

    backLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    backLeft.config_kP(0, teleopKp);
    backLeft.config_kD(0, teleopKd);
    backLeft.config_kI(0, teleopKi);
    backLeft.setInverted(true);

    backRight.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    backRight.config_kP(0, teleopKp);
    backRight.config_kD(0, teleopKd);
    backRight.config_kI(0, teleopKi);

    calibrateGyro();
    resetGyro();
    resetEncoders();

    mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
    odometry = new MecanumDriveOdometry(kinematics, gyro.getRotation2d());
  }

  public void drivePercent(double ySpeed, double xSpeed, double zRotation, boolean fieldOriented) {
    // TODO: unused
    mecanumDrive.driveCartesian(
      ySpeed,
      xSpeed,
      zRotation,
      fieldOriented ? gyro.getAngle() : 0
    );
  }

  public void driveTeleop(double y, double x, double z, boolean fieldOriented) {
    // scale [-1, 1] to raw sensor units
    // TODO make this work better, use eg. m/s?
    x *= 2048;
    y *= 2048;
    z *= 2048;

    Vector2d vector = new Vector2d(x, y);
    if (fieldOriented) {
      vector.rotate(gyro.getAngle());
    }

    // TODO: use gyro to correct rotation drift?
    targetFrontLeft.update(vector.x + vector.y + z, frontLeft.getSelectedSensorPosition());
    targetFrontRight.update(vector.x - vector.y - z, frontRight.getSelectedSensorPosition());
    targetBackLeft.update(vector.x - vector.y + z, backLeft.getSelectedSensorPosition());
    targetBackRight.update(vector.x + vector.y - z, backRight.getSelectedSensorPosition());

    frontLeft.set(ControlMode.Position, targetFrontLeft.getValue());
    frontRight.set(ControlMode.Position, targetFrontRight.getValue());
    backLeft.set(ControlMode.Position, targetBackLeft.getValue());
    backRight.set(ControlMode.Position, targetBackRight.getValue());

    mecanumDrive.feed();
  }

  public void stop() {
    mecanumDrive.stopMotor();
  }

  public double getAngle() {
    return gyro.getAngle();
  }

  public void resetGyro() {
    gyro.reset();
  }

  public void calibrateGyro() {
    gyro.calibrate();
  }

  public void resetEncoders() {
    targetFrontLeft.reset();
    targetFrontRight.reset();
    targetBackLeft.reset();
    targetBackRight.reset();

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
    return frontLeft.getSelectedSensorVelocity() * unitsToMeters * 10.0;
  }
  public double getFrontRightVelocity() {
    return frontRight.getSelectedSensorVelocity() * unitsToMeters * 10.0;
  }
  public double getBackLeftVelocity() {
    return backLeft.getSelectedSensorVelocity() * unitsToMeters * 10.0;
  }
  public double getBackRightVelocity() {
    return backRight.getSelectedSensorVelocity() * unitsToMeters * 10.0;
  }

  public void resetOdometry(Pose2d pose) {
    resetGyro();
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

    // SmartDashboard.putNumber("fl", getFrontLeftPosition());
    // SmartDashboard.putNumber("fr", getFrontRightPosition());
    // SmartDashboard.putNumber("bl", getBackLeftPosition());
    // SmartDashboard.putNumber("br", getBackRightPosition());
    // SmartDashboard.putNumber("fl v", getFrontLeftVelocity());
    // SmartDashboard.putNumber("fr v", getFrontRightVelocity());
    // SmartDashboard.putNumber("bl v", getBackLeftVelocity());
    // SmartDashboard.putNumber("br v", getBackRightVelocity());
    SmartDashboard.putNumber("Gyro", getAngle());
  }
}
