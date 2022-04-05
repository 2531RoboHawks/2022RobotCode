package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.StorageSubsystem;

public class MoveBallToShooter extends SequentialCommandGroup {
  public MoveBallToShooter(StorageSubsystem storageSubsystem) {
    addCommands(
      new RunStorageBeforeShooter(ShootingConstants.ejectBallPower, storageSubsystem)
        .until(() -> !storageSubsystem.isBallBeforeShooter())
        .withTimeout(ShootingConstants.waitForBallToShootTimeout)
    );
  }
}
