package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.StorageSubsystem;

public class LoadBallIntoStorage extends CommandBase {
  private StorageSubsystem storageSubsystem;

  public LoadBallIntoStorage(StorageSubsystem storageSubsystem) {
    addRequirements(storageSubsystem);
    this.storageSubsystem = storageSubsystem;
  }

  @Override
  public void execute() {
    // TODO
    if (storageSubsystem.isBallBeforeShooter()) {

    } else {

    }
    storageSubsystem.setBeforeShooterPower(ShootingConstants.prepareBallPower);
    storageSubsystem.setAfterIntakeRunning(true);
  }

  @Override
  public void end(boolean interrupted) {
    storageSubsystem.stopAfterIntake();
    storageSubsystem.stopBeforeShooter();
  }
}
