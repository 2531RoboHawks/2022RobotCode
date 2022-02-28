package frc.robot;

public class InputUtils {
    public static double deadzone(double n) {
        if (Math.abs(n) < 0.1) {
            return 0;
        }
        return n;
    }
}
