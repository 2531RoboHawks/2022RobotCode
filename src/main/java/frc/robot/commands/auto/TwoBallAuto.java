package frc.robot.commands.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.RunIntakeCommandGroup;
import frc.robot.commands.shooting.MoveBallToShooter;
import frc.robot.commands.shooting.PrepareToShootBall;
import frc.robot.commands.shooting.RevShooterToSpeedThenNeutral;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TwoBallAuto extends SequentialCommandGroup {
  // TODO move to constants, tune
  private static final double startingDistance = 55;
  private static final double moves = 80;

  public TwoBallAuto(DriveSubsystem driveSubsystem, ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem, VisionSubsystem visionSubsystem) {
    addCommands(new ResetOdometry(driveSubsystem));
    addCommands(new RevShooterToSpeedThenNeutral(shootSubsystem, 4000));
    addCommands(new MoveBallToShooter(shootSubsystem));
    // TODO: reimplement the second ball
    // addCommands(new RunIntakeCommandGroup(
    //   intakeSubsystem,
    //   new DriveToWaypoint(driveSubsystem, Units.inchesToMeters(moves), 0, 0),
    //   new SequentialCommandGroup(
    //     new WaitCommand(1),
    //     new DriveToWaypoint(driveSubsystem, 0, 0, 0)
    //   ).deadlineWith(new PrepareToShootBall(shootSubsystem)),
    //   new ShootOneBall(driveSubsystem, shootSubsystem, intakeSubsystem, visionSubsystem, startingDistance)
    // ));
  }
}
