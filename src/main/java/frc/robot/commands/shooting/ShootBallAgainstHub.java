// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooting;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.auto.DriveToWaypoint;
import frc.robot.commands.auto.ResetOdometry;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class ShootBallAgainstHub extends SequentialCommandGroup {
  private ShootSubsystem shootSubsystem;
  private IntakeSubsystem intakeSubsystem;

  public ShootBallAgainstHub(
    ShootSubsystem shootSubsystem,
    IntakeSubsystem intakeSubsystem,
    DriveSubsystem driveSubsystem,
    double rpm,
    double distance
  ) {
    this.shootSubsystem = shootSubsystem;
    this.intakeSubsystem = intakeSubsystem;
    double ejectBallPower = 0.3;
    double moveBallForwardPower = 0.14;
    double keepBallInPower = 0;
    addCommands(new ParallelCommandGroup(
      new RevSetSpeed(shootSubsystem, rpm),
      new SequentialCommandGroup(
        new ResetOdometry(driveSubsystem),
        new DriveToWaypoint(driveSubsystem, new Pose2d(Units.inchesToMeters(distance), 0, Rotation2d.fromDegrees(0))).withTimeout(2),
        new WaitForShooterToBeAtSpeed(shootSubsystem, rpm).withTimeout(2),
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
        new WaitCommand(0.1),
        new WaitForShooterToBeAtSpeed(shootSubsystem, rpm).withTimeout(2),
        new InstantCommand(() -> {
          shootSubsystem.setStorageBeforeShootPower(ejectBallPower);
        })
      )
    ));
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    shootSubsystem.stopEverything();
    intakeSubsystem.setEverything(false);
  }
}
