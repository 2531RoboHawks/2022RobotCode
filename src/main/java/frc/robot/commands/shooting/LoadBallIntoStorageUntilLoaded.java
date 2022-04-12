package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.StorageSubsystem;

public class LoadBallIntoStorageUntilLoaded extends SequentialCommandGroup {
  public LoadBallIntoStorageUntilLoaded(StorageSubsystem storageSubsystem) {
    addCommands(
      new LoadBallIntoStorage(storageSubsystem)
        .until(() -> storageSubsystem.isBallBeforeShooter())
        .withTimeout(ShootingConstants.waitForBallToBePreparedTimeout)
    );
  }
}
