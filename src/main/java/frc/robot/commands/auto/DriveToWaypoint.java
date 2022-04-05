package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Waypoint;
import frc.robot.subsystems.DriveSubsystem;

public class DriveToWaypoint extends CommandBase {
  private DriveSubsystem driveSubsystem;
  private Pose2d desiredPose;
  private boolean shouldResetOdometry = false;
  private PIDController forwardController = new PIDController(0.5, 0, 0);
  private PIDController sidewaysController = new PIDController(0.5, 0, 0);
  private PIDController rotationController = new PIDController(0.0115, 0.012, 0);

  private static final double maxVelocity = 0.3;
  private static final double maxRotation = 0.3;

  public DriveToWaypoint(Pose2d pose, DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.desiredPose = pose;
    addRequirements(driveSubsystem);

    forwardController.setSetpoint(desiredPose.getX());
    sidewaysController.setSetpoint(desiredPose.getY());
    rotationController.setSetpoint(desiredPose.getRotation().getDegrees());
    rotationController.setTolerance(1);
    forwardController.setTolerance(0.1);
    sidewaysController.setTolerance(0.1);
  }

  public DriveToWaypoint(DriveSubsystem driveSubsystem, Pose2d pose) {
    this(pose, driveSubsystem);
  }

  public DriveToWaypoint(DriveSubsystem driveSubsystem, double forward, double sideways, double rotationDegrees) {
    this(new Pose2d(forward, sideways, Rotation2d.fromDegrees(rotationDegrees)), driveSubsystem);
  }

  public DriveToWaypoint(DriveSubsystem driveSubsystem, Waypoint waypoint) {
    this(waypoint.getPose(), driveSubsystem);
  }

  @Override
  public void initialize() {
    forwardController.reset();
    sidewaysController.reset();
    rotationController.reset();
    if (shouldResetOdometry) {
      driveSubsystem.resetOdometry(new Pose2d());
    }
  }

  @Override
  public void execute() {
    Pose2d currentPose = driveSubsystem.getPose();

    double forwards = MathUtil.clamp(forwardController.calculate(currentPose.getX()), -maxVelocity, maxVelocity);
    double sideways = MathUtil.clamp(sidewaysController.calculate(currentPose.getY()), -maxVelocity, maxVelocity);
    double rotation = MathUtil.clamp(rotationController.calculate(currentPose.getRotation().getDegrees()), -maxRotation, maxRotation);

    driveSubsystem.drivePercent(forwards, -sideways, -rotation);
  }

  @Override
  public boolean isFinished() {
    return forwardController.atSetpoint() && sidewaysController.atSetpoint() && rotationController.atSetpoint();
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stop();
  }

  public DriveToWaypoint resetOdometry() {
    shouldResetOdometry = true;
    return this;
  }
}
