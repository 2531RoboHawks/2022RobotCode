// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
  private DriveSubsystem driveSubsystem;
  /** Creates a new DriveCommand. */
  public DriveCommand(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    addRequirements(RobotContainer.driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveSubsystem.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean turbo = RobotContainer.gamepad.getRawButton(6);
    double multiplier = turbo ? 1 : 0.5;
    RobotContainer.driveSubsystem.fieldOriented(
      -RobotContainer.gamepad.getY() * multiplier,
      RobotContainer.gamepad.getX() * multiplier,
      RobotContainer.gamepad.getRawAxis(4) / 2
    );

    if (RobotContainer.gamepad.getRawButton(2)) {
      RobotContainer.driveSubsystem.resetGyro();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.driveSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
