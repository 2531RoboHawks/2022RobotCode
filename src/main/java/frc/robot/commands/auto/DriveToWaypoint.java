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

  private PIDController forwardController = new PIDController(3, 0, 0);
  private PIDController sidewaysController = new PIDController(3, 0, 0);
  private PIDController rotationController = new PIDController(0.05, 0, 0);

  private double maxVelocity = 2;
  private double maxRotation = 2;

  private static final double xTolerance = 0.02;
  private static final double yTolerance = 999999;
  private static final double rotationTolerance = 1;

  public DriveToWaypoint(Pose2d pose, DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.desiredPose = pose;
    addRequirements(driveSubsystem);

    forwardController.setSetpoint(desiredPose.getX());
    forwardController.setTolerance(xTolerance);

    sidewaysController.setSetpoint(desiredPose.getY());
    sidewaysController.setTolerance(yTolerance);

    rotationController.setSetpoint(desiredPose.getRotation().getDegrees());
    rotationController.setTolerance(rotationTolerance);
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
    driveSubsystem.resetOdometry(new Pose2d());
    forwardController.reset();
    sidewaysController.reset();
    rotationController.reset();
  }

  @Override
  public void execute() {
    Pose2d currentPose = driveSubsystem.getPose();

    double forwards = MathUtil.clamp(forwardController.calculate(currentPose.getX()), -maxVelocity, maxVelocity);
    double sideways = MathUtil.clamp(sidewaysController.calculate(currentPose.getY()), -maxVelocity, maxVelocity);
    double rotation = MathUtil.clamp(rotationController.calculate(currentPose.getRotation().getDegrees()), -maxRotation, maxRotation);

    // sideways causes problems because the rest of the code is wrong
    sideways = 0;

    driveSubsystem.driveWheelSpeeds(driveSubsystem.calculateRobotOriented(forwards, -sideways, -rotation));
  }

  @Override
  public boolean isFinished() {
    return forwardController.atSetpoint() && sidewaysController.atSetpoint() && rotationController.atSetpoint();
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stop();
  }

  public DriveToWaypoint withMaxVelocity(double maxVelocity) {
    this.maxVelocity = maxVelocity;
    return this;
  }
}
