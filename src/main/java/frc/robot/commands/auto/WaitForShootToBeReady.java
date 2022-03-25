package frc.robot.commands.auto;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class WaitForShootToBeReady extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private ArrayList<Double> rpms = new ArrayList<>();
  private int period = 40;
  private double maxError = 20;
  private double maxTime = 3;
  private Timer timer = new Timer();

  public WaitForShootToBeReady(ShootSubsystem shootSubsystem) {
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

    System.out.println("Seems stable!");
    return true;
  }
}
