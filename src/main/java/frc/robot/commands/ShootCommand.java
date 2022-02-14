package frc.robot.commands;

public class ShootCommand extends CommandBase {
  public ShootCommand(ShootSubsystem shootSubsystem) {}
  
  @Override
  public void initialize() {}

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    shootSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}