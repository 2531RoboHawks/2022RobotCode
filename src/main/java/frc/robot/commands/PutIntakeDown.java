package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class PutIntakeDown extends CommandBase {
  private IntakeSubsystem intakeSubsystem;

  public PutIntakeDown(IntakeSubsystem intakeSubsystem) {
    addRequirements(intakeSubsystem);
    this.intakeSubsystem = intakeSubsystem;
  }

  @Override
  public void initialize() {
    intakeSubsystem.setEverything(true);
  }
}
