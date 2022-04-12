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
import frc.robot.Waypoint;
import frc.robot.subsystems.DriveSubsystem;

public class DriveTrajectory extends MecanumControllerCommand {
  // in meters/sec
  private static final double maxVelocity = 1;
  private static final double maxAcceleration = 1;

  // in radians/sec
  private static final double maxAngularVelocity = 1.5;
  private static final double maxAngularAcceleration = 1.5;

  private DriveSubsystem driveSubsystem;
  private Trajectory trajectory;
  private boolean shouldResetOdometry = false;

  public DriveTrajectory(Trajectory trajectory, DriveSubsystem driveSubsystem) {
    super(
      trajectory,
      driveSubsystem::getPose,
      driveSubsystem.getKinematics(),
      getXController(),
      getYController(),
      getThetaController(),
      maxVelocity,
      driveSubsystem::driveWheelSpeeds,
      driveSubsystem
    );

    this.driveSubsystem = driveSubsystem;
    this.trajectory = trajectory;
  }

  private static final PIDController getXController() {
    return new PIDController(1, 0, 0);
  }

  private static final PIDController getYController() {
    return new PIDController(1, 0, 0);
  }

  private static final ProfiledPIDController getThetaController() {
    return new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(maxAngularVelocity, maxAngularAcceleration));
  }

  public static DriveTrajectory fromWaypoints(DriveSubsystem driveSubsystem, Waypoint... waypointsArray) {
    if (waypointsArray.length < 2) {
      throw new IllegalArgumentException("forWaypoints called with too few waypoint arguments");
    }

    List<Pose2d> poses = new ArrayList<Pose2d>();
    for (Waypoint waypoint : waypointsArray) {
      poses.add(waypoint.getPose());
    }

    Pose2d start = poses.get(0);
    List<Translation2d> interior = new ArrayList<>();
    if (poses.size() > 2) {
      for (int i = 1; i < poses.size() - 1; i++) {
        interior.add(poses.get(i).getTranslation());
      }
    }
    Pose2d end = poses.get(poses.size() - 1);

    TrajectoryConfig config = new TrajectoryConfig(maxVelocity, maxAcceleration)
      .setKinematics(driveSubsystem.getKinematics());

    Trajectory trajectory = TrajectoryGenerator.generateTrajectory(start, interior, end, config);

    // Warning may be preferable to robot failing to start.
    // if (trajectory.getTotalTimeSeconds() == 0) {
    //   throw new IllegalArgumentException("Trajectory is invalid: it is zero seconds long. There may be a more detailed message above.");
    // }

    return new DriveTrajectory(trajectory, driveSubsystem);
  }

  public DriveTrajectory resetOdometry() {
    shouldResetOdometry = true;
    return this;
  }

  @Override
  public void initialize() {
    super.initialize();
    startupResetOdometry();
  }

  public void startupResetOdometry() {
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
