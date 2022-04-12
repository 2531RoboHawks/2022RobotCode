package frc.robot.subsystems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;
import frc.robot.Constants.DigitalInputs;
import frc.robot.Constants.CAN;

public class StorageSubsystem extends SubsystemBase {
  private BetterSparkMaxBrushless beforeShooter = new BetterSparkMaxBrushless(CAN.ShooterStorage)
    .configureFeedforward(new SimpleMotorFeedforward(0.21484, 0.12234, 0.002726), new PIDSettings(0.056956, 0, 0))
    .configureInverted(true);

  private BetterTalonFX storageAfterIntake = new BetterTalonFX(CAN.IntakeStorage)
    .configureBrakes(true);

  private DigitalInput switchBeforeShooter = new DigitalInput(DigitalInputs.BallStorage);

  public boolean isBallBeforeShooter() {
    return !switchBeforeShooter.get();
  }

  public void setBeforeShooterPower(double volts) {
    System.out.println("Before shooter volts: " + volts);
    beforeShooter.setVoltage(volts);
  }

  public void setBeforeShooterRPM(double rpm) {
    System.out.println("Before intake RPM: " + rpm);
    beforeShooter.setRPMFeedforwardPID(rpm);
  }

  public void stopBeforeShooter() {
    System.out.println("Before intake: stopped");
    beforeShooter.stop();
  }

  public void setAfterIntakePower(double volts) {
    System.out.println("Ater intake volts: " + volts);
    storageAfterIntake.setVoltage(volts);
  }

  public void stopAfterIntake() {
    System.out.println("After intake: stopped");
    storageAfterIntake.stop();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Storage befoer shooter RPM", beforeShooter.getRPM());
  }
}
