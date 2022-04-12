package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.StorageSubsystem;

public class RunStorage extends StartEndCommand {
  public RunStorage(boolean afterIntake, double beforeShooter, StorageSubsystem storageSubsystem) {
    super(
      () -> {
        storageSubsystem.setAfterIntakeRunning(afterIntake);
        storageSubsystem.setBeforeShooterPower(beforeShooter);
      },
      () -> {
        storageSubsystem.setAfterIntakeRunning(false);
        storageSubsystem.setBeforeShooterPower(0);
      },
      storageSubsystem
    );
  }
}
