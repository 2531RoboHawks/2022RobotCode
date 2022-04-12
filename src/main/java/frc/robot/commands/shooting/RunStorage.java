package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.StorageSubsystem;

public class RunStorage extends StartEndCommand {
  public RunStorage(double afterIntake, double beforeShooter, StorageSubsystem storageSubsystem) {
    super(
      () -> {
        storageSubsystem.setAfterIntakePower(afterIntake);
        storageSubsystem.setBeforeShooterPower(beforeShooter);
      },
      () -> {
        storageSubsystem.stopAfterIntake();
        storageSubsystem.stopBeforeShooter();
      },
      storageSubsystem
    );
  }
}
