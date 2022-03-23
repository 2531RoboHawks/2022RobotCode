package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class PrepareToShootBallCommand extends CommandBase {
  private ShootSubsystem shootSubsystem;

  public PrepareToShootBallCommand(ShootSubsystem shootSubsystem) {
    addRequirements(shootSubsystem);
    this.shootSubsystem = shootSubsystem;
  }

  @Override
  public void execute() {
    shootSubsystem.setStorageBeforeShootRunning(!shootSubsystem.isBallInStorage());
  }

  @Override
  public void end(boolean interrupted) {
    shootSubsystem.setStorageBeforeShootRunning(false);
  }
}
