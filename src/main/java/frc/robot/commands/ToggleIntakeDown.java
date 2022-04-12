package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class ToggleIntakeDown extends InstantCommand {
  public ToggleIntakeDown(IntakeSubsystem intakeSubsystem) {
    // do not use requirements to avoid interrupting other climbing commands
    super(() -> {
      intakeSubsystem.toggleDown();
    });
  }
}
