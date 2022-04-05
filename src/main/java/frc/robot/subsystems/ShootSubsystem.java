package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;
import frc.robot.Constants.DigitalInputs;
import frc.robot.Constants.Motors;

public class ShootSubsystem extends SubsystemBase {
  private BetterTalonFX revwheel = new BetterTalonFX(Motors.ShooterRevwheel)
    .configureBrakes(false)
    .configureUnitsPerRevolution(1)
    .configureFeedforward(new SimpleMotorFeedforward(0.52166, 0.10843, 0.0062646), new PIDSettings(0.10962, 0, 0));

  private BetterSparkMaxBrushless storageBeforeShoot = new BetterSparkMaxBrushless(Motors.ShooterStorage)
    .configureInverted(true);

  private DigitalInput limitSwitch = new DigitalInput(DigitalInputs.BallStorage);

  public ShootSubsystem() {
    stopEverything();
  }

  public void setRevwheelRPM(double rpm) {
    // Max RPM: ~6540 RPM
    SmartDashboard.putNumber("Target Revwheel RPM", rpm);
    double revolutionsPerSecond = rpm / 60.0;
    revwheel.setLinearVelocityFeedforwardPID(revolutionsPerSecond);
  }

  public double getRevwheelRPM() {
    return revwheel.getRPM();
  }

  public void setStorageBeforeShootPower(double power) {
    System.out.println("Storage before shoot: " + power);
    storageBeforeShoot.setPower(power);
  }

  public void setStorageBeforeShootRunning(boolean running) {
    if (running) {
      setStorageBeforeShootPower(0.18);
    } else {
      setStorageBeforeShootPower(0);
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
    return !limitSwitch.get();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Actual Revwheel RPM", revwheel.getRPM());
    SmartDashboard.putBoolean("Limit Switch", limitSwitch.get());
    // SmartDashboard.putNumber("Revwheel Voltage", revwheel.getWPI().getBusVoltage());
    // SmartDashboard.putNumber("Revwheel Current", revwheel.getWPI().getSupplyCurrent());
  }
}
