package frc.robot.commands.shooting;

public class SuppliedRPM {
  private final boolean ready;
  private final double rpm;

  public SuppliedRPM(double rpm, boolean ready) {
    this.rpm = rpm;
    this.ready = ready;
  }

  public SuppliedRPM(double rpm) {
    this(rpm, true);
  }

  public double getRPM() {
    return rpm;
  }

  public boolean isReady() {
    return ready;
  }
}
