package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RPMCalculator {
  static {
    SmartDashboard.putNumber("Test RPM", 0);
  }

  public static double inchesToRPM(double inches) {
    double testRPM = SmartDashboard.getNumber("Test RPM", 0);
    if (testRPM != 0) {
      System.out.println("USING TEST RPM!");
      return testRPM;
    }
    return inches * 8.59 + 4000.0;
  }
}
