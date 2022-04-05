package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class MoveBallToShooterAndPrepareNextBall extends SequentialCommandGroup {
  public MoveBallToShooterAndPrepareNextBall(StorageSubsystem storageSubsystem, IntakeSubsystem intakeSubsystem) {
    addCommands(new MoveBallToShooter(storageSubsystem));
    addCommands(
      new ParallelCommandGroup(
        new RunStorageBeforeShooter(ShootingConstants.moveBallForwardPower, storageSubsystem),
        new RunStorageAfterIntake(intakeSubsystem)
      )
        .until(() -> storageSubsystem.isBallBeforeShooter())
        .withTimeout(ShootingConstants.waitForBallToBePreparedTimeout)
    );
  }
}
