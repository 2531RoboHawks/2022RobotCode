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

  private BetterTalonFX afterIntake = new BetterTalonFX(CAN.IntakeStorage)
    .configureBrakes(true);

  private DigitalInput switchBeforeShooter = new DigitalInput(DigitalInputs.BallStorage);

  public boolean isBallBeforeShooter() {
    return !switchBeforeShooter.get();
  }

  public void setBeforeShooterPower(double power) {
    beforeShooter.setPower(power);
  }

  public void setBeforeShooterVoltage(double volts) {
    beforeShooter.setVoltage(volts);
  }

  public void setBeforeShooterRPM(double rpm) {
    beforeShooter.setRPMFeedforwardPID(rpm);
  }

  public void stopBeforeShooter() {
    beforeShooter.stop();
  }

  public void setAfterIntakePower(double power) {
    afterIntake.setPower(power);
  }

  public void setAfterIntakeVoltsVoltage(double volts) {
    afterIntake.setVoltage(volts);
  }

  public void stopAfterIntake() {
    afterIntake.stop();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Storage befoer shooter RPM", beforeShooter.getRPM());
    // SmartDashboard.putBoolean("Has Ball", isBallBeforeShooter());
  }
}
