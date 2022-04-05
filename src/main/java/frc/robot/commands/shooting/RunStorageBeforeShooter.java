package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.StorageSubsystem;

public class RunStorageBeforeShooter extends StartEndCommand {
  public RunStorageBeforeShooter(double power, StorageSubsystem storageSubsystem) {
    super(
      () -> {
        storageSubsystem.setBeforeShooterPower(power);
      },
      () -> {
        storageSubsystem.stopBeforeShooter();
      },
      storageSubsystem
    );
  }
}
