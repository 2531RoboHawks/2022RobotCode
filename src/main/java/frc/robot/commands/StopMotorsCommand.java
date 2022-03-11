package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveSubsystem;

public class StopMotorsCommand extends InstantCommand {
    public StopMotorsCommand(DriveSubsystem driveSubsystem) {
        super(() -> {
            driveSubsystem.stop();
        }, driveSubsystem);
    }
}
