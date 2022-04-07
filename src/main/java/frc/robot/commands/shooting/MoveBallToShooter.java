package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.StorageSubsystem;

public class MoveBallToShooter extends SequentialCommandGroup {
  public MoveBallToShooter(StorageSubsystem storageSubsystem) {
    addCommands(
      new SequentialCommandGroup(
        new WaitUntilCommand(() -> !storageSubsystem.isBallBeforeShooter()),
        new WaitCommand(0.1)
      )
        .deadlineWith(new RunStorageBeforeShooter(ShootingConstants.ejectBallPower, storageSubsystem))
        .withTimeout(ShootingConstants.waitForBallToShootTimeout)
    );
  }
}
