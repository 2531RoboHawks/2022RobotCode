// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.PIDSettings;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
  private static final PIDSettings pid = new PIDSettings(0.2, 0, 0);

  private DriveSubsystem driveSubsystem;
  private boolean fieldOriented = true;

  public DriveCommand(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
  }

  @Override
  public void initialize() {
    driveSubsystem.setSettings(pid);
    driveSubsystem.reset();
  }

  private double scale(double n) {
    if (Math.abs(n) < 0.1) return 0;
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
    }

    if (RobotContainer.gamepad.getRawButtonPressed(Constants.Controls.ResetDrive)) {
      driveSubsystem.resetGyro();
      driveSubsystem.resetEncoders();
    }

    double x = RobotContainer.gamepad.getX();
    double y = -RobotContainer.gamepad.getY();
    double z = RobotContainer.gamepad.getRawAxis(4);

    driveSubsystem.driveTeleop(
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
