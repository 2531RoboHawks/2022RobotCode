// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutoShootCommand extends CommandBase {
  private VisionSubsystem visionSubsystem;
  private ShootSubsystem shootSubsystem;
  private Timer timer = new Timer();

  public AutoShootCommand(ShootSubsystem shootSubsystem, VisionSubsystem visionSubsystem) {
    addRequirements(shootSubsystem, visionSubsystem);
    this.visionSubsystem = visionSubsystem;
    this.shootSubsystem = shootSubsystem;
  }

  @Override
  public void initialize() {
    visionSubsystem.ensureEnabled();
    timer.reset();
    timer.stop();
  }

  private double calculateRPMForDistance(double inches) {
    // TODO :)
    // return 3000 + inches * 50;
    return 5000;
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

      double startShootingAt = 5;
      double shootDuration = 1;
      if (timer.hasElapsed(startShootingAt + shootDuration)) {
        cancel();
      } else if (timer.hasElapsed(startShootingAt)) {
        shootSubsystem.setElevatorPercent(0.5);
      }
    } else {
      timer.reset();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shootSubsystem.stopEverything();
    visionSubsystem.noLongerNeeded();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
