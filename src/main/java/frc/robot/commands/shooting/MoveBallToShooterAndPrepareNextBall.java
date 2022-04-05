package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class MoveBallToShooterAndPrepareNextBall extends SequentialCommandGroup {
  public MoveBallToShooterAndPrepareNextBall(ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem) {
    addCommands(new MoveBallToShooter(shootSubsystem));
    addCommands(
      new ParallelCommandGroup(
        new RunStorageBeforeShooter(ShootingConstants.moveBallForwardPower, shootSubsystem),
        new RunStorageAfterIntake(intakeSubsystem)
      )
        .until(() -> shootSubsystem.isBallInStorage())
        .withTimeout(ShootingConstants.waitForBallToBePreparedTimeout)
    );
  }
}
