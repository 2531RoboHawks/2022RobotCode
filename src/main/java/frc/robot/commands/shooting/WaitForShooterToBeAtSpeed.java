package frc.robot.commands.shooting;

import java.util.ArrayList;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class WaitForShooterToBeAtSpeed extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private double maxError = 10;
  private double period = 5;
  private ArrayList<Double> rpms = new ArrayList<>();
  private Supplier<SuppliedRPM> rpmSupplier;

  public WaitForShooterToBeAtSpeed(Supplier<SuppliedRPM> rpmSupplier, ShootSubsystem shootSubsystem) {
    // intentionally not using requirements here -- this shouldn't interrupt something actually using the shooter
    this.shootSubsystem = shootSubsystem;
    this.rpmSupplier = rpmSupplier;
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
      System.out.println("WaitSpeed: not enough data");
      return false;
    }

    SuppliedRPM suppliedRPM = rpmSupplier.get();
    if (!suppliedRPM.isReady()) {
      System.out.println("WaitSpeed: not ready");
      return false;
    }

    double targetRPM = suppliedRPM.getRPM();
    for (double i : rpms) {
      double error = Math.abs(i - targetRPM);
      if (error > maxError) {
        System.out.println("WaitSpeed: Error range exceeded: " + error);
        return false;
      }
    }
    System.out.println("WaitSpeed: Done");
    return true;
  }
}
