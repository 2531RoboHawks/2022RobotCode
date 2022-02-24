package frc.robot.commands.playback;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.MecanumDriveInfo;
import frc.robot.PIDSettings;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;

public class RecordPlaybackCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private DriveCommand driveCommand;
    private List<PlaybackStep> steps = new ArrayList<>();
    private long start;

    public RecordPlaybackCommand(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        this.driveCommand = new DriveCommand(driveSubsystem);
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        this.driveCommand.initialize();
        start = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        this.driveCommand.execute();

        PlaybackStep step = new PlaybackStep();
        step.time = (System.currentTimeMillis() - start) / 1000.0;

        // TODO
        step.inputX = 0;
        step.inputY = 0;
        step.inputZ = 0;

        MecanumDriveInfo targetValues = driveSubsystem.getTargetValues();
        step.targetFrontLeft = targetValues.frontLeft;
        step.targetFrontRight = targetValues.frontRight;
        step.targetBackLeft = targetValues.backLeft;
        step.targetBackRight = targetValues.backRight;

        MecanumDriveInfo velocities = driveSubsystem.getWheelVelocities();
        step.velocityFrontLeft = velocities.frontLeft;
        step.velocityFrontRight = velocities.frontRight;
        step.velocityBackLeft = velocities.backLeft;
        step.velocityBackRight = velocities.backRight;

        MecanumDriveInfo positions = driveSubsystem.getWheelPositions();
        step.positionFrontLeft = positions.frontLeft;
        step.positionFrontRight = positions.frontRight;
        step.positionBackLeft = positions.backLeft;
        step.positionBackRight = positions.backRight;

        steps.add(step);
    }

    @Override
    public void end(boolean interrupted) {
        this.driveCommand.end(interrupted);

        Playback playback = new Playback();
        playback.setPID(driveSubsystem.getPID());
        playback.setSteps(steps.toArray(new PlaybackStep[0]));
        playback.save("playback-info-" + System.currentTimeMillis());
    }
}
