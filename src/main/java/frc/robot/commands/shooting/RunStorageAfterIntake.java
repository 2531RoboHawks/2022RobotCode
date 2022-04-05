package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class RunStorageAfterIntake extends StartEndCommand {
  public RunStorageAfterIntake(IntakeSubsystem intakeSubsystem) {
    super(
      () -> {
        intakeSubsystem.setStorageAfterIntakeRunning(true);
      },
      () -> {
        intakeSubsystem.setStorageAfterIntakeRunning(false);
      },
      intakeSubsystem
    );
  }
}
