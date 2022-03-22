// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutoShootCommand extends CommandBase {
  private VisionSubsystem visionSubsystem;
  private ShootSubsystem shootSubsystem;
  private IntakeSubsystem intakeSubsystem;
  private Timer timer = new Timer();

  public AutoShootCommand(ShootSubsystem shootSubsystem, VisionSubsystem visionSubsystem, IntakeSubsystem intakeSubsystem) {
    addRequirements(shootSubsystem, intakeSubsystem);
    this.visionSubsystem = visionSubsystem;
    this.shootSubsystem = shootSubsystem;
    this.intakeSubsystem = intakeSubsystem;
  }

  @Override
  public void initialize() {
    visionSubsystem.ensureEnabled();
    timer.reset();
    timer.stop();
  }

  private double calculateRPMForDistance(double inches) {
    return inches * 12 + 3440;
  }

  @Override
  public void execute() {
    if (!visionSubsystem.isReady()) {
      return;
    }

    // Start timer after limelight is ready
    timer.start();

    if (visionSubsystem.hasValidTarget()) {
      double distance = visionSubsystem.getDistance();
      double rpm = calculateRPMForDistance(distance);
      shootSubsystem.setRevwheelRPM(rpm);

      double startShootingAt = 2;
      double stopShootingAt = startShootingAt + 0.25;
      if (timer.hasElapsed(stopShootingAt)) {
        cancel();
      } else if (timer.hasElapsed(startShootingAt)) {
        shootSubsystem.setStorageBeforeShootRunning(true);
        intakeSubsystem.setStorageAfterIntakeRunning(true);
      }
    } else {
      timer.reset();
      shootSubsystem.setStorageBeforeShootRunning(false);
      intakeSubsystem.setStorageAfterIntakeRunning(false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shootSubsystem.stopEverything();
    intakeSubsystem.setStorageAfterIntakeRunning(false);
    visionSubsystem.noLongerNeeded();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
