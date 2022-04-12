// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooting;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class RevShooterToSpeed extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private Supplier<Double> rpmSupplier;

  public RevShooterToSpeed(Supplier<Double> rpmSupplier, ShootSubsystem shootSubsystem) {
    addRequirements(shootSubsystem);
    this.shootSubsystem = shootSubsystem;
    this.rpmSupplier = rpmSupplier;
  }

  @Override
  public void execute() {
    shootSubsystem.setRevwheelRPM(rpmSupplier.get());
  }

  @Override
  public void end(boolean interrupted) {
    shootSubsystem.idleRevwheel();
  }
}
