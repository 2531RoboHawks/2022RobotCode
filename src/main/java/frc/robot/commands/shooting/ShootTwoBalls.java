package frc.robot.commands.shooting;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class ShootTwoBalls extends SequentialCommandGroup {
  private ShootSubsystem shootSubsystem = RobotContainer.shootSubsystem;
  private StorageSubsystem storageSubsystem = RobotContainer.storageSubsystem;

  public ShootTwoBalls(Supplier<Double> rpmSupplier, Command runSimultaneously) {
    addCommands(
      runSimultaneously.deadlineWith(new RevShooterToSpeed(rpmSupplier, shootSubsystem))
    );
    addCommands(
      new ParallelDeadlineGroup(
        new WaitForShooterToBeStable(shootSubsystem),
        new RevShooterToSpeed(rpmSupplier, shootSubsystem)
      )
    );
    addCommands(
      new MoveBallToShooter(storageSubsystem)
    );
    addCommands(
      new SequentialCommandGroup(
        new LoadBallIntoStorageUntilLoaded(storageSubsystem),
        new WaitCommand(0.2),
        new WaitForShooterToBeStable(shootSubsystem)
      ).deadlineWith(new RevShooterToSpeed(rpmSupplier, shootSubsystem))
    );
    addCommands(
      new MoveBallToShooter(storageSubsystem)
    );
  }

  public ShootTwoBalls(Supplier<Double> rpmSupplier) {
    this(rpmSupplier, new InstantCommand());
  }

  public ShootTwoBalls(double fixedRpm) {
    this(() -> fixedRpm);
  }

  public ShootTwoBalls(double fixedRpm, Command runSimultaneously) {
    this(() -> fixedRpm, runSimultaneously);
  }
}
