package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class ZeroTurretCommand extends CommandBase {
    private ShootSubsystem shootSubsystem;

    public ZeroTurretCommand(ShootSubsystem shootSubsystem) {
        this.shootSubsystem = shootSubsystem;
        addRequirements(shootSubsystem);
    }

    @Override
    public void execute() {
        shootSubsystem.zeroTurret();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
