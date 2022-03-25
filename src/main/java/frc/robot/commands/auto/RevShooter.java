// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class RevShooter extends CommandBase {
  private VisionSubsystem visionSubsystem;
  private ShootSubsystem shootSubsystem;

  public RevShooter(ShootSubsystem shootSubsystem, VisionSubsystem visionSubsystem) {
    addRequirements(shootSubsystem);
    this.visionSubsystem = visionSubsystem;
    this.shootSubsystem = shootSubsystem;
  }

  @Override
  public void initialize() {
    visionSubsystem.ensureEnabled();
  }

  private double calculateRPMForDistance(double inches) {
    return inches * 12 + 3440;
  }

  @Override
  public void execute() {
    if (!visionSubsystem.isReady()) {
      return;
    }

    if (visionSubsystem.hasValidTarget()) {
      double distance = visionSubsystem.getDistance();
      double rpm = calculateRPMForDistance(distance);
      shootSubsystem.setRevwheelRPM(rpm);
    }
  }

  @Override
  public void end(boolean interrupted) {
    shootSubsystem.stopEverything();
    visionSubsystem.noLongerNeeded();
  }
}
