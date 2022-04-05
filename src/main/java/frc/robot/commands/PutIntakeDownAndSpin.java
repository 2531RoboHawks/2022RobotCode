package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class PutIntakeDownAndSpin extends StartEndCommand {
  public PutIntakeDownAndSpin(IntakeSubsystem intakeSubsystem) {
    super(
      () -> {
        intakeSubsystem.setEverything(true);
      },
      () -> {
        intakeSubsystem.setEverything(false);
      },
      intakeSubsystem
    );
  }
}
