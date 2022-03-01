package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class KeepTurretZeroedCommand extends CommandBase {
    private ShootSubsystem shootSubsystem;

    public KeepTurretZeroedCommand(ShootSubsystem shootSubsystem) {
        this.shootSubsystem = shootSubsystem;
        // NOTE: Intentionally do not addRequirements for now
    }

    @Override
    public void execute() {
        shootSubsystem.setTurretPosition(0);
    }
}
