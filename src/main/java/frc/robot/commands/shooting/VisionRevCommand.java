// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.auto.AutoShoot;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class VisionRevCommand extends CommandBase {
  private VisionSubsystem visionSubsystem;
  private ShootSubsystem shootSubsystem;

  public VisionRevCommand(ShootSubsystem shootSubsystem, VisionSubsystem visionSubsystem) {
    addRequirements(shootSubsystem);
    this.visionSubsystem = visionSubsystem;
    this.shootSubsystem = shootSubsystem;
  }

  @Override
  public void initialize() {
    visionSubsystem.ensureEnabled();
    rpm = 4000;
  }

  private double rpm = 0;

  @Override
  public void execute() {
    if (visionSubsystem.isReady() && visionSubsystem.hasValidTarget()) {
      double distance = visionSubsystem.getDistance();
      rpm = RPMCalculator.distancesToRPM(distance);
    }

    shootSubsystem.setRevwheelRPM(rpm);
  }

  @Override
  public void end(boolean interrupted) {
    shootSubsystem.stopEverything();
    visionSubsystem.noLongerNeeded();
  }
}
