package frc.robot.subsystems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;

public class ShootSubsystem extends SubsystemBase {
  private BetterTalonFX revwheel = new BetterTalonFX(15)
    .configureUnitsPerRevolution(1)
    .configureFeedforward(new SimpleMotorFeedforward(0.52166, 0.10843, 0.0062646), new PIDSettings(0.10962, 0, 0));

  private BetterSparkMaxBrushless storageBeforeShoot = new BetterSparkMaxBrushless(17)
    .configureInverted(true);

  private DigitalInput limitSwitch = new DigitalInput(0);

  public ShootSubsystem() {
    stopEverything();
  }

  public void setRevwheelRPM(double rpm) {
    // Max RPM: ~6540 RPM
    double revolutionsPerSecond = rpm / 60.0;
    revwheel.setLinearVelocityFeedforwardPID(revolutionsPerSecond);
  }

  public void setStorageBeforeShootRunning(boolean running) {
    System.out.println("Storage before shoot: " + running);
    if (running) {
      storageBeforeShoot.setPower(0.25);
    } else {
      storageBeforeShoot.stop();
    }
  }

  public void idleRevwheel() {
    revwheel.stop();
  }
  public void stopEverything() {
    idleRevwheel();
    setStorageBeforeShootRunning(false);
  }

  public boolean isBallInStorage() {
    return limitSwitch.get();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Actual Revwheel RPM", revwheel.getRPM());
    SmartDashboard.putBoolean("Limit Switch", limitSwitch.get());
    // SmartDashboard.putNumber("Revwheel Voltage", revwheel.getWPI().getBusVoltage());
    // SmartDashboard.putNumber("Revwheel Current", revwheel.getWPI().getSupplyCurrent());
  }
}
