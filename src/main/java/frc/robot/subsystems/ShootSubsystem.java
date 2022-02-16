package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShootSubsystem extends SubsystemBase {
    private CANSparkMax turret = new CANSparkMax(10, MotorType.kBrushless);
    private SparkMaxPIDController turretPid = turret.getPIDController();

    private TalonFX revwheel = new TalonFX(8);

    public ShootSubsystem() {
        // for safety, might not be actually needed???
        turret.getEncoder().setPosition(0);

        // TODO tune
        turretPid.setP(6e-5);
        turretPid.setD(0);
        turretPid.setI(0);

        revwheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        // TODO tune
        revwheel.config_kP(0, 0.01);
        revwheel.config_kD(0, 0);
        revwheel.config_kI(0, 0);
    }

    public void setRevwheelRPM(double rpm) {
        double revolutionsPerSecond = rpm / 60.0;
        double sensorUnitsPerSecond = revolutionsPerSecond * 2048.0;
        double sensorUnitsPer100MS = sensorUnitsPerSecond / 10.0;
        revwheel.set(ControlMode.Velocity, sensorUnitsPer100MS);
    }

    public void setTurretPosition(double position) {
        turretPid.setReference(position, ControlType.kPosition);
    }

    public void stopEverything() {
        revwheel.set(ControlMode.PercentOutput, 0);
        turret.set(0);
    }

    @Override
    public void periodic() {

    }
}
