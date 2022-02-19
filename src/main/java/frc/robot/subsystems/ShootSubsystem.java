package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;

public class ShootSubsystem extends SubsystemBase {
    private CANSparkMax turret = new CANSparkMax(15, MotorType.kBrushless);
    private SparkMaxPIDController turretPid = turret.getPIDController();
    private static final PIDSettings turretPidSettings = new PIDSettings(0.2, 0, 0);
    private static final double turretMaxPower = 0.2;
    private static final double turretGearRatio = 215.0 / 16.0;

    private BetterTalonFX revwheel = new BetterTalonFX(8);
    private static final PIDSettings revwheelPidSettings = new PIDSettings(0.15, 0.001, 0);

    private BetterTalonFX intake = new BetterTalonFX(9);
    private static final PIDSettings intakePidSettings = new PIDSettings(0.1, 0.001, 5);

    public ShootSubsystem() {
        turretPid.setP(turretPidSettings.kp);
        turretPid.setI(turretPidSettings.ki);
        turretPid.setD(turretPidSettings.kd);
        turretPid.setOutputRange(-turretMaxPower, turretMaxPower);
        // turret.getEncoder().setPosition(0);
        turret.setInverted(true);

        revwheel.configurePID(revwheelPidSettings);

        intake.setInverted(true);
        intake.configurePID(intakePidSettings);
    }

    public void setRevwheelRPM(double rpm) {
        // Max RPM: ~5600 RPM
        revwheel.setRPM(rpm);
    }

    public void setRevwheelPercent(double percent) {
        revwheel.setPower(percent);
    }

    public double getRevwheelRPM() {
        return revwheel.getRPM();
    }

    public void setIntakeRPM(double rpm) {
        intake.setRPM(rpm);
    }

    public void setIntakePercent(double percent) {
        intake.setPower(percent);
    }

    public double getIntakeRPM() {
        return intake.getRPM();
    }

    public void setTurretPosition(double position) {
        turretPid.setReference(position, CANSparkMax.ControlType.kPosition);
    }

    public void setTurretPercent(double percent) {
        turret.set(percent);
    }

    public double getTurretPosition() {
        return turret.getEncoder().getPosition();
    }

    public void stopEverything() {
        revwheel.stop();
        intake.stop();
        turret.set(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Actual Revwheel RPM", getRevwheelRPM());
        SmartDashboard.putNumber("Actual Intake RPM", getIntakeRPM());
        SmartDashboard.putNumber("Actual Turret Positon", getTurretPosition());
    }
}
