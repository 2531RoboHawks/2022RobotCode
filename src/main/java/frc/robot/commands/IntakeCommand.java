package frc.robot.commands;

public class IntakeCommand extends CommandBase {
  public IntakeCommand(IntakeSubsystem intakeSubsystem) {}
  
  @Override
  public void initialize() {}

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    intakeSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}