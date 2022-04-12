package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.BetterTalonFX;
import frc.robot.Constants.DigitalInputs;
import frc.robot.Constants.ShootingConstants;
import frc.robot.Constants.CAN;

public class StorageSubsystem extends SubsystemBase {
  private BetterSparkMaxBrushless beforeShooter = new BetterSparkMaxBrushless(CAN.ShooterStorage)
    .configureInverted(true);

  private BetterTalonFX storageAfterIntake = new BetterTalonFX(CAN.IntakeStorage)
    .configureBrakes(false);

  private DigitalInput switchBeforeShooter = new DigitalInput(DigitalInputs.BallStorage);

  public void setBeforeShooterPower(double power) {
    System.out.println("Before shooter: " + power);
    beforeShooter.setVoltage(power);
  }

  public void stopBeforeShooter() {
    setBeforeShooterPower(0);
  }

  public boolean isBallBeforeShooter() {
    return !switchBeforeShooter.get();
  }

  public void setAfterIntakeRunning(boolean running) {
    System.out.println("Storage after intake: " + running);
    if (running) {
      storageAfterIntake.setVoltage(ShootingConstants.intakeStoragePower);
    } else {
      storageAfterIntake.stop();
    }
  }

  public void stopAfterIntake() {
    setAfterIntakeRunning(false);
  }
}
