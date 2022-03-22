package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TheRumbling extends SequentialCommandGroup {
  private static final Waypoint START = new Waypoint(7.71, 2.75, -90.00);
  private static final Waypoint FIRST_BALL = new Waypoint(7.71, 0.5, -90.00);
  private static final Waypoint SECOND_BALL = new Waypoint(5.0, 1.98, -90.00);
  private static final Waypoint TERMINAL = new Waypoint(1.56, 1.60, -90.00);
  private static final Waypoint FINAL_SHOT = SECOND_BALL;

  public TheRumbling(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, ShootSubsystem shootSubsystem, VisionSubsystem visionSubsystem) {
    addRequirements(driveSubsystem, intakeSubsystem, shootSubsystem);
    addCommands(new ResetOdometryCommand(driveSubsystem, START));
    addCommands(new AutoShootCommand(shootSubsystem, visionSubsystem));
    addCommands(new InstantCommand(() -> {
      intakeSubsystem.enable();
      intakeSubsystem.setDown(true);
    }));
    addCommands(TrajectoryCommand.fromWaypoints(driveSubsystem, START, FIRST_BALL));
    addCommands(new WaitCommand(1));
    addCommands(TrajectoryCommand.fromWaypoints(driveSubsystem, FIRST_BALL, SECOND_BALL));
    // TODO: shoot two balls from here
    addCommands(new WaitCommand(1));
    addCommands(TrajectoryCommand.fromWaypoints(driveSubsystem, SECOND_BALL, TERMINAL));
    addCommands(new WaitCommand(1));
    addCommands(TrajectoryCommand.fromWaypoints(driveSubsystem, TERMINAL, FINAL_SHOT));
    // TODO: shoot two balls from here
    addCommands(new WaitCommand(1));
  }
}
