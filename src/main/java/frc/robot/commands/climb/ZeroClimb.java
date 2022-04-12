package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ClimbSubsystem;

public class ZeroClimb extends InstantCommand {
  public ZeroClimb(ClimbSubsystem climbSubsystem) {
    super(() -> {
      climbSubsystem.zero();
    }, climbSubsystem);
  }
}
