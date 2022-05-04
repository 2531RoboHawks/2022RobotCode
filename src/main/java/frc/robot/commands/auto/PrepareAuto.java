package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

public class PrepareAuto extends ParallelCommandGroup {
  private DriveSubsystem driveSubsystem = RobotContainer.driveSubsystem;

  public PrepareAuto(double degreesOffset) {
    addCommands(new ResetOdometry(driveSubsystem));
    addCommands(new ZeroGyro(degreesOffset, driveSubsystem));
  }
}
