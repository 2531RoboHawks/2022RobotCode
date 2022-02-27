package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {
  private IntakeSubsystem intakeSubsystem;
  private boolean isDown;

  public IntakeCommand(IntakeSubsystem intakeSubsystem) {
    this.intakeSubsystem = intakeSubsystem;
  }

  @Override
  public void initialize() {
    isDown = false;
  }

  @Override
  public void execute() {
    if (RobotContainer.gamepad.getRawButtonPressed(Constants.Controls.ToggleIntakeDown)) {
      isDown = !isDown;
      System.out.println("Toggled intake: " + isDown);
    }

    intakeSubsystem.setRPM(isDown ? 0.2 : 0);
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