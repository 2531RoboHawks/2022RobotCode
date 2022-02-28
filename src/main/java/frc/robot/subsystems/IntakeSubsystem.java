package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
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
        // TODO
        System.out.println("Intake power: " + rpm);
        intakeWheel.set(rpm);
    }

    public void setDown(boolean down) {
        System.out.println("Intake down: " + down);
        solenoid.set(down);
    }

    public void stop() {
        intakeWheel.stop();
    }

    @Override
    public void periodic() {

    }
}
