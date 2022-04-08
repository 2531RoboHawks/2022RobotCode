package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.Constants.DigitalInputs;
import frc.robot.Constants.CAN;

public class StorageSubsystem extends SubsystemBase {
  private BetterSparkMaxBrushless beforeShooter = new BetterSparkMaxBrushless(CAN.ShooterStorage)
    .configureInverted(true);

  private DigitalInput switchBeforeShooter = new DigitalInput(DigitalInputs.BallStorage);

  public void setBeforeShooterPower(double power) {
    System.out.println("Before shooter: " + power);
    beforeShooter.setPower(power);
  }

  public void stopBeforeShooter() {
    beforeShooter.stop();
  }

  public boolean isBallBeforeShooter() {
    return !switchBeforeShooter.get();
  }
}
