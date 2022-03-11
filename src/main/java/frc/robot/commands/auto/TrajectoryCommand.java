package frc.robot.commands.auto;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.MecanumControllerCommand;
import frc.robot.subsystems.DriveSubsystem;

public class TrajectoryCommand extends MecanumControllerCommand {
    // in meters/sec
    private static final double maxVelocity = 2;
    private static final double maxAcceleration = 1;

    // in radians/sec
    private static final double maxAngularVelocity = 3;
    private static final double maxAngularAcceleration = 3;

    private DriveSubsystem driveSubsystem;
    private Trajectory trajectory;
    private boolean shouldResetOdometry = false;

    public TrajectoryCommand(Trajectory trajectory, DriveSubsystem driveSubsystem) {
        super(
            trajectory,
            driveSubsystem::getPose,
            driveSubsystem.kinematics,
            new PIDController(1, 0, 0),
            new PIDController(1, 0, 0),
            new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(maxAngularVelocity, maxAngularAcceleration)),
            maxVelocity,
            driveSubsystem::driveWheelSpeeds,
            driveSubsystem
        );

        this.driveSubsystem = driveSubsystem;
        this.trajectory = trajectory;
    }

    public static TrajectoryCommand fromWaypoints(DriveSubsystem driveSubsystem, Pose2d... waypointsArray) {
        List<Pose2d> waypoints = List.of(waypointsArray);
        if (waypoints.size() < 2) {
            throw new RuntimeException("forWaypoints called with too few waypoint arguments");
        }

        TrajectoryConfig trajectoryConfig = new TrajectoryConfig(maxVelocity, maxAcceleration)
            .setKinematics(driveSubsystem.kinematics);

        Pose2d initial = waypoints.get(0);
        Pose2d ending = waypoints.get(waypoints.size() - 1);
        List<Translation2d> middlePoints = new ArrayList<>();
        if (waypoints.size() > 2) {
            for (int i = 1; i < waypoints.size() - 1; i++) {
                middlePoints.add(waypoints.get(i).getTranslation());
            }
        }

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            initial,
            middlePoints,
            ending,
            trajectoryConfig
        );

        return new TrajectoryCommand(trajectory, driveSubsystem);
    }

    public TrajectoryCommand resetOdometry() {
        shouldResetOdometry = true;
        return this;
    }

    @Override
    public void initialize() {
        super.initialize();
        if (shouldResetOdometry) {
            driveSubsystem.resetOdometry(trajectory.getInitialPose());
        }
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        driveSubsystem.stop();
    }
}
