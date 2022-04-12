package frc.robot.commands.shooting;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class ShootTwoBalls extends SequentialCommandGroup {
  private ShootSubsystem shootSubsystem = RobotContainer.shootSubsystem;
  private StorageSubsystem storageSubsystem = RobotContainer.storageSubsystem;

  public ShootTwoBalls(Supplier<Double> rpmSupplier) {
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

  public ShootTwoBalls(double fixedRpm) {
    this(() -> fixedRpm);
  }
}
