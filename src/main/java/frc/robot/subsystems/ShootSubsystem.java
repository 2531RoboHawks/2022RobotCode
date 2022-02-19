package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PIDSettings;
import frc.robot.TalonUtils;

public class ShootSubsystem extends SubsystemBase {
    private CANSparkMax turret = new CANSparkMax(15, MotorType.kBrushless);
    private SparkMaxPIDController turretPid = turret.getPIDController();
    private static final PIDSettings turretPidSettings = new PIDSettings(0.1, 0, 0);
    private static final double turretGearRatio = 215.0 / 16.0;

    private TalonFX revwheel = new TalonFX(8);
    private static final PIDSettings revwheelPidSettings = new PIDSettings(0.15, 0.001, 0);

    private TalonFX intake = new TalonFX(9);
    private static final PIDSettings intakePidSettings = new PIDSettings(0.15, 0.001, 0);

    public ShootSubsystem() {
        turretPid.setP(turretPidSettings.kp);
        turretPid.setI(turretPidSettings.ki);
        turretPid.setD(turretPidSettings.kd);
        turret.getEncoder().setPosition(0);
        turret.setInverted(true);

        TalonUtils.configurePID(revwheel, revwheelPidSettings);
        TalonUtils.configurePID(intake, intakePidSettings);
    }

    public void setRevwheelRPM(double rpm) {
        // Max RPM: ~5600 RPM
        revwheel.set(ControlMode.Velocity, TalonUtils.rpmToSensorVelocity(rpm));
    }

    public double getRevwheelRPM() {
        return TalonUtils.sensorVelocityToRPM(revwheel.getSelectedSensorVelocity());
    }

    public void setIntakeRPM(double rpm) {
        intake.set(ControlMode.Velocity, TalonUtils.rpmToSensorVelocity(rpm));
    }

    public double getIntakeRPM() {
        return TalonUtils.sensorVelocityToRPM(intake.getSelectedSensorVelocity());
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
        revwheel.set(ControlMode.PercentOutput, 0);
        intake.set(ControlMode.PercentOutput, 0);
        turret.set(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Actual Revwheel RPM", getRevwheelRPM());
        SmartDashboard.putNumber("Actual Intake RPM", getIntakeRPM());
        SmartDashboard.putNumber("Actual Turret Positon", getTurretPosition());
    }
}
