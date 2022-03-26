// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.Controls;
import frc.robot.Constants.HelmsControls;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ResetIntakeCommand;
import frc.robot.commands.IntakeDownCommand;
import frc.robot.commands.ManualClimbCommand;
import frc.robot.commands.PrepareToShootBallCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.SynchronizedClimbCommand;
import frc.robot.commands.ToggleClimbExtendCommand;
import frc.robot.commands.ToggleIntakeCommand;
import frc.robot.commands.auto.AutoAimShootCommand;
import frc.robot.commands.auto.ShootBallAgainstHub;
import frc.robot.commands.auto.ShootOneBall;
import frc.robot.commands.auto.TwoBallAuto;
import frc.robot.commands.auto.Taxi;
import frc.robot.commands.auto.TheRumbling;
import frc.robot.commands.auto.TrajectoryCommand;
import frc.robot.commands.auto.WallMaria;
import frc.robot.commands.auto.Waypoint;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.CompressorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public static final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
  public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
  public static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static final ShootSubsystem shootSubsystem = new ShootSubsystem();
  public static final VisionSubsystem visionSubsystem = new VisionSubsystem();
  public static final CompressorSubsystem compressorSubsystem = new CompressorSubsystem();

  public static XboxController gamepad = new XboxController(0);
  public static XboxController helms = new XboxController(1);

  private SendableChooser<Command> autoChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureButtonBindings();

    driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem));
    intakeSubsystem.setDefaultCommand(new ResetIntakeCommand(intakeSubsystem));
    shootSubsystem.setDefaultCommand(new ShootCommand(shootSubsystem));

    SmartDashboard.putData("Manual Climb Command", new ManualClimbCommand(climbSubsystem, intakeSubsystem));

    autoChooser.addOption("None", null);
    autoChooser.addOption(
      // TODO: remove; was for testing only
      "8 Ball Auto",
      TrajectoryCommand.fromWaypoints(
        driveSubsystem,
        Waypoint.LEFT,
        Waypoint.UP,
        Waypoint.DOWN
      ).resetOdometry()
    );
    autoChooser.addOption(
      "Taxi",
      new Taxi(driveSubsystem)
    );
    // autoChooser.addOption(
    //   "One Ball",
    //   new SequentialCommandGroup(
    //     new ShootOneBall(driveSubsystem, shootSubsystem, intakeSubsystem, visionSubsystem),
    //     new Taxi(driveSubsystem)
    //   )
    // );
    autoChooser.setDefaultOption(
      "Two Ball",
      new TwoBallAuto(driveSubsystem, shootSubsystem, intakeSubsystem, visionSubsystem)
    );
    autoChooser.addOption("The Rumbling", new TheRumbling(driveSubsystem, intakeSubsystem, shootSubsystem, visionSubsystem));
    SmartDashboard.putData(autoChooser);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(helms, HelmsControls.SynchronizedClimb).toggleWhenActive(new SynchronizedClimbCommand(climbSubsystem, intakeSubsystem));
    new JoystickButton(helms, HelmsControls.ManualClimb).toggleWhenActive(new ManualClimbCommand(climbSubsystem, intakeSubsystem));
    new JoystickButton(helms, HelmsControls.ToggleIntakeDown).whenPressed(new ToggleIntakeCommand(intakeSubsystem));
    new JoystickButton(helms, HelmsControls.ToggleClimbExtended).whenPressed(new ToggleClimbExtendCommand(climbSubsystem));
    new JoystickButton(gamepad, Controls.ToggleIntakeDown).toggleWhenActive(new ParallelCommandGroup(new IntakeDownCommand(intakeSubsystem), new PrepareToShootBallCommand(shootSubsystem)));
    new JoystickAxis(gamepad, Controls.AutoAimShoot).whenAboveThreshold(
      0.5,
      new ShootBallAgainstHub(shootSubsystem, intakeSubsystem, driveSubsystem, 3950, 32)
    );
    new JoystickAxis(gamepad, Controls.EjectBall).whenAboveThreshold(
      0.5,
      new ShootBallAgainstHub(shootSubsystem, intakeSubsystem, driveSubsystem, 2300, 12)
    );
    new JoystickButton(gamepad, Controls.PrepareToShootBall).whenHeld(new PrepareToShootBallCommand(shootSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
