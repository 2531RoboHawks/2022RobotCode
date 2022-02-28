package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.InputUtils;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class SynchronizedClimbCommand extends CommandBase {
  private ClimbSubsystem climbSubsystem;
  private IntakeSubsystem intakeSubsystem;
  private double target;

  public SynchronizedClimbCommand(ClimbSubsystem climbSubsystem, IntakeSubsystem intakeSubsystem) {
    this.climbSubsystem = climbSubsystem;
    this.intakeSubsystem = intakeSubsystem;
    addRequirements(climbSubsystem, intakeSubsystem);
  }

  @Override
  public void initialize() {
    intakeSubsystem.setDown(true);
    climbSubsystem.zero();
    target = 0;
  }

  @Override
  public void execute() {
    if (RobotContainer.gamepad.getRawButtonPressed(Constants.Controls.ToggleClimbArmManually)) {
      climbSubsystem.togglePistonExtended();
    }

    double power = -InputUtils.deadzone(RobotContainer.gamepad.getRawAxis(1));
    target += power * 1500;
    climbSubsystem.setArmExtension(target);
  }

  @Override
  public void end(boolean interrupted) {
    climbSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
