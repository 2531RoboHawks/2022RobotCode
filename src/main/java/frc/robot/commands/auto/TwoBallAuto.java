package frc.robot.commands.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.RunIntakeCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TwoBallAuto extends SequentialCommandGroup {
  public TwoBallAuto(DriveSubsystem driveSubsystem, ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem, VisionSubsystem visionSubsystem) {
    addCommands(new ResetOdometryCommand(driveSubsystem));
    addCommands(new ShootOneBall(shootSubsystem, intakeSubsystem, visionSubsystem));
    addCommands(new RunIntakeCommandGroup(
      intakeSubsystem,
      new DriveToWaypoint(driveSubsystem, Units.inchesToMeters(42.421), 0, 0),
      new WaitCommand(2),
      new ShootOneBall(shootSubsystem, intakeSubsystem, visionSubsystem)
    ));
  }
}
