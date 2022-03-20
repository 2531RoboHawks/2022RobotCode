package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class WallMaria extends SequentialCommandGroup {
  public WallMaria(DriveSubsystem drive, IntakeSubsystem intake, ShootSubsystem shoot) {
    addRequirements(drive, intake, shoot);
    // TODO
  }
}
