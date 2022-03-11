package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class Waypoints {
    private static Pose2d makeWaypoint(double forwards, double sideways, double rotation) {
        return new Pose2d(forwards, sideways, Rotation2d.fromDegrees(rotation));
    }

    // All units are in meters or degrees
    public static final Pose2d LEFT = makeWaypoint(-1.5, 0, 0);
    public static final Pose2d UP = makeWaypoint(0, 1.5, 0);
    public static final Pose2d RIGHT = makeWaypoint(1.5, 0, 0);
    public static final Pose2d DOWN = makeWaypoint(0, -1.5, 0);
}
