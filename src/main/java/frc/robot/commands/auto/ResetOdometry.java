package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Waypoint;
import frc.robot.subsystems.DriveSubsystem;

public class ResetOdometry extends InstantCommand {
  public ResetOdometry(DriveSubsystem driveSubsystem, Pose2d initialPose) {
    super(() -> {
      driveSubsystem.resetOdometry(initialPose);
    }, driveSubsystem);
  }

  public ResetOdometry(DriveSubsystem driveSubsystem, Waypoint waypoint) {
    this(driveSubsystem, waypoint.getPose());
  }

  public ResetOdometry(DriveSubsystem driveSubsystem) {
    this(driveSubsystem, new Pose2d());
  }
}
