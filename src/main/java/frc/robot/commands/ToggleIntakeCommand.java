package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class ToggleIntakeCommand extends InstantCommand {
  public ToggleIntakeCommand(IntakeSubsystem intakeSubsystem) {
    // do not use requirements to not interrupt other climbing commands
    super(() -> {
      intakeSubsystem.toggleDown();
    });
  }
}
