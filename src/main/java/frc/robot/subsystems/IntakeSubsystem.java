package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.PIDSettings;

public class IntakeSubsystem extends SubsystemBase {
    private BetterSparkMaxBrushless intakeWheel = new BetterSparkMaxBrushless(20);
    private Solenoid solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 3);

    public IntakeSubsystem() {
        intakeWheel.configurePID(new PIDSettings(0.00006, 0, 0));
        setDown(false);
    }

    public void setRPM(double rpm) {
        // TODO Broken
        System.out.println("Intake RPM: " + rpm);
        intakeWheel.setRPM(rpm);
    }
    public void setPower(double power) {
        System.out.println("Intake power: " + power);
        intakeWheel.set(power);
    }

    public void setDown(boolean down) {
        System.out.println("Intake down: " + down);
        solenoid.set(down);
    }
    public void toggleDown() {
      setDown(!isDown());
    }
    public boolean isDown() {
        return solenoid.get();
    }

    public void stop() {
        intakeWheel.stop();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Actual Intake RPM", intakeWheel.getRPM());
    }
}
