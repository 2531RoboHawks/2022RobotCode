package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.Controls;
import frc.robot.commands.auto.AutoAimShootCommand;
import frc.robot.subsystems.ShootSubsystem;

public class ShootCommand extends CommandBase {
  private ShootSubsystem shootSubsystem;

  public ShootCommand(ShootSubsystem shootSubsystem) {
    this.shootSubsystem = shootSubsystem;
    addRequirements(shootSubsystem);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    if (RobotContainer.gamepad.getLeftTriggerAxis() > .5) {
      RobotContainer.autoAimShootCommand.schedule();
    } else {
      RobotContainer.autoAimShootCommand.cancel();
    }
  }

  @Override
  public void end(boolean interrupted) {
    shootSubsystem.stopEverything();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}