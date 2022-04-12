package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

import com.revrobotics.RelativeEncoder;

public class BetterSparkMaxBrushless {
  private CANSparkMax canSparkMax;
  private RelativeEncoder integratedEncoder;

  private SimpleMotorFeedforward feedforward;
  private PIDController feedforwardPID;

  public BetterSparkMaxBrushless(int id) {
    canSparkMax = new CANSparkMax(id, MotorType.kBrushless);
    integratedEncoder = canSparkMax.getEncoder();
  }

  public BetterSparkMaxBrushless configureInverted(boolean inverted) {
    canSparkMax.setInverted(true);
    return this;
  }

  public void setPower(double power) {
    canSparkMax.set(power);
  }

  public void setVoltage(double voltage) {
    canSparkMax.setVoltage(voltage);
  }

  public void stop() {
    canSparkMax.stopMotor();
  }

  public double getRPM() {
    return integratedEncoder.getVelocity();
  }

  public void setRPMFeedforwardPID(double rpm) {
    double revolutionsPerSecond = rpm / 60.0;
    canSparkMax.setVoltage(feedforward.calculate(revolutionsPerSecond) + feedforwardPID.calculate(getRPM(), revolutionsPerSecond));
  }

  public void configureFeedforward(SimpleMotorFeedforward feedforward, PIDSettings pidSettings) {
    this.feedforward = feedforward;
    this.feedforwardPID = pidSettings.toController();
  }

  public CANSparkMax getCanSparkMax() {
    return canSparkMax;
  }
}
