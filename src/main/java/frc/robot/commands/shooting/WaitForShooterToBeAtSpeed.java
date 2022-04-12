package frc.robot.commands.shooting;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.ShootSubsystem;

public class WaitForShooterToBeAtSpeed extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private double maxError = 10;
  private double period = 5;
  private ArrayList<Double> rpms = new ArrayList<>();
  private double targetRpm;
  private Timer timer = new Timer();

  public WaitForShooterToBeAtSpeed(double rpm, ShootSubsystem shootSubsystem) {
    // intentionally not using requirements here -- this shouldn't interrupt something actually using the shooter
    this.shootSubsystem = shootSubsystem;
    this.targetRpm = rpm;
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
    if (timer.hasElapsed(ShootingConstants.waitForShooterToReachSpeedTimeout)) {
      System.out.println("Timed out");
      return true;
    }
    if (rpms.size() < period) {
      System.out.println("Too little data");
      return false;
    }
    for (double i : rpms) {
      double error = Math.abs(i - targetRpm);
      System.out.println(i);
      if (error > maxError) {
        System.out.println("Error range exceeded: " + error);
        return false;
      }
    }
    System.out.println("Done");
    return true;
  }
}
