package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Convert;

public class Waypoint {
  // All units are in meters or degrees

  public static final Waypoint CENTER = new Waypoint(0, 0, 0);
  public static final Waypoint LEFT = new Waypoint(-1.5, 0, 0);
  public static final Waypoint UP = new Waypoint(0, 1.5, 0);
  public static final Waypoint RIGHT = new Waypoint(1.5, 0, 0);
  public static final Waypoint DOWN = new Waypoint(0, -1.5, 0);

  private final Pose2d pose;

  public Waypoint(double x, double y, double rotation) {
    pose = new Pose2d(x, y, Rotation2d.fromDegrees(rotation));
  }

  public static Waypoint inches(double x, double y, double rotation) {
    return new Waypoint(Convert.inchToMeter(x), Convert.inchToMeter(y), rotation);
  }

  public Pose2d getPose() {
    return pose;
  }

  public Pose2d getPoseWithoutRotation() {
    return new Pose2d(pose.getX(), pose.getY(), Rotation2d.fromDegrees(0));
  }
}
