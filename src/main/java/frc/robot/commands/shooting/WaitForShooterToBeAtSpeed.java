package frc.robot.commands.shooting;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class WaitForShooterToBeAtSpeed extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private double maxError = 10;
  private double period = 5;
  private ArrayList<Double> rpms = new ArrayList<>();
  private double targetRpm;

  public WaitForShooterToBeAtSpeed(ShootSubsystem shootSubsystem, double rpm) {
    // intentionally not using requirements here -- this shouldn't interrupt something actually using the shooter
    this.shootSubsystem = shootSubsystem;
    this.targetRpm = rpm;
  }

  @Override
  public void initialize() {
    rpms.clear();
  }

  @Override
  public void execute() {
    rpms.add(shootSubsystem.getRevwheelRPM());
    if (rpms.size() > period) {
      rpms.remove(0);
    }
  }

  @Override
  public boolean isFinished() {
    if (rpms.size() < period) {
      return false;
    }
    for (double i : rpms) {
      double error = Math.abs(i - targetRpm);
      if (error > maxError) {
        return false;
      }
    }
    return true;
  }
}
