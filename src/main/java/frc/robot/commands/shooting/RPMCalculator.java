package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RPMCalculator {
  static {
    SmartDashboard.putNumber("Test RPM", 0);
  }

  public static double inchesToRPM(double inches) {
    // return SmartDashboard.getNumber("Test RPM", 0);
    return inches * 10 + 3560.0;
  }
}
