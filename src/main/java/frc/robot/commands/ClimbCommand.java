package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase {
  private ClimbSubsystem climbSubsystem;
  private double target;
  private boolean manual;

  public ClimbCommand(ClimbSubsystem climbSubsystem) {
    this.climbSubsystem = climbSubsystem;
    addRequirements(climbSubsystem);
  }

  @Override
  public void initialize() {
    climbSubsystem.reset();
    target = 0;
    manual = false;
  }

  private double applyDeadzone(double n) {
    if (Math.abs(n) < 0.1) return 0;
    return n;
  }

  @Override
  public void execute() {
    // Temporary
    if (RobotContainer.gamepad.getRawButtonPressed(1)) {
      manual = !manual;
      climbSubsystem.reset();
      target = 0;
      System.out.println("Toggled manual climb: " + manual);
    }

    // Temporary
    if (RobotContainer.gamepad.getRawButtonPressed(2)) {
      System.out.println("Toggled arm");
      climbSubsystem.togglePistonExtended();
    }

    if (manual) {
      double left = -applyDeadzone(RobotContainer.gamepad.getRawAxis(1));
      double right = -applyDeadzone(RobotContainer.gamepad.getRawAxis(5));
      climbSubsystem.leftTalon.setPower(left);
      climbSubsystem.rightTalon.setPower(right);
    } else {
      double power = -applyDeadzone(RobotContainer.gamepad.getRawAxis(1));
      target += power * 1500;
      climbSubsystem.setArmExtension(target);
    }
  }

  @Override
  public void end(boolean interrupted) {
    climbSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}