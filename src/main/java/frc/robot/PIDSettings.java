package frc.robot;

public class PIDSettings {
    // NOTE: This class is JSON-serialized and deserialized!
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
}
