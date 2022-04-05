package frc.robot.commands.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
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
  // TODO move to constants, tune
  private static final double startingDistance = 55;
  private static final double moves = 80;

  private final DriveSubsystem driveSubsystem = RobotContainer.driveSubsystem;
  private final ShootSubsystem shootSubsystem = RobotContainer.shootSubsystem;
  private final StorageSubsystem storageSubsystem = RobotContainer.storageSubsystem;
  private final IntakeSubsystem intakeSubsystem = RobotContainer.intakeSubsystem;
  private final VisionSubsystem visionSubsystem = RobotContainer.visionSubsystem;

  public TwoBallAuto() {
    addCommands(new ResetOdometry(driveSubsystem));
    addCommands(new RevShooterToSpeedThenNeutral(4000, shootSubsystem)); // TODO: tune
    addCommands(new MoveBallToShooter(storageSubsystem));
    addCommands(new WaitCommand(0.2));
    // addCommands(new ParallelCommandGroup(
    //   new PutIntakeDownAndSpin(intakeSubsystem),
    //   new SequentialCommandGroup(
    //     new DriveToWaypoint(driveSubsystem, Units.inchesToMeters(moves), 0, 0),
    //     new RevShooterToSpeedThenNeutral(4000, shootSubsystem),
    //     new MoveBallToShooter(storageSubsystem)
    //   )
    // ));
  }
}
