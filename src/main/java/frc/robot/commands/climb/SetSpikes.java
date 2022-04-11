package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ClimbSubsystem;

public class SetSpikes extends InstantCommand {
  public SetSpikes(boolean activated, ClimbSubsystem climbSubsystem) {
    // intentionally does not use requirements to avoid interrupting other commands
    super(() -> {
      climbSubsystem.setSpikes(activated);
    });
  }
}
