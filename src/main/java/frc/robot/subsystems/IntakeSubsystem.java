package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.PIDSettings;

public class IntakeSubsystem extends SubsystemBase {
    private BetterSparkMaxBrushless intakeWheel = new BetterSparkMaxBrushless(20);

    public IntakeSubsystem() {
        intakeWheel.configurePID(new PIDSettings(0.00006, 0, 0));
    }

    public void setRPM(double rpm) {
        // TODO
        intakeWheel.set(rpm);
    }

    public void setDown(boolean down) {
        // TODO
    }

    public void stop() {
        intakeWheel.stop();
    }

    @Override
    public void periodic() {

    }
}
