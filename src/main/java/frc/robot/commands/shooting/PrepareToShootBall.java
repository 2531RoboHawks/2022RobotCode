package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.StorageSubsystem;

public class PrepareToShootBall extends CommandBase {
  private StorageSubsystem storageSubsystem;

  public PrepareToShootBall(StorageSubsystem storageSubsystem) {
    addRequirements(storageSubsystem);
    this.storageSubsystem = storageSubsystem;
  }

  @Override
  public void execute() {
    storageSubsystem.setBeforeShooterPower(ShootingConstants.prepareBallPower);
    storageSubsystem.setAfterIntakeRunning(true);
  }

  @Override
  public boolean isFinished() {
    return storageSubsystem.isBallBeforeShooter();
  }

  @Override
  public void end(boolean interrupted) {
    storageSubsystem.stopAfterIntake();
    storageSubsystem.stopBeforeShooter();
  }
}
