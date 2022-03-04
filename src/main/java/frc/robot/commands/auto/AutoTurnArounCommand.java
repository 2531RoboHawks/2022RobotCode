package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;

// TODO fix typo in name l0l
public class AutoTurnArounCommand extends SequentialCommandGroup {
    public AutoTurnArounCommand(DriveSubsystem driveSubsystem) {
        addCommands(new AutoDriveCommand(driveSubsystem, 1, 0, 0, 0.38));
    }
}
