package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.ShootSubsystem;

public class MoveBallToShooter extends SequentialCommandGroup {
  public MoveBallToShooter(ShootSubsystem shootSubsystem) {
    addCommands(
      new RunStorageBeforeShooter(ShootingConstants.ejectBallPower, shootSubsystem)
        .until(() -> !shootSubsystem.isBallInStorage())
        .withTimeout(ShootingConstants.waitForBallToShootTimeout)
    );
  }
}
