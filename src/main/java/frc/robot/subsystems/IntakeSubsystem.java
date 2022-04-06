package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.BetterTalonFX;
import frc.robot.Constants.CAN;
import frc.robot.Constants.Solenoids;

public class IntakeSubsystem extends SubsystemBase {
  private BetterSparkMaxBrushless intakeWheel = new BetterSparkMaxBrushless(CAN.IntakeSpinner);
  private BetterTalonFX storageAfterIntake = new BetterTalonFX(CAN.IntakeStorage)
    .configureBrakes(false);
  private Solenoid solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Solenoids.Intake);

  public IntakeSubsystem() {
    setDown(false);
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

  public void setStorageAfterIntakeRunning(boolean running) {
    System.out.println("Storage after intake: " + running);
    if (running) {
      // TODO: tune
      storageAfterIntake.setPower(0.17);
    } else {
      storageAfterIntake.stop();
    }
  }

  public void setEverything(boolean running) {
    setDown(running);
    setSpinning(running);
    setStorageAfterIntakeRunning(running);
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("Actual Intake RPM", intakeWheel.getRPM());
  }
}
