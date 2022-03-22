package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class CooksleyStraight extends SequentialCommandGroup {
  private static final Waypoint START = new Waypoint(7.71, 1.82, -180);
  private static final Waypoint FIRST_BALL = new Waypoint(5.18, 1.96, -180);
  private static final Waypoint SECOND_BALL = new Waypoint(1.55, 1.50, -135);
  private static final Waypoint FINAL_SHOT = SECOND_BALL;

  public CooksleyStraight(DriveSubsystem drive, IntakeSubsystem intake, ShootSubsystem shoot) {
    addRequirements(drive, intake, shoot);
    addCommands(TrajectoryCommand.fromWaypoints(
      drive,
      START,
      FIRST_BALL,
      SECOND_BALL,
      FINAL_SHOT
    ).resetOdometry());
  }
}
