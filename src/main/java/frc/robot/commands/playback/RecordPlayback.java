package frc.robot.commands.playback;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.MecanumDriveInfo;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

public class RecordPlayback extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private List<PlaybackStep> steps = new ArrayList<>();
    private long start;

    public RecordPlayback(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        driveSubsystem.setSettings(DriveSubsystem.recordPlaybackSettings);
        driveSubsystem.reset();
        start = System.currentTimeMillis();
    }

    private double scale(double n) {
        if (Math.abs(n) < 0.1) return 0;
        return n * n * Math.signum(n);
    }

    @Override
    public void execute() {
        boolean turbo = RobotContainer.gamepad.getRawButton(6);
        double xyMultiplier = turbo ? 1 : 0.3;
        double rotationMultiplier = turbo ? 0.35 : 0.25;
        double x = scale(RobotContainer.gamepad.getX()) * xyMultiplier;
        double y = scale(-RobotContainer.gamepad.getY()) * xyMultiplier;
        double z = scale(RobotContainer.gamepad.getRawAxis(4)) * rotationMultiplier;

        // Note: Whether we're field oriented or not doesn't affect playback
        boolean fieldOriented = true;
        driveSubsystem.driveTeleop(x, y, z, fieldOriented);

        PlaybackStep step = new PlaybackStep();
        step.time = (System.currentTimeMillis() - start) / 1000.0;

        step.inputX = x;
        step.inputY = y;
        step.inputZ = z;

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
        driveSubsystem.stop();

        Playback playback = new Playback();
        playback.setSteps((PlaybackStep[]) steps.toArray());
        playback.save("playback-info-" + System.currentTimeMillis());
    }
}
