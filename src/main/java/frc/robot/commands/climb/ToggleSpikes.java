package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ClimbSubsystem;

public class ToggleSpikes extends InstantCommand {
  public ToggleSpikes(ClimbSubsystem climbSubsystem) {
    // intentionally does not use requirements to avoid interrupting other commands
    super(() -> {
      climbSubsystem.setSpikes(!climbSubsystem.getSpikes());
    });
  }
}
