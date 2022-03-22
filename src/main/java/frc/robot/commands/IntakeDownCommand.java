package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeDownCommand extends CommandBase {
  private IntakeSubsystem intakeSubsystem;

  public IntakeDownCommand(IntakeSubsystem intakeSubsystem) {
    addRequirements(intakeSubsystem);
    this.intakeSubsystem = intakeSubsystem;
  }

  @Override
  public void initialize() {
    intakeSubsystem.setStorageAfterIntakeRunning(true);
    intakeSubsystem.setSpinning(true);
    intakeSubsystem.setDown(true);
  }

  @Override
  public void end(boolean interrupted) {
    intakeSubsystem.setStorageAfterIntakeRunning(false);
    intakeSubsystem.setSpinning(false);
    intakeSubsystem.setDown(false);
  }
}
