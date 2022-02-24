package frc.robot.commands.playback;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.MecanumDriveInfo;
import frc.robot.subsystems.DriveSubsystem;

public class PlayPlaybackCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private Playback playback;
    private PlaybackStep initialState;
    private int index;

    public PlayPlaybackCommand(DriveSubsystem driveSubsystem, Playback playback) {
        this.driveSubsystem = driveSubsystem;
        this.playback = playback;
        initialState = playback.getSteps()[0];
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Playback START");
        driveSubsystem.setPID(playback.pid);
        driveSubsystem.reset();
        index = 0;
    }

    @Override
    public void execute() {
        System.out.println("Running playback step " + index);

        PlaybackStep step = playback.getSteps()[index];
        index += 1;

        driveSubsystem.driveFixedSensorUnits(new MecanumDriveInfo(
            step.targetFrontLeft - initialState.targetFrontLeft,
            step.targetFrontRight - initialState.targetFrontRight,
            step.targetBackLeft - initialState.targetBackLeft,
            step.targetBackRight - initialState.targetBackRight
        ));
    }

    @Override
    public boolean isFinished() {
        return index >= playback.getSteps().length;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Playback DONE");
        driveSubsystem.stop();
    }
}
