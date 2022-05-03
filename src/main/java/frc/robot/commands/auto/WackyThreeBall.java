package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.StorageSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class WackyThreeBall extends SequentialCommandGroup {
  private final DriveSubsystem driveSubsystem = RobotContainer.driveSubsystem;
  private final StorageSubsystem storageSubsystem = RobotContainer.storageSubsystem;
  private final IntakeSubsystem intakeSubsystem = RobotContainer.intakeSubsystem;
  private final VisionSubsystem visionSubsystem = RobotContainer.visionSubsystem;

  public WackyThreeBall() {
    addCommands(new TwoBallAuto(AutoConstants.distnaceToSecondBallShort));
    // addCommands(new DriveToWaypoint(driveSubsystem, new Pose2d(0, 0, new Rotation2d(AutoConstants.angleToThirdBallDegrees))).withTimeout(1));
    // addCommands(new ResetOdometry(driveSubsystem));
    // addCommands(new DriveToWaypoint(driveSubsystem, new Pose2d(AutoConstants.distanceToThirdBall, 0, new Rotation2d())));
  }
}
