// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
  private DriveSubsystem driveSubsystem;

  public DriveCommand(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    addRequirements(RobotContainer.driveSubsystem);
  }

  @Override
  public void initialize() {
    driveSubsystem.resetGyro();
  }

  private double applyDeadzone(double i) {
    if (Math.abs(i) < 0.1) return 0;
    return i;
  }

  @Override
  public void execute() {
    boolean turbo = RobotContainer.gamepad.getRawButton(6);
    double xyMultiplier = turbo ? 1 : 0.3;
    boolean fieldOriented = false;

    if (RobotContainer.gamepad.getRawButtonPressed(2)) {
      driveSubsystem.resetGyro();
    }

    driveSubsystem.drivePercent(
      -applyDeadzone(RobotContainer.gamepad.getY()) * xyMultiplier,
      applyDeadzone(RobotContainer.gamepad.getX()) * xyMultiplier,
      applyDeadzone(RobotContainer.gamepad.getRawAxis(4)) / 4,
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
