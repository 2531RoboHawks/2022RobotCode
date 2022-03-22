package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.Controls;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {
  private IntakeSubsystem intakeSubsystem;

  public IntakeCommand(IntakeSubsystem intakeSubsystem) {
    this.intakeSubsystem = intakeSubsystem;
    addRequirements(intakeSubsystem);
  }

  @Override
  public void initialize() {
    intakeSubsystem.enable();
    intakeSubsystem.setDown(false);
  }

  @Override
  public void execute() {
    if (RobotContainer.gamepad.getRawButtonPressed(Controls.ToggleIntakeDown)) {
      intakeSubsystem.toggleDown();
    }

    // if(RobotContainer.gamepad.getRawButton(6)) {
    //   intakeSubsystem.setStorageAfterIntakeRunning(true);
    // } else {
    //   intakeSubsystem.setStorageAfterIntakeRunning(false);
      
    // }
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
