package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;

public class Taxi extends SequentialCommandGroup {
  public Taxi(DriveSubsystem driveSubsystem) {
    addCommands(new AutoDriveDistanceCommand(driveSubsystem, 1, 0, 0));
  }
}
