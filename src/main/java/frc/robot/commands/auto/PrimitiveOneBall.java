package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class PrimitiveOneBall extends SequentialCommandGroup {
  public PrimitiveOneBall(DriveSubsystem drive, ShootSubsystem shoot) {
    addRequirements(drive, shoot);
    // TODO
    addCommands(new Taxi(drive));
  }
}
