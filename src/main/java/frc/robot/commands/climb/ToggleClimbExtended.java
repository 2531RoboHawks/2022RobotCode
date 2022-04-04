package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClimbSubsystem;

public class ToggleClimbExtended extends SequentialCommandGroup {
  private boolean newExtended;

  public ToggleClimbExtended(ClimbSubsystem climbSubsystem) {
    // do not use requirements to not interrupt other climbing commands
    addCommands(new InstantCommand(() -> {
      newExtended = !climbSubsystem.areArmsExtended();
      climbSubsystem.setArmsExtended(newExtended);
    }));
  }
}
