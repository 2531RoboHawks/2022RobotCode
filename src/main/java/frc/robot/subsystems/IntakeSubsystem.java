package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.PIDSettings;
import frc.robot.Constants.Solenoids;

public class IntakeSubsystem extends SubsystemBase {
  private boolean canRunIntake = false;
  private BetterSparkMaxBrushless intakeWheel = new BetterSparkMaxBrushless(20);
  private Solenoid solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Solenoids.Intake);

  public IntakeSubsystem() {
    intakeWheel.configurePID(new PIDSettings(0.0005, 0, 0));
    setDown(false);
  }

  public void disable() {
    canRunIntake = false;
    setDown(false);
  }

  public void enable() {
    canRunIntake = true;
  }

  private void setPower(double power) {
    System.out.println("Intake power: " + power);
    intakeWheel.set(power);
  }

  public void setDown(boolean down) {
    System.out.println("Intake down: " + down);
    solenoid.set(down);
    SmartDashboard.putBoolean("Intake Down", down);
    if (down && canRunIntake) {
      setPower(0.4);
    } else {
      stop();
    }
  }
  public boolean isDown() {
    return solenoid.get();
  }
  public void toggleDown() {
    setDown(!isDown());
  }

  public void stop() {
    intakeWheel.stop();
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("Actual Intake RPM", intakeWheel.getRPM());
  }
}
