package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.StorageSubsystem;

public class LoadBallIntoStorage extends CommandBase {
  private StorageSubsystem storageSubsystem;
  private Timer timer = new Timer();

  public LoadBallIntoStorage(StorageSubsystem storageSubsystem) {
    addRequirements(storageSubsystem);
    this.storageSubsystem = storageSubsystem;
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    if (storageSubsystem.isBallBeforeShooter()) {
      storageSubsystem.stopBeforeShooter();
      storageSubsystem.setAfterIntakeVoltsVoltage(ShootingConstants.afterIntakeSlowVolts);
      timer.reset();
    } else {
      // if (timer.hasElapsed(1.2)) {
      //   storageSubsystem.setAfterIntakeVoltsVoltage(ShootingConstants.afterIntakeStuckVolts);
      // } else if (timer.hasElapsed(1)) {
      //   storageSubsystem.setAfterIntakeVoltsVoltage(-ShootingConstants.afterIntakeVolts);
      // } else {
      // }
      storageSubsystem.setAfterIntakeVoltsVoltage(ShootingConstants.afterIntakeVolts);
      storageSubsystem.setBeforeShooterRPM(ShootingConstants.beforeShooterPrepareRPM);
    }
  }

  @Override
  public void end(boolean interrupted) {
    storageSubsystem.stopAfterIntake();
    storageSubsystem.stopBeforeShooter();
  }
}
