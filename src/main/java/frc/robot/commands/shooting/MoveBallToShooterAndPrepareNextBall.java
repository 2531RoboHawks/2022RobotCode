package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.StorageSubsystem;

public class MoveBallToShooterAndPrepareNextBall extends SequentialCommandGroup {
  public MoveBallToShooterAndPrepareNextBall(StorageSubsystem storageSubsystem) {
    addCommands(new MoveBallToShooter(storageSubsystem));
    addCommands(
      new RunStorage(true, ShootingConstants.moveBallToShooterPower, storageSubsystem)
        .until(() -> storageSubsystem.isBallBeforeShooter())
        .withTimeout(ShootingConstants.waitForBallToBePreparedTimeout)
    );
  }
}
