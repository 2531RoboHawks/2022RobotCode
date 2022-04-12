package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.StorageSubsystem;

public class RunStorage extends ParallelCommandGroup {
  public RunStorage(double afterIntake, double beforeShooter, StorageSubsystem storageSubsystem) {
    addCommands(
      new RunCommand(() -> {
        System.out.println(beforeShooter);
        storageSubsystem.setAfterIntakeVoltsVoltage(afterIntake);
        storageSubsystem.setBeforeShooterRPM(beforeShooter);
      })
    );
    addCommands(
      new StartEndCommand(
        () -> {},
        () -> {
          storageSubsystem.stopAfterIntake();
          storageSubsystem.stopBeforeShooter();
        }
      )
    );
  }
}
