package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;

public class ClimbSubsystem extends SubsystemBase {
    // TODO
    private BetterTalonFX talon = new BetterTalonFX(0);
    private static final PIDSettings talonPid = new PIDSettings(0, 0, 0);
    private static final double secondsFromNeutralToFull = 1;

    public ClimbSubsystem() {
        talon.configurePID(talonPid);
        talon.getWPI().configClosedloopRamp(secondsFromNeutralToFull);
        talon.getWPI().configOpenloopRamp(secondsFromNeutralToFull);
    }

    public void setArmExtension(double sensorUnits) {
        talon.setFixedEncoderTarget(sensorUnits);
    }

    public void stopArm() {
        talon.stop();
    }

    public void stopAll() {
        stopAll();
    }

    public void reset() {
        stopAll();
        talon.zeroFixedEncoderTarget();
    }

    @Override
    public void periodic() {

    }
}
