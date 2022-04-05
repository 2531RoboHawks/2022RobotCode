package frc.robot.commands.shooting;

public class RPMCalculator {
  public static double distancesToRPM(double inches) {
    return inches * 17.6 + 2886.0;
  }
}
