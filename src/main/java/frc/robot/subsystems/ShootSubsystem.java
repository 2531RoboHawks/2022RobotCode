package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.TalonUtils;

public class ShootSubsystem extends SubsystemBase {
    private CANSparkMax turret = new CANSparkMax(10, MotorType.kBrushless);
    private SparkMaxPIDController turretPid = turret.getPIDController();
    private static final double turretKp = 6e-5;
    private static final double turretKd = 0;
    private static final double turretKi = 0;
    private static final double turretGearRatio = 215.0 / 16.0;

    private TalonFX revwheel = new TalonFX(8);
    private TalonFX intake = new TalonFX(9);    
    private static final double talonKp = 0.1;
    private static final double talonKd = 5;
    private static final double talonKi = 0.001;

    public ShootSubsystem() {
        turretPid.setP(turretKp);
        turretPid.setD(turretKd);
        turretPid.setI(turretKi);
        turret.getEncoder().setPosition(0);

        TalonUtils.configurePID(revwheel, talonKp, talonKd, talonKi);
        TalonUtils.configurePID(intake, talonKp, talonKd, talonKi);
    }

    public void setRevwheelPercent(double percent) {
        revwheel.set(ControlMode.PercentOutput, percent);
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
        turretPid.setReference(position, ControlType.kPosition);
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
    }
}
