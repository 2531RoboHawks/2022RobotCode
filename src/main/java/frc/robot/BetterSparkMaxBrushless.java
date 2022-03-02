package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class BetterSparkMaxBrushless {
    private CANSparkMax canSparkMax;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;

    public BetterSparkMaxBrushless(int id) {
        canSparkMax = new CANSparkMax(id, MotorType.kBrushless);
        encoder = canSparkMax.getEncoder();
        pidController = canSparkMax.getPIDController();
    }

    public void setInverted(boolean inverted) {
        canSparkMax.setInverted(true);
    }

    public void set(double power) {
        canSparkMax.set(power);
    }

    public void stop() {
        canSparkMax.stopMotor();
    }

    public void setRPM(double rpm) {
        pidController.setReference(rpm, ControlType.kVelocity);
    }
    public double getRPM() {
        return encoder.getVelocity();
    }

    public void zero() {
        // TODO This probably doesn't work
        encoder.setPosition(0);
    }
    public void setPosition(double turns) {
        pidController.setReference(turns, ControlType.kPosition);
    }
    public double getPosition() {
        return encoder.getPosition();
    }

    public void configurePID(PIDSettings pidSettings) {
        pidController.setP(pidSettings.kp);
        pidController.setI(pidSettings.ki);
        pidController.setD(pidSettings.kd);
    }

    public void setMaxPIDOutput(double output) {
        pidController.setOutputRange(-output, output);
    }

    public CANSparkMax getCanSparkMax() {
        return canSparkMax;
    }
}
