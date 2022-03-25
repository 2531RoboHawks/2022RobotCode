package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class CooksleyStraight extends SequentialCommandGroup {
  private static final Waypoint START = new Waypoint(7.71, 1.82, -180);
  private static final Waypoint FIRST_BALL = new Waypoint(5.18, 1.96, -180);
  private static final Waypoint SECOND_BALL = new Waypoint(1.55, 1.50, -135);
  private static final Waypoint FINAL_SHOT = SECOND_BALL;

  public CooksleyStraight(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, ShootSubsystem shootSubsystem, VisionSubsystem visionSubsystem) {
    addCommands(new ResetOdometryCommand(driveSubsystem, START));
    addCommands(new ShootOneBall(driveSubsystem, shootSubsystem, intakeSubsystem, visionSubsystem));
    addCommands(new DriveToWaypoint(driveSubsystem, FIRST_BALL));
    addCommands(new WaitCommand(1)); // TODO: shoot
    addCommands(new DriveToWaypoint(driveSubsystem, SECOND_BALL));
    addCommands(new DriveToWaypoint(driveSubsystem, FINAL_SHOT));
    addCommands(new WaitCommand(1)); // TODO: shoot
  }
}
