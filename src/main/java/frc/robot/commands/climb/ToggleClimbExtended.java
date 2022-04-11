package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ClimbSubsystem;

public class ToggleClimbExtended extends SequentialCommandGroup {
  private ClimbSubsystem climbSubsystem;

  // private Command setExtendedTrue = new SequentialCommandGroup(
  //   new InstantCommand(() -> {
  //     climbSubsystem.setArmsExtended(true);
  //   }),
  //   new WaitCommand(1),
  //   new InstantCommand(() -> {
  //     climbSubsystem.setSpikes(true);
  //   })
  // );

  // private Command setExtendedFalse = new SequentialCommandGroup(
  //   new InstantCommand(() -> {
  //     climbSubsystem.setSpikes(false);
  //   }),
  //   new WaitCommand(0.1),
  //   new InstantCommand(() -> {
  //     climbSubsystem.setArmsExtended(false);
  //   })
  // );

  public ToggleClimbExtended(ClimbSubsystem climbSubsystem) {
    // do not use requirements to not interrupt other climbing commands
    this.climbSubsystem = climbSubsystem;
    // addCommands(new SelectCommand(() -> {
    //   if (climbSubsystem.areArmsExtended()) {
    //     return setExtendedFalse;
    //   }
    //   return setExtendedTrue;
    // }));
    addCommands(new InstantCommand(() -> {
      climbSubsystem.setArmsExtended(!climbSubsystem.areArmsExtended());
    }));
  }
}
