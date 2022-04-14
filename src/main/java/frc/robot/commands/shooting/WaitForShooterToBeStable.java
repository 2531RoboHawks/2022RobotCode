package frc.robot.commands.shooting;

import java.util.ArrayList;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class WaitForShooterToBeStable extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private ArrayList<Double> rpms = new ArrayList<>();
  private int period = 10;
  private double maxError = 10;
  private Supplier<Boolean> readySupplier;

  public WaitForShooterToBeStable(Supplier<Boolean> readySupplier, ShootSubsystem shootSubsystem) {
    // intentionally not using requirements here -- this shouldn't interrupt something actually using the shooter
    this.shootSubsystem = shootSubsystem;
    this.readySupplier = readySupplier;
  }

  @Override
  public void initialize() {
    rpms.clear();
  }

  @Override
  public void execute() {
    if (readySupplier.get()) {
      rpms.add(shootSubsystem.getRevwheelRPM());
      if (rpms.size() > period) {
        rpms.remove(0);
      }
    }
  }

  @Override
  public boolean isFinished() {
    if (rpms.size() < period) {
      System.out.println("WaitStable: Not enough data");
      return false;
    }

    double sum = 0;
    for (double i : rpms) {
      sum += i;
    }
    double average = sum / rpms.size();

    if (average < 500) {
      System.out.println("WaitStable: Too slow: " + average);
      return false;
    }

    double minimumAccepted = average - maxError;
    double maximumAccepted = average + maxError;
    for (double i : rpms) {
      if (i < minimumAccepted) {
        System.out.println("WaitStable: Min exceeded by " + (i - minimumAccepted));
        return false;
      }
      if (i > maximumAccepted) {
        System.out.println("WaitStable: Max exceeded by " + (maximumAccepted - i));
        return false;
      }
    }

    System.out.println("WaitStable: Seems stable: " + shootSubsystem.getRevwheelRPM());
    return true;
  }
}
