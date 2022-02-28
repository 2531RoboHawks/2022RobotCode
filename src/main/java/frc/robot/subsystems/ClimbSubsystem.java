package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;

public class ClimbSubsystem extends SubsystemBase {
    public BetterTalonFX leftTalon = new BetterTalonFX(21);
    public BetterTalonFX rightTalon = new BetterTalonFX(22);
    private DoubleSolenoid solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);

    private static final PIDSettings talonPid = new PIDSettings(0.2, 0, 0);
    private static final double secondsFromNeutralToFull = 1;

    public ClimbSubsystem() {
        leftTalon.configurePID(talonPid);
        leftTalon.configureRamp(secondsFromNeutralToFull);

        rightTalon.configurePID(talonPid);
        rightTalon.configureRamp(secondsFromNeutralToFull);

        setPistonExtended(false);
    }

    public void setArmExtension(double sensorUnits) {
        if (sensorUnits < 0) {
            // System.out.println("sensorUnits should NOT BE LESS THAN ZERO: " + sensorUnits);
            sensorUnits = 0;
        }
        leftTalon.setFixedEncoderTarget(sensorUnits);
        rightTalon.setFixedEncoderTarget(sensorUnits);
    }

    public void setExtensionPercent(double percent) {
        leftTalon.setPower(percent);
        rightTalon.setPower(percent);
    }

    public void setPistonExtended(boolean extended) {
        solenoid.set(!extended ? Value.kForward : Value.kReverse);
    }
    public void togglePistonExtended() {
        solenoid.toggle();
    }

    public void stop() {
        leftTalon.stop();
        rightTalon.stop();
    }

    public void reset() {
        stop();
        leftTalon.zeroFixedEncoderTarget();
        rightTalon.zeroFixedEncoderTarget();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Climb", leftTalon.getPositionSensorUnits());
        SmartDashboard.putNumber("Right Climb", rightTalon.getPositionSensorUnits());
    }
}
