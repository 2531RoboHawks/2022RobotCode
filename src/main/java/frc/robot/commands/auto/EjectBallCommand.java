// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class EjectBallCommand extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private IntakeSubsystem intakeSubsystem;
  private Timer timer = new Timer();

  public EjectBallCommand(ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem) {
    addRequirements(shootSubsystem, intakeSubsystem);
    this.shootSubsystem = shootSubsystem;
    this.intakeSubsystem = intakeSubsystem;
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    shootSubsystem.setRevwheelRPM(4000);
    if (timer.hasElapsed(3)) {
      shootSubsystem.setStorageBeforeShootPower(0.8);
      intakeSubsystem.setStorageAfterIntakeRunning(true);
    }
  }

  @Override
  public void end(boolean interrupted) {
    shootSubsystem.stopEverything();
    intakeSubsystem.setStorageAfterIntakeRunning(false);
  }
}
