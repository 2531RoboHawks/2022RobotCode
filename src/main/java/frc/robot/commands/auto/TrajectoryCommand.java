package frc.robot.commands.auto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
      driveSubsystem.getFeedforward(),
      driveSubsystem.getKinematics(),
      getXController(),
      getYController(),
      getThetaController(),
      maxVelocity,
      driveSubsystem.getFeedforwardPID(),
      driveSubsystem.getFeedforwardPID(),
      driveSubsystem.getFeedforwardPID(),
      driveSubsystem.getFeedforwardPID(),
      driveSubsystem::getWheelSpeeds,
      driveSubsystem::driveVoltages,
      driveSubsystem
    );

    this.driveSubsystem = driveSubsystem;
    this.trajectory = trajectory;
  }

  private static final PIDController getXController() {
    return new PIDController(1, 0, 0);
  }

  private static final PIDController getYController() {
    return getXController();
  }

  private static final ProfiledPIDController getThetaController() {
    return new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(maxAngularVelocity, maxAngularAcceleration));
  }

  public static TrajectoryCommand fromWaypoints(DriveSubsystem driveSubsystem, Waypoint... waypointsArray) {
    if (waypointsArray.length < 2) {
      throw new RuntimeException("forWaypoints called with too few waypoint arguments");
    }

    List<Pose2d> poses = new ArrayList<Pose2d>();
    for (Waypoint waypoint : waypointsArray) {
      poses.add(waypoint.getPose());
    }

    TrajectoryConfig config = new TrajectoryConfig(maxVelocity, maxAcceleration)
      .setKinematics(driveSubsystem.getKinematics());

    Trajectory trajectory = TrajectoryGenerator.generateTrajectory(poses, config);

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
