// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.LimelightTrackCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutoAimShootCommand extends ParallelCommandGroup {
  private ShootSubsystem shootSubsystem;
  private IntakeSubsystem intakeSubsystem;

  public AutoAimShootCommand(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem, ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem) {
    this.shootSubsystem = shootSubsystem;
    this.intakeSubsystem = intakeSubsystem;
    double ejectBallPower = 0.3;
    double moveBallForwardPower = 0.14;
    double keepBallInPower = 0;
    addCommands(new AutoRevCommand(shootSubsystem, visionSubsystem));
    addCommands(
      new SequentialCommandGroup(
        new LimelightTrackCommand(visionSubsystem, driveSubsystem).withTimeout(2),
        new WaitForShootToBeReady(shootSubsystem),
        new InstantCommand(() -> {
          shootSubsystem.setStorageBeforeShootPower(ejectBallPower);
        }),
        new WaitUntilCommand(() -> !shootSubsystem.isBallInStorage()),
        new InstantCommand(() -> {
          intakeSubsystem.setStorageAfterIntakeRunning(true);
          shootSubsystem.setStorageBeforeShootPower(moveBallForwardPower);
        }),
        new WaitUntilCommand(() -> shootSubsystem.isBallInStorage()),
        new InstantCommand(() -> {
          shootSubsystem.setStorageBeforeShootPower(keepBallInPower);
        }),
        new WaitCommand(0.3),
        new WaitForShootToBeReady(shootSubsystem),
        new InstantCommand(() -> {
          shootSubsystem.setStorageBeforeShootPower(ejectBallPower);
        })
      )
    );
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    shootSubsystem.stopEverything();
    intakeSubsystem.setEverything(false);
  }
}
