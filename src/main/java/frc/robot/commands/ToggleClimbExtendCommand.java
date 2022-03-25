package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ClimbSubsystem;

public class ToggleClimbExtendCommand extends SequentialCommandGroup {
  private boolean newExtended;

  public ToggleClimbExtendCommand(ClimbSubsystem climbSubsystem) {
    // do not use requirements to not interrupt other climbing commands
    addCommands(new InstantCommand(() -> {
      newExtended = !climbSubsystem.areArmsExtended();
      climbSubsystem.setArmsExtended(newExtended);
    }));
    // addCommands(new WaitCommand(1));
    // addCommands(new InstantCommand(() -> {
    //   climbSubsystem.setGrabbing(newExtended);
    // }));
  }
}
