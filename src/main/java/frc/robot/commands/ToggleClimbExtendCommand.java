package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ClimbSubsystem;

public class ToggleClimbExtendCommand extends InstantCommand {
  public ToggleClimbExtendCommand(ClimbSubsystem climbSubsystem) {
    // do not use requirements to not interrupt other climbing commands
    super(() -> {
      if (climbSubsystem.areArmsExtended()) {
        climbSubsystem.setArmsExtended(false);
        climbSubsystem.setGrabbing(false);
      } else {
        climbSubsystem.setArmsExtended(true);
        climbSubsystem.setGrabbing(true);
      }
    });
  }
}
