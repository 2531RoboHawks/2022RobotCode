package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.LimelightTrackCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class ShootOneBall extends ParallelCommandGroup {
  public ShootOneBall(DriveSubsystem driveSubsystem, ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem, VisionSubsystem visionSubsystem, double distance) {
    addCommands(new LimelightTrackCommand(visionSubsystem, driveSubsystem).withTimeout(1.5));
    addCommands(new AutoShootCommand(shootSubsystem, visionSubsystem, intakeSubsystem, distance));
  }
}
