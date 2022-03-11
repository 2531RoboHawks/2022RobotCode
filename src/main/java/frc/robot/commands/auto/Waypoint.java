package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class Waypoint {
    // All units are in meters or degrees
    public static final Waypoint LEFT = new Waypoint(-1.5, 0, 0);
    public static final Waypoint UP = new Waypoint(0, 1.5, 0);
    public static final Waypoint RIGHT = new Waypoint(1.5, 0, 0);
    public static final Waypoint DOWN = new Waypoint(0, -1.5, 0);

    private final Pose2d pose;

    public Waypoint(double forwards, double sideways, double rotation) {
        pose = new Pose2d(forwards, sideways, Rotation2d.fromDegrees(rotation));
    }

    public Pose2d getPose() {
        return pose;
    }
}
