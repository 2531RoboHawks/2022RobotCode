// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.InputUtils;
import frc.robot.PIDSettings;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
  private static final PIDSettings pid = new PIDSettings(0.2, 0, 0);

  private DriveSubsystem driveSubsystem;
  private boolean fieldOriented = false;

  public DriveCommand(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {
    driveSubsystem.setPID(pid);
    driveSubsystem.reset();
    SmartDashboard.putBoolean("Field Oriented", fieldOriented);
  }

  private double scale(double n) {
    n = InputUtils.deadzone(n);
    return n * n * Math.signum(n);
  }

  @Override
  public void execute() {
    boolean turbo = RobotContainer.gamepad.getRawButton(Constants.Controls.Turbo);
    double xyMultiplier = turbo ? 1 : 0.3;
    double rotationMultiplier = turbo ? 0.35 : 0.25;

    if (RobotContainer.gamepad.getRawButtonPressed(Constants.Controls.ToggleFieldOriented)) {
      fieldOriented = !fieldOriented;
      System.out.println("Field oriented: " + fieldOriented);
      SmartDashboard.putBoolean("Field Oriented", fieldOriented);
    }

    if (fieldOriented) {
      if (RobotContainer.gamepad.getRawButtonPressed(Constants.Controls.ResetDrive)) {
        driveSubsystem.resetGyro();
        driveSubsystem.resetEncoders();
      }  
    } else {
      // TEMPORARY
      if (RobotContainer.gamepad.getRawButton(Constants.Controls.ResetDrive)) {
        xyMultiplier /= 2;
      }
    }

    double x = RobotContainer.gamepad.getX();
    double y = -RobotContainer.gamepad.getY();
    double z = RobotContainer.gamepad.getRawAxis(4);

    driveSubsystem.drivePercent(
      scale(y) * xyMultiplier,
      scale(x) * xyMultiplier,
      scale(z) * rotationMultiplier,
      fieldOriented
    );
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
