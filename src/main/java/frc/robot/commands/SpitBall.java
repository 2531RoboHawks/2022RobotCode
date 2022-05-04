package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShootingConstants;
import frc.robot.commands.shooting.RunStorage;
import frc.robot.subsystems.StorageSubsystem;

public class SpitBall extends SequentialCommandGroup {
  public SpitBall(StorageSubsystem storageSubsystem) {
    addCommands(new RunStorage(ShootingConstants.afterIntakeSpitVolts, ShootingConstants.beforeShooterSpitRPM, storageSubsystem));
  }
}
