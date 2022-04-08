package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveDistance extends CommandBase {
  private DriveSubsystem driveSubsystem;
  private PIDController forwardController = new PIDController(1.5, 0, 0);
  private PIDController sidewaysController = new PIDController(1.5, 0, 0);
  private PIDController rotationController = new PIDController(0.05, 0, 0);

  private static final double maxVelocity = 5;
  private static final double maxRotation = 5;

  private boolean doesNothing;

  public DriveDistance(double inches, DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);

    doesNothing = inches == 0;

    forwardController.setSetpoint(Units.inchesToMeters(inches));
    forwardController.setTolerance(0.1);

    sidewaysController.setSetpoint(0);
    sidewaysController.setTolerance(0.1);

    rotationController.setSetpoint(0);
    rotationController.setTolerance(1);
  }

  @Override
  public void initialize() {
    forwardController.reset();
    sidewaysController.reset();
    rotationController.reset();

    driveSubsystem.resetOdometry(new Pose2d());
  }

  @Override
  public void execute() {
    if (doesNothing) {
      return;
    }

    Pose2d currentPose = driveSubsystem.getPose();

    double forwards = MathUtil.clamp(forwardController.calculate(currentPose.getX()), -maxVelocity, maxVelocity);
    double sideways = MathUtil.clamp(sidewaysController.calculate(currentPose.getY()), -maxVelocity, maxVelocity);
    double rotation = MathUtil.clamp(rotationController.calculate(currentPose.getRotation().getDegrees()), -maxRotation, maxRotation);

    System.out.println("F: " + forwards + " P: " + currentPose.getX());
    System.out.println("S: " + sideways + " P: " + currentPose.getY());
    System.out.println("R: " + rotation + " P: " + currentPose.getRotation().getDegrees());
    System.out.println("---");

    sideways = 0;

    driveSubsystem.driveWheelSpeeds(driveSubsystem.calculateFieldOriented(forwards, -sideways, -rotation));
  }

  @Override
  public boolean isFinished() {
    if (doesNothing) {
      return true;
    }
    return forwardController.atSetpoint() && sidewaysController.atSetpoint() && rotationController.atSetpoint();
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stop();
  }
}
