package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.Constants.CAN;
import frc.robot.Constants.Solenoids;

public class IntakeSubsystem extends SubsystemBase {
  private BetterSparkMaxBrushless intakeWheel = new BetterSparkMaxBrushless(CAN.IntakeSpinner);
  private Solenoid solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Solenoids.Intake);

  public IntakeSubsystem() {
    bringUpAndStopSpinning();
  }

  public void setSpinning(boolean spinning) {
    double power = spinning ? 0.4 : 0;
    System.out.println("Intake power: " + power);
    intakeWheel.setPower(power);
  }
  public void stopSpinning() {
    intakeWheel.stop();
  }

  public void setDown(boolean down) {
    System.out.println("Intake down: " + down);
    solenoid.set(down);
    SmartDashboard.putBoolean("Intake Down", down);
  }
  public boolean isDown() {
    return solenoid.get();
  }
  public void toggleDown() {
    setDown(!isDown());
  }

  public void bringUpAndStopSpinning() {
    setDown(false);
    setSpinning(false);
  }

  public void putDownAndSpin() {
    setDown(true);
    setSpinning(true);
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("Actual Intake RPM", intakeWheel.getRPM());
  }
}
