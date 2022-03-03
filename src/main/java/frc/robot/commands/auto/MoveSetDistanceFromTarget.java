package frc.robot.commands.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class MoveSetDistanceFromTarget extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private VisionSubsystem visionSubsystem;
    private double distance;
    private PIDController pid = new PIDController(0.01, 0, 0);

    public MoveSetDistanceFromTarget(DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem, double distance) {
        this.driveSubsystem = driveSubsystem;
        this.visionSubsystem = visionSubsystem;
        this.distance = distance;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        pid.setTolerance(0.5);
        pid.setSetpoint(distance);
        pid.reset();
        driveSubsystem.reset();
    }

    @Override
    public void execute() {
        double value = pid.calculate(visionSubsystem.getDistance());
        double maxSpeed = 0.3;
        if (value > maxSpeed) value = maxSpeed;
        if (value < -maxSpeed) value = -maxSpeed;
        driveSubsystem.drivePercent(-value, 0, 0, false);
    }

    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stop();
    }
}