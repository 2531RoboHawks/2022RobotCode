package frc.robot.commands;

public class ClimbCommand extends CommandBase {
  public ClimbCommand(ClimbSubsystem climbSubsystem) {}
  
  @Override
  public void initialize() {}

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    climbSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}