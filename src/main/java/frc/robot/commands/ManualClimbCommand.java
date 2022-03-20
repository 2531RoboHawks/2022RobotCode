package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.InputUtils;
import frc.robot.RobotContainer;
import frc.robot.Constants.HelmsControls;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class ManualClimbCommand extends CommandBase {
  private ClimbSubsystem climbSubsystem;
  private IntakeSubsystem intakeSubsystem;

  public ManualClimbCommand(ClimbSubsystem climbSubsystem, IntakeSubsystem intakeSubsystem) {
    this.climbSubsystem = climbSubsystem;
    this.intakeSubsystem = intakeSubsystem;
    addRequirements(climbSubsystem, intakeSubsystem);
  }

  @Override
  public void initialize() {
    intakeSubsystem.stop();
  }

  @Override
  public void execute() {
    if (RobotContainer.helms.getRawButtonPressed(HelmsControls.ToggleClimbExtended)) {
      climbSubsystem.toggleArmsExtended();
    }
    if (RobotContainer.helms.getRawButtonPressed(HelmsControls.ToggleIntakeDown)) {
      intakeSubsystem.toggleDown();
    }

    double left = -InputUtils.deadzone(RobotContainer.helms.getRawAxis(1)) * 0.25;
    double right = -InputUtils.deadzone(RobotContainer.helms.getRawAxis(5)) * 0.25;
    climbSubsystem.leftTalon.setPower(left);
    climbSubsystem.rightTalon.setPower(right);
  }

  @Override
  public void end(boolean interrupted) {
    climbSubsystem.stop();
  }
}
