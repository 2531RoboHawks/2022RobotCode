// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
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
  
  // Keep these constants in this file, I dont want to deal with renaming them in constants
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

  private static class EncoderInfo {
    // both of these are in raw sensor units
    // zeroing is used for auto pathing -- NOT for teleop driving
    private double targetValue = 0;
    private double zero = 0;
    EncoderInfo() {
      reset();
    }
    double getValue() {
      return targetValue;
    }
    void update(double delta, double currentActualValue) {
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
  private double teleopKp = 0.2;
  private double teleopKd = 0.0;
  private double teleopKi = 0.0;

  public DriveSubsystem() {
    frontLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    frontLeft.config_kP(0, teleopKp);
    frontLeft.config_kD(0, teleopKd);
    frontLeft.config_kI(0, teleopKi);
    frontLeft.setInverted(false);

    frontRight.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    frontRight.config_kP(0, teleopKp);
    frontRight.config_kD(0, teleopKd);
    frontRight.config_kI(0, teleopKi);
    frontRight.setInverted(true);

    backLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    backLeft.config_kP(0, teleopKp);
    backLeft.config_kD(0, teleopKd);
    backLeft.config_kI(0, teleopKi);
    backLeft.setInverted(false);

    backRight.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    backRight.config_kP(0, teleopKp);
    backRight.config_kD(0, teleopKd);
    backRight.config_kI(0, teleopKi);
    backRight.setInverted(true);

    analogGyro.calibrate();
    navxGyro.calibrate();
    resetGyro();
    resetEncoders();

    mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
    odometry = new MecanumDriveOdometry(kinematics, navxGyro.getRotation2d());
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
    frontLeftInfo.update(vector.x + vector.y + z, frontLeft.getSelectedSensorPosition());
    frontRightInfo.update(vector.x - vector.y - z, frontRight.getSelectedSensorPosition());
    backLeftInfo.update(vector.x - vector.y + z, backLeft.getSelectedSensorPosition());
    backRightInfo.update(vector.x + vector.y - z, backRight.getSelectedSensorPosition());

    frontLeft.set(ControlMode.Position, frontLeftInfo.getValue());
    frontRight.set(ControlMode.Position, frontRightInfo.getValue());
    backLeft.set(ControlMode.Position, backLeftInfo.getValue());
    backRight.set(ControlMode.Position, backRightInfo.getValue());

    mecanumDrive.feed();
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

  private double getFrontLeftPositionRaw() {
    return frontLeft.getSelectedSensorPosition() - frontLeftInfo.getZero();
  }
  public double getFrontLeftPositionMeters() {
    return getFrontLeftPositionRaw() * unitsToMeters;
  }
  private double getFrontRightPositionRaw() {
    return frontRight.getSelectedSensorPosition() - frontRightInfo.getZero();
  }
  public double getFrontRightPositionMeters() {
    return getFrontRightPositionRaw() * unitsToMeters;
  }
  private double getBackLeftPositionRaw() {
    return backLeft.getSelectedSensorPosition() - backLeftInfo.getZero();
  }
  public double getBackLeftPositionMeters() {
    return getBackLeftPositionRaw() * unitsToMeters;
  }
  private double getBackRightPositionRaw() {
    return backRight.getSelectedSensorPosition() - backRightInfo.getZero();
  }
  public double getBackRightPositionMeters() {
    return getBackRightPositionRaw() * unitsToMeters;
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
    odometry.resetPosition(pose, navxGyro.getRotation2d());
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
    odometry.update(navxGyro.getRotation2d(), getWheelSpeeds());

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
