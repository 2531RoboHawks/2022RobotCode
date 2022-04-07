// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.defaults;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.InputUtils;
import frc.robot.RobotContainer;
import frc.robot.Constants.Controls;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDrive extends CommandBase {
  private DriveSubsystem driveSubsystem;

  public DefaultDrive(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {
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
    boolean slow = RobotContainer.gamepad.getRawButton(Controls.Slow);
    boolean turbo = RobotContainer.gamepad.getRawButton(Controls.Turbo);
    if (slow) {
      xMultiplier = .25;
      yMultiplier = .25;
      rotationMultiplier = .25;
    } else if (turbo) {
      xMultiplier = .75;
      yMultiplier = .75;
      rotationMultiplier = .75;
    } else {
      xMultiplier = .5;
      yMultiplier = .5;
      rotationMultiplier = .33;
    }

    double x = RobotContainer.gamepad.getLeftX();
    double y = -RobotContainer.gamepad.getLeftY();
    double z = RobotContainer.gamepad.getRawAxis(4);

    driveSubsystem.drivePercent(driveSubsystem.calculateRobotOriented(
      scale(y) * yMultiplier,
      scale(x) * xMultiplier,
      scale(z) * rotationMultiplier
    ));
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
