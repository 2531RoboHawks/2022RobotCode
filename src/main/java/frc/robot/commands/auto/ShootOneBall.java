package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.LimelightTrackCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class ShootOneBall extends SequentialCommandGroup {
  public ShootOneBall(DriveSubsystem driveSubsystem, ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem, VisionSubsystem visionSubsystem) {
    addCommands(new LimelightTrackCommand(visionSubsystem, driveSubsystem).withTimeout(2));
    addCommands(new AutoShootCommand(shootSubsystem, visionSubsystem, intakeSubsystem).withTimeout(4));
  }
}
