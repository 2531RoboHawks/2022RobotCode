package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class DefaultShoot extends CommandBase {
  private ShootSubsystem shootSubsystem;

  public DefaultShoot(ShootSubsystem shootSubsystem) {
    this.shootSubsystem = shootSubsystem;
    addRequirements(shootSubsystem);
  }

  @Override
  public void initialize() {
    shootSubsystem.stopEverything();
  }

  @Override
  public void execute() {
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