package frc.robot.commands.shooting;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.ShootSubsystem;

public class WaitForShooterToBeReady extends SequentialCommandGroup {
  public WaitForShooterToBeReady(Supplier<SuppliedRPM> rpmSupplier, ShootSubsystem shootSubsystem) {
    addCommands(new ParallelRaceGroup(
      // new WaitForShooterToBeAtSpeed(rpmSupplier, shootSubsystem),
      new WaitForShooterToBeStable(() -> rpmSupplier.get().isReady(), shootSubsystem)
    ).withTimeout(ShootingConstants.waitForShooterToBeReadyTimeout));
    addCommands(new InstantCommand(() -> {
      System.out.println("Shooter is ready: current: " + shootSubsystem.getRevwheelRPM() + " target: " + rpmSupplier.get().getRPM());
    }));
  }
}
