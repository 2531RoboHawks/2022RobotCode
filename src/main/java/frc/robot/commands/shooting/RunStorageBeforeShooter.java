package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.ShootSubsystem;

public class RunStorageBeforeShooter extends StartEndCommand {
  public RunStorageBeforeShooter(double power, ShootSubsystem shootSubsystem) {
    super(
      () -> {
        shootSubsystem.setStorageBeforeShootPower(power);
      },
      () -> {
        shootSubsystem.setStorageBeforeShootRunning(false);
      },
      shootSubsystem
    );
  }
}
