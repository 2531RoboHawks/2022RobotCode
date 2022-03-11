package frc.robot.commands.playback;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.MecanumDriveInfo;
import frc.robot.subsystems.DriveSubsystem;

public class PlayPlaybackCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private Playback playback;
    private PlaybackStep initialStep;
    private long start;

    public PlayPlaybackCommand(DriveSubsystem driveSubsystem, Playback playback) {
        this.driveSubsystem = driveSubsystem;
        this.playback = playback;
        initialStep = playback.getInitialStep();
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Playback START");
        driveSubsystem.setPID(playback.pid);
        driveSubsystem.reset();
        start = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        double seconds = (System.currentTimeMillis() - start) / 1000.0;
        PlaybackStep step = playback.getStepAtTime(seconds);
        System.out.println("Running playback step " + seconds + "s:");
        System.out.println("  FL: " + (step.targetFrontLeft - initialStep.targetFrontLeft));
        System.out.println("  FR: " + (step.targetFrontRight - initialStep.targetFrontRight));
        System.out.println("  BL: " + (step.targetBackLeft - initialStep.targetBackLeft));
        System.out.println("  BR: " + (step.targetBackRight - initialStep.targetBackRight));
        // TODO: broken, unused
        // driveSubsystem.driveFixedSensorUnits(new MecanumDriveInfo(
        //     step.targetFrontLeft - initialStep.targetFrontLeft,
        //     step.targetFrontRight - initialStep.targetFrontRight,
        //     step.targetBackLeft - initialStep.targetBackLeft,
        //     step.targetBackRight - initialStep.targetBackRight
        // ));
    }

    @Override
    public boolean isFinished() {
        return (System.currentTimeMillis() - start) >= playback.getTotalTime();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Playback DONE");
        driveSubsystem.stop();
    }
}
