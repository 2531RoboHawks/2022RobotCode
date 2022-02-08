// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class DriveSubsystem extends SubsystemBase {
  private Gyro gyro = new AHRS(SPI.Port.kMXP);

  private WPI_TalonFX frontLeft = new WPI_TalonFX(0);
  private WPI_TalonFX frontRight = new WPI_TalonFX(1);
  private WPI_TalonFX backLeft = new WPI_TalonFX(2);
  private WPI_TalonFX backRight = new WPI_TalonFX(3);

  private MecanumDrive mecanumDrive;

  public DriveSubsystem() {
    frontLeft.setInverted(true);
    backLeft.setInverted(true);
    mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
    calibrateGyro();
  }

  public void fieldOriented(double ySpeed, double xSpeed, double zRotation) {
    mecanumDrive.driveCartesian(ySpeed, xSpeed, zRotation, gyro.getAngle());
    SmartDashboard.putNumber("Gyro", gyro.getAngle());
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

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
