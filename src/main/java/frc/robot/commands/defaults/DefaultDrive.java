// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.InputUtils;
import frc.robot.RobotContainer;
import frc.robot.Constants.Controls;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDrive extends CommandBase {
  private DriveSubsystem driveSubsystem;
  private boolean fieldOriented = false;

  public DefaultDrive(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {

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
      xMultiplier = 1;
      yMultiplier = 1;
      rotationMultiplier = .75;
    } else {
      xMultiplier = .5;
      yMultiplier = .5;
      rotationMultiplier = .33;
    }

    double sideways = scale(RobotContainer.gamepad.getLeftX()) * xMultiplier;
    double forwards = scale(-RobotContainer.gamepad.getLeftY()) * yMultiplier;
    double rotation = scale(RobotContainer.gamepad.getRawAxis(4)) * rotationMultiplier;

    // if (RobotContainer.gamepad.getRawButtonPressed(Controls.ToggleFieldOriented)) {
    //   fieldOriented = !fieldOriented;
    //   System.out.println("Field oriented: " + fieldOriented);
    // }

    // if (RobotContainer.gamepad.getRawButtonPressed(Controls.ResetFieldOriented)) {
    //   driveSubsystem.zeroGyro();
    //   System.out.println("Reset field oriented");
    // }

    if (fieldOriented) {
      driveSubsystem.drivePercent(driveSubsystem.calculateFieldOriented(forwards, sideways, rotation));
    } else {
      driveSubsystem.drivePercent(driveSubsystem.calculateRobotOriented(forwards, sideways, rotation));
    }
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stop();
  }
}
