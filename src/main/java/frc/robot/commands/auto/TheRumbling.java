package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TheRumbling extends SequentialCommandGroup {
  private static final Waypoint START = new Waypoint(0, 0, 0);
  private static final Waypoint FIRST_BALL = new Waypoint(Units.inchesToMeters(42), 0, 0);
  private static final Waypoint SECOND_BALL = new Waypoint(Units.inchesToMeters(12), -Units.inchesToMeters(60), -90);
  private static final Waypoint TERMINAL = new Waypoint(Units.inchesToMeters(60), -Units.inchesToMeters(60), 0);
  private static final Waypoint FINAL_SHOT = SECOND_BALL;

  public TheRumbling(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, ShootSubsystem shootSubsystem, VisionSubsystem visionSubsystem) {
    addCommands(new ResetOdometryCommand(driveSubsystem, START));
    // addCommands(new ShootOneBall(shootSubsystem, intakeSubsystem, visionSubsystem));
    addCommands(new InstantCommand(() -> {
      intakeSubsystem.setEverything(true);
    }, intakeSubsystem));
    addCommands(new DriveToWaypoint(driveSubsystem, FIRST_BALL));
    addCommands(new WaitCommand(1)); // TODO Shoot here
    addCommands(new DriveToWaypoint(driveSubsystem, FIRST_BALL.withRotationFrom(SECOND_BALL)));
    addCommands(new WaitCommand(1)); // TODO Shoot here
    addCommands(new DriveToWaypoint(driveSubsystem, SECOND_BALL.getPoseWithoutRotation()).resetOdometry());
    // addCommands(new WaitCommand(1)); // TODO: shoot
    // addCommands(new DriveToWaypoint(driveSubsystem, TERMINAL));
    // addCommands(new DriveToWaypoint(driveSubsystem, FINAL_SHOT));
    // addCommands(new WaitCommand(1)); // TODO: shoot
  }
}
