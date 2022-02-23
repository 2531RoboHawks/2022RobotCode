package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {
  private IntakeSubsystem intakeSubsystem;
  private boolean manualOverride;

  public IntakeCommand(IntakeSubsystem intakeSubsystem) {
    this.intakeSubsystem = intakeSubsystem;
  }

  @Override
  public void initialize() {
    manualOverride = false;
  }

  @Override
  public void execute() {
    if (RobotContainer.gamepad.getRawButtonPressed(6)) {
      manualOverride = !manualOverride;
      System.out.println("Toggled shooter manual override: " + manualOverride);
    }

    if (manualOverride) {

    } else {

    }
  }

  @Override
  public void end(boolean interrupted) {
    intakeSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}