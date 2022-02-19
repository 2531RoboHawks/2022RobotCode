package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.TalonUtils;

public class IntakeSubsystem extends SubsystemBase {
    // TODO: CAN, BetterTalonFX
    private static TalonFX intake = new TalonFX(1);
    private static final double kp = 0.1;
    private static final double ki = 0.001;
    private static final double kd = 5;

    public IntakeSubsystem() {
        TalonUtils.configurePID(intake, kp, ki, kd);
    }

    public void setRPM(double rpm) {
        intake.set(ControlMode.Velocity, TalonUtils.rpmToSensorVelocity(rpm));
    }

    public double getRPM() {
        return TalonUtils.sensorVelocityToRPM(intake.getSelectedSensorVelocity());
    }

    public void stop() {
        intake.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void periodic() {

    }
}
