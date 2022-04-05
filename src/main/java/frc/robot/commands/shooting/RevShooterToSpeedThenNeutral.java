package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.ShootSubsystem;

public class RevShooterToSpeedThenNeutral extends SequentialCommandGroup {
  public RevShooterToSpeedThenNeutral(double rpm, ShootSubsystem shootSubsystem) {
    addCommands(
      new WaitForShooterToBeAtSpeed(rpm, shootSubsystem)
        .deadlineWith(
          new ParallelCommandGroup(
            new StartEndCommand(
              () -> {},
              () -> {
                shootSubsystem.idleRevwheel();
              }
            ),
            new RunCommand(() -> {
              shootSubsystem.setRevwheelRPM(rpm);
            })
          )
        )
        .withTimeout(ShootingConstants.waitForShooterToReachSpeedTimeout)
    );
  }
}
