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
import frc.robot.TalonUtils;

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

  public DriveSubsystem() {
    mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);

    setPID(new PIDSettings());

    double secondsFromNeutralToFull = 0.5;
    frontLeft.configOpenloopRamp(secondsFromNeutralToFull);
    frontLeft.configClosedloopRamp(secondsFromNeutralToFull);
    frontRight.configOpenloopRamp(secondsFromNeutralToFull);
    frontRight.configClosedloopRamp(secondsFromNeutralToFull);
    backLeft.configOpenloopRamp(secondsFromNeutralToFull);
    backLeft.configClosedloopRamp(secondsFromNeutralToFull);
    backRight.configOpenloopRamp(secondsFromNeutralToFull);
    backRight.configClosedloopRamp(secondsFromNeutralToFull);

    frontRight.setInverted(true);
    backRight.setInverted(true);

    odometry = new MecanumDriveOdometry(kinematics, getRotation2d());
    reset();
  }

  private PIDSettings lastPidSettings;
  public void setPID(PIDSettings pid) {
    lastPidSettings = pid;
    TalonUtils.configurePID(frontLeft, pid);
    TalonUtils.configurePID(frontRight, pid);
    TalonUtils.configurePID(backLeft, pid);
    TalonUtils.configurePID(backRight, pid);
  }
  public PIDSettings getPID() {
    return lastPidSettings;
  }

  public void drivePercent(double ySpeed, double xSpeed, double zRotation, boolean fieldOriented) {
    mecanumDrive.driveCartesian(
      ySpeed,
      xSpeed,
      zRotation,
      fieldOriented ? getAngle() : 0
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
    x *= 2048;
    y *= 2048;
    z *= 2048;

    // Vector2d arguments are supposed to be backwards
    Vector2d vector = new Vector2d(y, x);
    if (fieldOriented) {
      vector.rotate(getAngle());
    }

    frontLeftInfo.changeTargetBy(vector.x + vector.y + z);
    frontRightInfo.changeTargetBy(vector.x - vector.y - z);
    backLeftInfo.changeTargetBy(vector.x - vector.y + z);
    backRightInfo.changeTargetBy(vector.x + vector.y - z);
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

  public void driveFixedSensorUnits(MecanumDriveInfo info) {
    frontLeftInfo.setTarget(info.frontLeft);
    frontRightInfo.setTarget(info.frontRight);
    backLeftInfo.setTarget(info.backLeft);
    backRightInfo.setTarget(info.backRight);
    updateMotorsToTargetValues();
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

  public MecanumDriveInfo getWheelPositions() {
    return new MecanumDriveInfo(
      frontLeft.getSelectedSensorPosition(),
      frontRight.getSelectedSensorPosition(),
      backLeft.getSelectedSensorPosition(),
      backRight.getSelectedSensorPosition()
    );
  }

  public MecanumDriveInfo getWheelVelocities() {
    return new MecanumDriveInfo(
      frontLeft.getSelectedSensorVelocity(),
      frontRight.getSelectedSensorVelocity(),
      backLeft.getSelectedSensorVelocity(),
      backRight.getSelectedSensorVelocity()
    );
  }

  public MecanumDriveInfo getTargetValues() {
    return new MecanumDriveInfo(
      frontLeftInfo.targetValue,
      frontRightInfo.targetValue,
      backLeftInfo.targetValue,
      backRightInfo.targetValue
    );
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public MecanumDriveWheelSpeeds getWheelSpeeds() {
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
  }
}
