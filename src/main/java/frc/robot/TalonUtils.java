package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class TalonUtils {
    // TalonFX sensor velocity uses "raw sensor units per 100ms"
    // 2048 sensor units = 1 revolution

    public static double rpmToSensorVelocity(double rpm) {
        double revolutionsPerSecond = rpm / 60.0;
        double sensorUnitsPerSecond = revolutionsPerSecond * 2048.0;
        double sensorUnitsPer100MS = sensorUnitsPerSecond / 10.0;
        return sensorUnitsPer100MS;
    }

    public static double sensorVelocityToRPM(double sensorUnitsPer100MS) {
        double sensorUnitsPerSecond = sensorUnitsPer100MS * 10.0;
        double revolutionsPerSecond = sensorUnitsPerSecond / 2048.0;
        double revolutionsPerMinute = revolutionsPerSecond * 60.0;
        return revolutionsPerMinute;
    }

    public static void configurePID(TalonFX talon, double kp, double ki, double kd) {
        talon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        talon.config_kP(0, kp);
        talon.config_kI(0, ki);
        talon.config_kD(0, kd);
    }
}
