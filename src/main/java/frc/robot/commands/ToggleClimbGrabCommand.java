package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ClimbSubsystem;

public class ToggleClimbGrabCommand extends InstantCommand {
  public ToggleClimbGrabCommand(ClimbSubsystem climbSubsystem) {
    // do not use requirements to not interrupt other climbing commands
    super(() -> {
      climbSubsystem.toggleGrabbing();
    });
  }
}
