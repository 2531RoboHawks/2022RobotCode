package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDriveCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private double millis;
    private double ySpeed;
    private double xSpeed;
    private double zRotation;

    public AutoDriveCommand(DriveSubsystem driveSubsystem, double seconds, double ySpeed, double xSpeed, double zRotation) {
        this.driveSubsystem = driveSubsystem;
        this.millis = seconds * 1000.0;
        this.ySpeed = ySpeed;
        this.xSpeed = xSpeed;
        this.zRotation = zRotation;
        addRequirements(driveSubsystem);
    }

    private long start = 0;

    @Override
    public void initialize() {
        start = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        driveSubsystem.drivePercent(ySpeed, xSpeed, zRotation, false);
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
