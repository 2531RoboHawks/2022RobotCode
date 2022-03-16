package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDriveCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private double ySpeed;
    private double xSpeed;
    private double zRotation;

    public AutoDriveCommand(DriveSubsystem driveSubsystem, double ySpeed, double xSpeed, double zRotation) {
        this.driveSubsystem = driveSubsystem;
        this.ySpeed = ySpeed;
        this.xSpeed = xSpeed;
        this.zRotation = zRotation;
        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        driveSubsystem.drivePercent(ySpeed, xSpeed, zRotation);
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stop();
    }
}
