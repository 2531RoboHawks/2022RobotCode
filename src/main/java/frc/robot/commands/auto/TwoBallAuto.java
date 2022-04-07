package frc.robot.commands.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.Waypoint;
import frc.robot.Constants.ShootingConstants;
import frc.robot.commands.PutIntakeDownAndSpin;
import frc.robot.commands.shooting.MoveBallToShooter;
import frc.robot.commands.shooting.PrepareToShootBall;
import frc.robot.commands.shooting.RevShooterToSpeedThenNeutral;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TwoBallAuto extends SequentialCommandGroup {
  private static final Waypoint start = new Waypoint(7.06, 4.79, 133.57);
  private static final Waypoint shoot = new Waypoint(7.06 + Units.inchesToMeters(ShootingConstants.highGoalOptimalDistance), 4.79, 133.57);

  private final DriveSubsystem driveSubsystem = RobotContainer.driveSubsystem;
  private final ShootSubsystem shootSubsystem = RobotContainer.shootSubsystem;
  private final StorageSubsystem storageSubsystem = RobotContainer.storageSubsystem;
  private final IntakeSubsystem intakeSubsystem = RobotContainer.intakeSubsystem;
  private final VisionSubsystem visionSubsystem = RobotContainer.visionSubsystem;

  public TwoBallAuto() {
    addCommands(new ResetOdometry(driveSubsystem, start));
    addCommands(new DriveToWaypoint(driveSubsystem, shoot));
    addCommands(new RevShooterToSpeedThenNeutral(ShootingConstants.highGoalOptimalRPM, shootSubsystem));
    addCommands(new MoveBallToShooter(storageSubsystem));
    addCommands(
      new SequentialCommandGroup(
        new ParallelCommandGroup(
          new SequentialCommandGroup(
            // new DriveToWaypoint(driveSubsystem, Units.inchesToMeters(80), 0, 0),
            new WaitCommand(1),
            new DriveToWaypoint(driveSubsystem, shoot)
          ),
          new PrepareToShootBall(storageSubsystem)
        ),
        new RevShooterToSpeedThenNeutral(ShootingConstants.highGoalOptimalRPM, shootSubsystem),
        new MoveBallToShooter(storageSubsystem)
      ).deadlineWith(new PutIntakeDownAndSpin(intakeSubsystem))
    );
  }
}
