// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class DriveSubsystem extends SubsystemBase {
  private Spark frontLeft = new Spark(0);
  private Spark frontRight = new Spark(1);
  private Spark backLeft = new Spark(2);
  private Spark backRight = new Spark(3);

  private MecanumDrive mecanumDrive;
  /** Creates a new ExampleSubsystem. */
  public DriveSubsystem() {
    frontLeft.setInverted(true);
    backLeft.setInverted(true);
    mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
  }

  public void mecanum(double ySpeed, double xSpeed, double zRotation) {
    mecanumDrive.driveCartesian(ySpeed, xSpeed, zRotation);
    SmartDashboard.putNumber("Gyro", RobotContainer.gyro.getAngle());
  }

  public void stop() {
    mecanumDrive.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
