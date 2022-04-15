package frc.robot.commands.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.Waypoint;
import frc.robot.Constants.ShootingConstants;
import frc.robot.commands.PutIntakeDownAndSpin;
import frc.robot.commands.shooting.LoadBallIntoStorage;
import frc.robot.commands.shooting.ShootTwoBalls;
import frc.robot.commands.shooting.VisionAimAndShootTwoBalls;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TwoBallAuto extends SequentialCommandGroup {
  private final DriveSubsystem driveSubsystem = RobotContainer.driveSubsystem;
  private final ShootSubsystem shootSubsystem = RobotContainer.shootSubsystem;
  private final StorageSubsystem storageSubsystem = RobotContainer.storageSubsystem;
  private final IntakeSubsystem intakeSubsystem = RobotContainer.intakeSubsystem;
  private final VisionSubsystem visionSubsystem = RobotContainer.visionSubsystem;

  private final double pickUpBallDistance = Units.inchesToMeters(80);
  private final double moveTimeout = 2;
  private final double rpm = 4200;

  public TwoBallAuto() {
    addCommands(new ParallelDeadlineGroup(
      new SequentialCommandGroup(
        new WaitCommand(1.5),
        new DriveToWaypoint(driveSubsystem, new Waypoint(pickUpBallDistance, 0, 0))
          .withTimeout(moveTimeout),
        new WaitCommand(0.5),
        new DriveToWaypoint(driveSubsystem, new Waypoint(-pickUpBallDistance, 0, 0))
          .withMaxVelocity(3)
          .withTimeout(moveTimeout)
      ),
      new PutIntakeDownAndSpin(intakeSubsystem),
      new LoadBallIntoStorage(storageSubsystem)
    ));
    addCommands(new ParallelDeadlineGroup(
      new VisionAimAndShootTwoBalls(driveSubsystem, visionSubsystem),
      new PutIntakeDownAndSpin(intakeSubsystem)
    ));
  }
}
