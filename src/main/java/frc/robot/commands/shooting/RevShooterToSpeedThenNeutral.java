package frc.robot.commands.shooting;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShootSubsystem;

@Deprecated
public class RevShooterToSpeedThenNeutral extends SequentialCommandGroup {
  public RevShooterToSpeedThenNeutral(Supplier<Double> rpmSupplier, ShootSubsystem shootSubsystem) {
    // addCommands(
    //   new WaitForShooterToBeAtSpeed(rpmSupplier, shootSubsystem)
    //     .deadlineWith(new RevShooterToSpeed(rpmSupplier, shootSubsystem))
    // );
  }
}
