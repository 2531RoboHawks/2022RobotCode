package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IntakeSubsystem;

public class RunIntakeCommandGroup extends SequentialCommandGroup {
  private IntakeSubsystem intakeSubsystem;

  public RunIntakeCommandGroup(IntakeSubsystem intakeSubsystem, Command... commands) {
    super(commands);
    addRequirements(intakeSubsystem);
    this.intakeSubsystem = intakeSubsystem;
  }

  @Override
  public void initialize() {
    super.initialize();
    intakeSubsystem.setEverything(true);
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    intakeSubsystem.setEverything(false);
  }
}
