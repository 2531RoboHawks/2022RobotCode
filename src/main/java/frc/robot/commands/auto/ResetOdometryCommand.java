package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveSubsystem;

public class ResetOdometryCommand extends InstantCommand {
  public ResetOdometryCommand(DriveSubsystem driveSubsystem, Pose2d initialPose) {
    super(() -> {
      driveSubsystem.resetOdometry(initialPose);
    }, driveSubsystem);
  }

  public ResetOdometryCommand(DriveSubsystem driveSubsystem, Waypoint waypoint) {
    this(driveSubsystem, waypoint.getPose());
  }

  public ResetOdometryCommand(DriveSubsystem driveSubsystem) {
    this(driveSubsystem, new Pose2d());
  }
}
