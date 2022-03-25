// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class RevSetSpeed extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private double rpm;

  public RevSetSpeed(ShootSubsystem shootSubsystem, double rpm) {
    addRequirements(shootSubsystem);
    this.shootSubsystem = shootSubsystem;
    this.rpm = rpm;
  }

  @Override
  public void execute() {
    shootSubsystem.setRevwheelRPM(rpm);
  }

  @Override
  public void end(boolean interrupted) {
    shootSubsystem.stopEverything();
  }
}
