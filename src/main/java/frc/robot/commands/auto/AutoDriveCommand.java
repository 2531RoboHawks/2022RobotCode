package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDriveCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private double millis;
    private double speed;

    public AutoDriveCommand(DriveSubsystem driveSubsystem, double seconds, double speed) {
        this.driveSubsystem = driveSubsystem;
        this.millis = seconds * 1000.0;
        this.speed = speed;
        addRequirements(driveSubsystem);
    }

    private long start = 0;

    @Override
    public void initialize() {
        start = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        driveSubsystem.drivePercent(speed, 0, 0, false);
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stop();
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() > start + millis;
    }
}
