package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;

public class ClimbSubsystem extends SubsystemBase {
    // TODO
    private BetterTalonFX leftTalon = new BetterTalonFX(21);
    private BetterTalonFX rightTalon = new BetterTalonFX(22);

    private static final PIDSettings talonPid = new PIDSettings(0, 0, 0);
    private static final double secondsFromNeutralToFull = 1;

    public ClimbSubsystem() {
        leftTalon.configurePID(talonPid);
        leftTalon.configureRamp(secondsFromNeutralToFull);

        rightTalon.configurePID(talonPid);
        rightTalon.configureRamp(secondsFromNeutralToFull);
    }

    public void setArmExtension(double sensorUnits) {
        leftTalon.setFixedEncoderTarget(sensorUnits);
        rightTalon.setFixedEncoderTarget(sensorUnits);
    }

    public void stopArm() {
        leftTalon.stop();
        rightTalon.stop();
    }

    public void stopAll() {
        stopAll();
    }

    public void reset() {
        stopAll();
        leftTalon.zeroFixedEncoderTarget();
        rightTalon.zeroFixedEncoderTarget();
    }

    @Override
    public void periodic() {

    }
}
