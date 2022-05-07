package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.RobotContainer;
import frc.robot.Waypoint;
import frc.robot.Constants.ShootingConstants;
import frc.robot.commands.shooting.ShootTwoBalls;
import frc.robot.commands.shooting.SuppliedRPM;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class OneBallAndDriveAllTheWayBack extends SequentialCommandGroup {
  private final DriveSubsystem driveSubsystem = RobotContainer.driveSubsystem;
  private final VisionSubsystem visionSubsystem = RobotContainer.visionSubsystem;

  private final double moveTimeout = 2;

  public OneBallAndDriveAllTheWayBack() {
    addCommands(new VisionAim(visionSubsystem, driveSubsystem).withTimeout(ShootingConstants.visionAimTimeout));
    addCommands(new ShootTwoBalls(() -> {
      return new SuppliedRPM(4500, true);
    }, new InstantCommand(), false));
    addCommands(
      new DriveToWaypoint(driveSubsystem, new Waypoint(0, 0, 0))
        .withTimeout(moveTimeout)
    );
    addCommands(new ParallelCommandGroup(
      new StartEndCommand(
        () -> {},
        () -> {
          driveSubsystem.stop();
        }
      ),
      new RunCommand(() -> {
        driveSubsystem.drivePercent(0.2, 0, 0);
      })
    ));
  }
}
