// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AutoClimbCommand;
import frc.robot.commands.AutoTrajectoryCommand;
import frc.robot.commands.SynchronizedClimbCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ManualClimbCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.playback.PlayPlaybackCommand;
import frc.robot.commands.playback.Playback;
import frc.robot.commands.playback.RecordPlaybackCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.CompressorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
  public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
  public static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static final ShootSubsystem shootSubsystem = new ShootSubsystem();
  // public static final VisionSubsystem visionSubsystem = new VisionSubsystem();
  public static final CompressorSubsystem compressorSubsystem = new CompressorSubsystem();

  public final SynchronizedClimbCommand synchronizedClimbCommand = new SynchronizedClimbCommand(climbSubsystem, intakeSubsystem);
  public final ManualClimbCommand manualClimbCommand = new ManualClimbCommand(climbSubsystem, intakeSubsystem);
  public final AutoClimbCommand autoClimbCommand = new AutoClimbCommand(climbSubsystem, intakeSubsystem);
  public final DriveCommand driveCommand = new DriveCommand(driveSubsystem);
  public final IntakeCommand intakeCommand = new IntakeCommand(intakeSubsystem);
  public final ShootCommand shootCommand = new ShootCommand(shootSubsystem);
  
  public static Joystick gamepad = new Joystick(0);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureButtonBindings();

    SmartDashboard.putData("Synchronized climb command", synchronizedClimbCommand);
    SmartDashboard.putData("Manual climb command", manualClimbCommand);
    SmartDashboard.putData("Auto climb command", autoClimbCommand);
    SmartDashboard.putData("Drive command", driveCommand);
    SmartDashboard.putData("Intake command", intakeCommand);
    SmartDashboard.putData("Shoot command", shootCommand);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // new JoystickButton(gamepad, 6).whenHeld(new RecordPlaybackCommand(driveSubsystem));
    // new JoystickButton(gamepad, 6).whenHeld(new PlayPlaybackCommand(driveSubsystem, Playback.load("test")));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new PlayPlaybackCommand(driveSubsystem, Playback.load("test"));
  }
}
