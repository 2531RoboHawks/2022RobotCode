// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.LimelightTrackCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutoAimShootCommand extends SequentialCommandGroup {
  public AutoAimShootCommand(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem, ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem) {
    addCommands(new LimelightTrackCommand(visionSubsystem, driveSubsystem).withTimeout(2.5));
    addCommands(new AutoShootCommand(shootSubsystem, visionSubsystem, intakeSubsystem));
    addCommands(new AutoShootCommand(shootSubsystem, visionSubsystem, intakeSubsystem));
  }
}
