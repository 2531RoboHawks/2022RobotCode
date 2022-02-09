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

  @Override
  public void execute() {
    boolean turbo = RobotContainer.gamepad.getRawButton(6);
    double multiplier = turbo ? 1 : 0.3;

    driveSubsystem.fieldOriented(
      -RobotContainer.gamepad.getY() * multiplier,
      RobotContainer.gamepad.getX() * multiplier,
      RobotContainer.gamepad.getRawAxis(4) / 4
    );

    if (RobotContainer.gamepad.getRawButton(2)) {
      driveSubsystem.resetGyro();
    }
  }

  @Override
  public void end(boolean interrupted) {
    RobotContainer.driveSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
