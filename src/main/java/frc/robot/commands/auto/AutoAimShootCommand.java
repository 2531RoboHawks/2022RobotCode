// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.LimelightTrackCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoAimShootCommand extends SequentialCommandGroup {
  /** Creates a new AutoAimShootCommand. */
  public AutoAimShootCommand(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem, ShootSubsystem shootSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new LimelightTrackCommand(visionSubsystem, driveSubsystem).withTimeout(2.5));
    addCommands(new AutoShootCommand(shootSubsystem, visionSubsystem));
  }
}
