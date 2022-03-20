package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;

@Deprecated
public class PrimitiveTurnAround extends SequentialCommandGroup {
  public PrimitiveTurnAround(DriveSubsystem driveSubsystem) {
    addCommands(new AutoDriveCommand(driveSubsystem, 0, 0, 0.38));
  }
}
