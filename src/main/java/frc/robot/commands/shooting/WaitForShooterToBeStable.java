package frc.robot.commands.shooting;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class WaitForShooterToBeStable extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private ArrayList<Double> rpms = new ArrayList<>();
  private int period = 20;
  private double maxError = 10;
  private double maxTime = 2;
  private Timer timer = new Timer();

  public WaitForShooterToBeStable(ShootSubsystem shootSubsystem) {
    // intentionally not using requirements here -- this shouldn't interrupt something actually using the shooter
    this.shootSubsystem = shootSubsystem;
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
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
    if (timer.hasElapsed(maxTime)) {
      System.out.println("Too much time passed.");
      return true;
    }
    if (rpms.size() < period) {
      System.out.println("Not enough data.");
      return false;
    }

    double sum = 0;
    for (double i : rpms) {
      sum += i;
    }
    double average = sum / rpms.size();

    if (average < 500) {
      System.out.println("Too slow: " + average);
      return false;
    }

    double minimumAccepted = average - maxError;
    double maximumAccepted = average + maxError;
    for (double i : rpms) {
      if (i < minimumAccepted) {
        System.out.println("Min exceeded by " + (i - minimumAccepted));
        return false;
      }
      if (i > maximumAccepted) {
        System.out.println("Max exceeded by " + (maximumAccepted - i));
        return false;
      }
    }

    System.out.println("Seems stable! Current: " + shootSubsystem.getRevwheelRPM());
    return true;
  }
}
