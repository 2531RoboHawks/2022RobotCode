package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class Waypoint {
  // All units are in meters or degrees
  public static final Waypoint CENTER = new Waypoint(0, 0, 0);
  public static final Waypoint LEFT = new Waypoint(-1.5, 0, 0);
  public static final Waypoint UP = new Waypoint(0, 1.5, 0);
  public static final Waypoint RIGHT = new Waypoint(1.5, 0, 0);
  public static final Waypoint DOWN = new Waypoint(0, -1.5, 0);

  public static final Waypoint RUMBLING_START = new Waypoint(7.71, 2.75, -90.00);
  public static final Waypoint RUMBLING_FIRST_BALL = new Waypoint(7.66, 0.72, -90.00);
  public static final Waypoint RUMBLING_SECOND_BALL = new Waypoint(5.11, 1.98, 180.00);
  public static final Waypoint RUMBLING_TERMINAL = new Waypoint(1.56, 1.60, -131.99);
  public static final Waypoint RUMBLING_FINAL_SHOT = RUMBLING_SECOND_BALL;

  private final Pose2d pose;

  public Waypoint(double x, double y, double rotation) {
    pose = new Pose2d(x, y, Rotation2d.fromDegrees(rotation));
  }

  public Pose2d getPose() {
    return pose;
  }
}
