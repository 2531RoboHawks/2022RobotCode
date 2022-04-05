package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

public class BetterSparkMaxBrushless {
  private CANSparkMax canSparkMax;
  private SparkMaxPIDController pidController;
  private RelativeEncoder encoder;

  public BetterSparkMaxBrushless(int id) {
    canSparkMax = new CANSparkMax(id, MotorType.kBrushless);
    encoder = canSparkMax.getEncoder();
    pidController = canSparkMax.getPIDController();
  }

  public BetterSparkMaxBrushless configureInverted(boolean inverted) {
    canSparkMax.setInverted(true);
    return this;
  }

  public void setPower(double power) {
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

  public void configurePID(PIDSettings pidSettings) {
    pidController.setP(pidSettings.kp);
    pidController.setI(pidSettings.ki);
    pidController.setD(pidSettings.kd);
  }

  public CANSparkMax getCanSparkMax() {
    return canSparkMax;
  }
}
