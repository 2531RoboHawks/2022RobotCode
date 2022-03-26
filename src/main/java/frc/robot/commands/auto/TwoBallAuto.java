package frc.robot.commands.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.RunIntakeCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TwoBallAuto extends SequentialCommandGroup {
  public TwoBallAuto(DriveSubsystem driveSubsystem, ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem, VisionSubsystem visionSubsystem) {
    double startingDistance = 52.5;
    double moves = 82;
    double jiggle = 45;
    addCommands(new ResetOdometryCommand(driveSubsystem));
    addCommands(new ShootOneBall(driveSubsystem, shootSubsystem, intakeSubsystem, visionSubsystem, startingDistance));
    addCommands(new RunIntakeCommandGroup(
      intakeSubsystem,
      new DriveToWaypoint(driveSubsystem, Units.inchesToMeters(moves), 0, 0),
      // new DriveToWaypoint(driveSubsystem, Units.inchesToMeters(jiggle), 0, 0),
      new WaitCommand(1),
      new ShootOneBall(driveSubsystem, shootSubsystem, intakeSubsystem, visionSubsystem, startingDistance + moves)
    ));
  }
}
