package frc.robot;

import edu.wpi.first.math.controller.PIDController;

public class PIDSettings {
  public final double kp;
  public final double ki;
  public final double kd;

  public PIDSettings() {
    this(0, 0, 0);
  }

  public PIDSettings(double kp, double ki, double kd) {
    this.kp = kp;
    this.ki = ki;
    this.kd = kd;
  }

  public PIDController toController() {
    return new PIDController(kp, ki, kd);
  }
}
