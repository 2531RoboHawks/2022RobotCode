package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class SetClimbToPosition extends CommandBase {
  private ClimbSubsystem climbSubsystem;
  private double target;
  private double maxError = 100;

  public SetClimbToPosition(double target, ClimbSubsystem climbSubsystem) {
    this.target = target;
    this.climbSubsystem = climbSubsystem;
  }

  @Override
  public void execute() {
    climbSubsystem.setArmExtensionTarget(target);
  }

  @Override
  public void end(boolean interrupted) {
    climbSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return Math.abs(climbSubsystem.getLeftExtension() - target) < maxError && Math.abs(climbSubsystem.getRightExtension() - target) < maxError;
  }
}
