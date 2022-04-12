package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.StorageSubsystem;

public class MoveBallToShooter extends SequentialCommandGroup {
  public MoveBallToShooter(StorageSubsystem storageSubsystem) {
    addCommands(
      new WaitUntilCommand(() -> !storageSubsystem.isBallBeforeShooter())
        .deadlineWith(new RunStorage(false, ShootingConstants.ejectBallPower, storageSubsystem))
        .withTimeout(ShootingConstants.waitForBallToShootTimeout)
    );
  }
}
