// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutoShootCommand extends CommandBase {
  private VisionSubsystem visionSubsystem;
  private ShootSubsystem shootSubsystem;
  private IntakeSubsystem intakeSubsystem;
  private Timer timer = new Timer();
  private Timer finalTimer = new Timer();

  private static final double startShootingAfter = 2;
  private static final double stopShootingAfter = 3;

  public AutoShootCommand(ShootSubsystem shootSubsystem, VisionSubsystem visionSubsystem, IntakeSubsystem intakeSubsystem) {
    addRequirements(shootSubsystem, intakeSubsystem);
    this.visionSubsystem = visionSubsystem;
    this.shootSubsystem = shootSubsystem;
    this.intakeSubsystem = intakeSubsystem;
    SmartDashboard.putNumber("Testing RPM", 0);
  }

  @Override
  public void initialize() {
    visionSubsystem.ensureEnabled();
    timer.reset();
    timer.stop();
    finalTimer.reset();
    finalTimer.stop();
    rpm = 4000;
  }

  public static double calculateRPMForDistance(double inches) {
    // return SmartDashboard.getNumber("Testing RPM", 0);
    return inches * 17.6 + 2886.0;
  }

  private double rpm = 0;

  @Override
  public void execute() {
    if (!visionSubsystem.isReady()) {
      return;
    }

    // Start timer after limelight is ready
    timer.start();

    if (visionSubsystem.hasValidTarget()) {
      double distance = visionSubsystem.getDistance();
      rpm = calculateRPMForDistance(distance);

      if (timer.hasElapsed(startShootingAfter)) {
        finalTimer.start();
        shootSubsystem.setStorageBeforeShootRunning(true);
        intakeSubsystem.setStorageAfterIntakeRunning(true);
      }
    } else {
      timer.reset();
      shootSubsystem.setStorageBeforeShootRunning(false);
      intakeSubsystem.setStorageAfterIntakeRunning(false);
    }

    shootSubsystem.setRevwheelRPM(rpm);
  }

  @Override
  public void end(boolean interrupted) {
    shootSubsystem.stopEverything();
    intakeSubsystem.setStorageAfterIntakeRunning(false);
    visionSubsystem.noLongerNeeded();
  }

  @Override
  public boolean isFinished() {
    return finalTimer.hasElapsed(stopShootingAfter);
  }
}
