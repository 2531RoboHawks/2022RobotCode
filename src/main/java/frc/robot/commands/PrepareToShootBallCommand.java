package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.ShootSubsystem;

public class PrepareToShootBallCommand extends StartEndCommand {
  public PrepareToShootBallCommand(ShootSubsystem shootSubsystem) {
    super(
      () -> {
        shootSubsystem.setStorageBeforeShootRunning(true);
      },
      () -> {
        shootSubsystem.setStorageBeforeShootRunning(false);
      },
      shootSubsystem
    );
  }
}
