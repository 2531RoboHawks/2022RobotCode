package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.RunIntakeCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TheRumbling extends SequentialCommandGroup {
  private static final Waypoint START = new Waypoint(7.65, 1.91, -90.00);
  private static final Waypoint FIRST_BALL = new Waypoint(7.66, 0.72, -90.00);
  private static final Waypoint SECOND_BALL = new Waypoint(5.11, 1.98, -180);
  private static final Waypoint TERMINAL = new Waypoint(1.56, 1.60, -157.70);
  private static final Waypoint FINAL_SHOT = SECOND_BALL;

  public TheRumbling(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, ShootSubsystem shootSubsystem, VisionSubsystem visionSubsystem) {
    addCommands(new ResetOdometryCommand(driveSubsystem, START));
    addCommands(new ShootOneBall(shootSubsystem, intakeSubsystem, visionSubsystem));
    addCommands(new RunIntakeCommandGroup(
      intakeSubsystem,
      TrajectoryCommand.fromWaypoints(driveSubsystem, START, FIRST_BALL),
      new WaitCommand(1),
      TrajectoryCommand.fromWaypoints(driveSubsystem, FIRST_BALL, SECOND_BALL),
      new WaitCommand(1),
      TrajectoryCommand.fromWaypoints(driveSubsystem, SECOND_BALL, TERMINAL),
      new WaitCommand(1),
      TrajectoryCommand.fromWaypoints(driveSubsystem, TERMINAL, FINAL_SHOT),
      new WaitCommand(1)
    ));
  }
}
