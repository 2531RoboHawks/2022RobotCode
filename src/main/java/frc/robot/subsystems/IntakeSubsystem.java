package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMAXBrushless;
import frc.robot.PIDSettings;

public class IntakeSubsystem extends SubsystemBase {
    private BetterSparkMAXBrushless intakeWheel = new BetterSparkMAXBrushless(20);

    public IntakeSubsystem() {
        intakeWheel.configurePID(new PIDSettings(0.00006, 0, 0));
    }

    public void setRPM(double rpm) {
        // TODO
        intakeWheel.set(rpm);
    }

    public void stop() {
        intakeWheel.stop();
    }

    public void setDown(boolean down) {
        // TODO
    }

    @Override
    public void periodic() {

    }
}
