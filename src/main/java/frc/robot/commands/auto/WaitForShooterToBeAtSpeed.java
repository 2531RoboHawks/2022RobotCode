package frc.robot.commands.auto;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class WaitForShooterToBeAtSpeed extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private double maxError = 10;
  private double rpm;

  public WaitForShooterToBeAtSpeed(ShootSubsystem shootSubsystem, double rpm) {
    // intentionally not using requirements here -- this shouldn't interrupt something actually using the shooter
    this.shootSubsystem = shootSubsystem;
    this.rpm = rpm;
  }

  @Override
  public boolean isFinished() {
    return Math.abs(shootSubsystem.getRevwheelRPM() - rpm) < maxError;
  }
}
