package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveSubsystem;

public class ZeroGyro extends InstantCommand {
  public ZeroGyro(double degrees, DriveSubsystem driveSubsystem) {
    super(() -> {
      driveSubsystem.resetGyro(degrees);
    });
  }
}
