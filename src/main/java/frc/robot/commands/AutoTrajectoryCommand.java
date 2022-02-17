package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoTrajectoryCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private Trajectory trajectory;
    private long startTime;
    private Pose2d initialPose = null;

    public AutoTrajectoryCommand(DriveSubsystem driveSubsystem, Trajectory trajectory) {
        this.driveSubsystem = driveSubsystem;
        this.trajectory = trajectory;
        addRequirements(driveSubsystem);
    }

    private double now() {
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }

    @Override
    public void initialize() {
        driveSubsystem.setSettings(DriveSubsystem.autoSettings);
        driveSubsystem.reset();
        initialPose = trajectory.getInitialPose();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        double currentTime = now();
        State state = trajectory.sample(currentTime);
        Pose2d pose = state.poseMeters;

        driveSubsystem.driveAutoFixed(pose.getX() - initialPose.getX(), pose.getY() - initialPose.getY());
    }

    @Override
    public boolean isFinished() {
        return now() >= (trajectory.getTotalTimeSeconds() + 1);
    }
}
