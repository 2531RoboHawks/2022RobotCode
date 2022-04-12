package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.shooting.VisionAim;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

// NEEDS TO BE REFACTORED
@Deprecated
public class ShootOneBall extends ParallelCommandGroup {
  public ShootOneBall(DriveSubsystem driveSubsystem, ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem, VisionSubsystem visionSubsystem, double distance) {
    addCommands(new VisionAim(visionSubsystem, driveSubsystem).withTimeout(1.5));
    addCommands(new AutoShoot(shootSubsystem, visionSubsystem, intakeSubsystem, distance));
  }
}
