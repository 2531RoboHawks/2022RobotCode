package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class TheRumbling extends SequentialCommandGroup {
  private static final Waypoint START = new Waypoint(7.71, 2.75, -90.00);
  private static final Waypoint FIRST_BALL = new Waypoint(7.71, 0.5, -90.00);
  private static final Waypoint SECOND_BALL = new Waypoint(5.0, 1.98, -90.00);
  private static final Waypoint TERMINAL = new Waypoint(1.56, 1.60, -90.00);
  private static final Waypoint FINAL_SHOT = SECOND_BALL;

  public TheRumbling(DriveSubsystem drive, IntakeSubsystem intake, ShootSubsystem shoot) {
    addRequirements(drive, intake, shoot);
    addCommands(TrajectoryCommand.fromWaypoints(drive, START, FIRST_BALL).resetOdometry());
    addCommands(new WaitCommand(1));
    addCommands(TrajectoryCommand.fromWaypoints(drive, FIRST_BALL, SECOND_BALL));
    addCommands(new WaitCommand(1));
    addCommands(TrajectoryCommand.fromWaypoints(drive, SECOND_BALL, TERMINAL));
    addCommands(new WaitCommand(1));
    addCommands(TrajectoryCommand.fromWaypoints(drive, TERMINAL, FINAL_SHOT));
    addCommands(new WaitCommand(1));
  }
}
