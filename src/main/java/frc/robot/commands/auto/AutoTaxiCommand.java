package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;

public class AutoTaxiCommand extends SequentialCommandGroup {
    public AutoTaxiCommand(DriveSubsystem driveSubsystem) {
        addRequirements(driveSubsystem);

        addCommands(new InstantCommand(() -> {
            driveSubsystem.drivePercent(0.15, 0, 0, false);
        }));
        addCommands(new WaitCommand(5));
        addCommands(new InstantCommand(() -> {
            driveSubsystem.stop();
        }));
    }
}
