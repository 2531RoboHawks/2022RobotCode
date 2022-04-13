// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.Waypoint;
import frc.robot.commands.auto.DriveToWaypoint;
import frc.robot.subsystems.DriveSubsystem;

public class DriveThenShootTwoBalls extends SequentialCommandGroup {
  private DriveSubsystem driveSubsystem = RobotContainer.driveSubsystem;

  public DriveThenShootTwoBalls(double rpm, Waypoint driveTo) {
    addCommands(
      new ShootTwoBalls(
        rpm,
        new DriveToWaypoint(driveSubsystem, driveTo).withTimeout(2)
      )
    );
  }
}
