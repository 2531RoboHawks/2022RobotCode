package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    // TODO
    private static TalonFX intake = new TalonFX(1);

    public IntakeSubsystem() {
        intake.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        intake.config_kP(0, 0.01);
        intake.config_kD(0, 0);
        intake.config_kI(0, 0);
    }

    public void stop() {

    }

    @Override
    public void periodic() {

    }
}
