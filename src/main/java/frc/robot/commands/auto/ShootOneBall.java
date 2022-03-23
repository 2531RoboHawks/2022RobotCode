package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class ShootOneBall extends SequentialCommandGroup {
  public ShootOneBall(ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem, VisionSubsystem visionSubsystem) {
    addCommands(new AutoShootCommand(shootSubsystem, visionSubsystem, intakeSubsystem).withTimeout(4));
  }
}
