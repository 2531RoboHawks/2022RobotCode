// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.InputUtils;
import frc.robot.PIDSettings;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
  private static final PIDSettings pid = new PIDSettings(0.2, 0, 0);

  private DriveSubsystem driveSubsystem;

  public DriveCommand(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {
    driveSubsystem.setPID(pid);
    driveSubsystem.reset();
  }

  private double scale(double n) {
    n = InputUtils.deadzone(n);
    return n * n * Math.signum(n);
  }

  @Override
  public void execute() {
    double xMultiplier;
    double yMultiplier;
    double rotationMultiplier;
    boolean slow = RobotContainer.gamepad.getRawButton(Constants.Controls.Slow);
    boolean turbo = RobotContainer.gamepad.getRawButton(Constants.Controls.Turbo);
    if (slow) {
      xMultiplier = 0.5;
      yMultiplier = 0.5;
      rotationMultiplier = 0.5;
    } else if (turbo) {
      xMultiplier = 3;
      yMultiplier = 3;
      rotationMultiplier = 2;
    } else {
      xMultiplier = 1;
      yMultiplier = 1;
      rotationMultiplier = 1;
    }

    double x = RobotContainer.gamepad.getLeftX();
    double y = -RobotContainer.gamepad.getLeftY();
    double z = RobotContainer.gamepad.getRightX();

    driveSubsystem.drive(
      scale(y) * yMultiplier,
      scale(x) * xMultiplier,
      scale(z) * rotationMultiplier
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
