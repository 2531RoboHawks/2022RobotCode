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

  public void setBeforeShooterPower(double volts) {
    System.out.println("Before shooter: " + volts);
    beforeShooter.setVoltage(volts);
  }

  public void stopBeforeShooter() {
    setBeforeShooterPower(0);
  }

  public boolean isBallBeforeShooter() {
    return !switchBeforeShooter.get();
  }

  public void setAfterIntakePower(double volts) {
    System.out.println("Ater intake: " + volts);
    storageAfterIntake.setVoltage(volts);
  }

  public void stopAfterIntake() {
    setAfterIntakePower(0);
  }
}
