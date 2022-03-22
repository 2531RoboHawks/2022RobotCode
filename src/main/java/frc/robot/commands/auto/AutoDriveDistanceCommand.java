package frc.robot.commands.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.RotatePID;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDriveDistanceCommand extends CommandBase {
  private DriveSubsystem driveSubsystem;
  private Pose2d desiredPose;
  private PIDController xController = new PIDController(0.5, 0, 0);
  private PIDController yController = new PIDController(0.5, 0, 0);
  private PIDController zController = new PIDController(RotatePID.kP, RotatePID.kI, RotatePID.kD);

  public AutoDriveDistanceCommand(DriveSubsystem driveSubsystem, double yDistance, double xDistance, double degrees) {
    this.driveSubsystem = driveSubsystem;
    this.desiredPose = new Pose2d(xDistance, yDistance, Rotation2d.fromDegrees(degrees));
    addRequirements(driveSubsystem);

    xController.setSetpoint(desiredPose.getX());
    yController.setSetpoint(desiredPose.getY());
    zController.setSetpoint(desiredPose.getRotation().getDegrees());
    zController.setTolerance(0.5);
  }

  @Override
  public void initialize() {
    driveSubsystem.resetOdometry(new Pose2d());
    xController.reset();
    yController.reset();
    zController.reset();
  }

  @Override
  public void execute() {
    Pose2d currentPose = driveSubsystem.getPose();

    double x = xController.calculate(currentPose.getY());
    double y = yController.calculate(currentPose.getX());
    double z = zController.calculate(currentPose.getRotation().getDegrees());

    System.out.println("x: " + x + "y: " + y + " z: " + z);

    driveSubsystem.drivePercent(y, x, -z);
  }

  @Override
  public boolean isFinished() {
    return xController.atSetpoint() && yController.atSetpoint() && zController.atSetpoint();
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stop();
  }
}
