package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class DefaultIntake extends CommandBase {
  private IntakeSubsystem intakeSubsystem;

  public DefaultIntake(IntakeSubsystem intakeSubsystem) {
    this.intakeSubsystem = intakeSubsystem;
    addRequirements(intakeSubsystem);
  }

  @Override
  public void initialize() {
    if (intakeSubsystem.isDown()) {
      System.out.println("SOME COMMAND DID NOT PROPERLY RESET THE INTAKE -- THE DEFAULT COMMAND IS FIXING IT FOR YOU.");
    }
    intakeSubsystem.setEverything(false);
  }

  @Override
  public void execute() {

  }

  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
