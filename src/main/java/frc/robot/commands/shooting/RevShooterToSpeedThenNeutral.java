package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.ShootSubsystem;

public class RevShooterToSpeedThenNeutral extends SequentialCommandGroup {
  public RevShooterToSpeedThenNeutral(double rpm, ShootSubsystem shootSubsystem) {
    addCommands(
      new WaitForShooterToBeAtSpeed(rpm, shootSubsystem)
        .deadlineWith(new RevShooterToSpeed(rpm, shootSubsystem))
        .withTimeout(ShootingConstants.waitForShooterToReachSpeedTimeout)
    );
  }
}
