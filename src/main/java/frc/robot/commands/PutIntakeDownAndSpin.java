package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class PutIntakeDownAndSpin extends CommandBase {
  private IntakeSubsystem intakeSubsystem;

  public PutIntakeDownAndSpin(IntakeSubsystem intakeSubsystem) {
    addRequirements(intakeSubsystem);
    this.intakeSubsystem = intakeSubsystem;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    intakeSubsystem.putDownAndSpin();
  }

  @Override
  public void end(boolean interrupted) {
    intakeSubsystem.bringUpAndStopSpinning();
  }
}
