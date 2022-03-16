package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;

public class AutoTurnAroundCommand extends SequentialCommandGroup {
    public AutoTurnAroundCommand(DriveSubsystem driveSubsystem) {
        addCommands(new AutoDriveCommand(driveSubsystem, 0, 0, 0.38));
    }
}
