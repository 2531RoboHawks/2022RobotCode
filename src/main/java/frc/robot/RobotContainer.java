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
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.Controls;
import frc.robot.Constants.HelmsControls;
import frc.robot.Constants.ShootingConstants;
import frc.robot.commands.PutIntakeDownAndSpin;
import frc.robot.commands.SpitBall;
import frc.robot.commands.ToggleIntakeDown;
import frc.robot.commands.auto.OneBallAndDriveAllTheWayBack;
import frc.robot.commands.auto.PrepareAuto;
import frc.robot.commands.auto.TwoBallAuto;
import frc.robot.commands.auto.WackyThreeBall;
import frc.robot.commands.climb.ManualClimb;
import frc.robot.commands.climb.SynchronizedClimb;
import frc.robot.commands.climb.ToggleClimbExtended;
import frc.robot.commands.climb.ToggleSpikes;
import frc.robot.commands.climb.ZeroClimb;
import frc.robot.commands.defaults.DefaultDrive;
import frc.robot.commands.defaults.DefaultIntake;
import frc.robot.commands.defaults.DefaultShoot;
import frc.robot.commands.shooting.LoadBallIntoStorage;
import frc.robot.commands.shooting.RPMCalculator;
import frc.robot.commands.shooting.ShootTwoBalls;
import frc.robot.commands.shooting.VisionAimAndShootTwoBalls;
import frc.robot.commands.shooting.DriveThenShootTwoBalls;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.CompressorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;
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
  public static final StorageSubsystem storageSubsystem = new StorageSubsystem();
  public static final VisionSubsystem visionSubsystem = new VisionSubsystem();
  public static final CompressorSubsystem compressorSubsystem = new CompressorSubsystem();

  public static XboxController gamepad = new XboxController(0);
  public static XboxController helms = new XboxController(1);

  private SendableChooser<Command> autoChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureButtonBindings();

    driveSubsystem.setDefaultCommand(new DefaultDrive(driveSubsystem));
    intakeSubsystem.setDefaultCommand(new DefaultIntake(intakeSubsystem));
    shootSubsystem.setDefaultCommand(new DefaultShoot(shootSubsystem));

    SmartDashboard.putData("Zero Climber", new ZeroClimb(climbSubsystem));

    autoChooser.addOption("None", null);
    autoChooser.addOption("One Ball And Back Out", new SequentialCommandGroup(
      new PrepareAuto(180),
      new OneBallAndDriveAllTheWayBack()
    ));
    autoChooser.addOption("Wacky Three Ball", new SequentialCommandGroup(
      new PrepareAuto(180),
      new WackyThreeBall()
    ));
    autoChooser.setDefaultOption("Two Ball Long", new SequentialCommandGroup(
      new PrepareAuto(180),
      new TwoBallAuto(AutoConstants.distanceToSecondBallLong)
    ));
    autoChooser.addOption("Two Ball Angled", new SequentialCommandGroup(
      new PrepareAuto(180 + 30),
      new TwoBallAuto(AutoConstants.distanceToSecondBallLong)
    ));
    autoChooser.addOption("Two Ball Short", new SequentialCommandGroup(
      new PrepareAuto(90),
      new TwoBallAuto(AutoConstants.distnaceToSecondBallShort)
    ));
    SmartDashboard.putData(autoChooser);

    RPMCalculator.setup();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(helms, HelmsControls.SynchronizedClimb).toggleWhenActive(new SynchronizedClimb(climbSubsystem, intakeSubsystem));
    new JoystickButton(helms, HelmsControls.ManualClimb).toggleWhenActive(new ManualClimb(climbSubsystem));
    new JoystickButton(helms, HelmsControls.ToggleIntakeDown).whenPressed(new ToggleIntakeDown(intakeSubsystem));
    new JoystickButton(helms, HelmsControls.ToggleClimbExtended).whenPressed(new ToggleClimbExtended(climbSubsystem));
    new JoystickButton(helms, HelmsControls.ToggleSpikes).whenPressed(new ToggleSpikes(climbSubsystem));

    new JoystickButton(gamepad, Controls.ToggleIntakeDown).toggleWhenActive(
      new ParallelCommandGroup(
        new PutIntakeDownAndSpin(intakeSubsystem),
        new LoadBallIntoStorage(storageSubsystem)
      )
    );
    new JoystickAxis(gamepad, Controls.HighGoal).whenActivated(
      new DriveThenShootTwoBalls(
        ShootingConstants.highGoalOptimalRPM,
        new Waypoint(ShootingConstants.highGoalOptimalDistance, 0, ShootingConstants.highGoalOptimalRotation)
      )
    );
    new JoystickAxis(gamepad, Controls.LowGoal).whenActivated(
      new DriveThenShootTwoBalls(
        ShootingConstants.lowGoalOptimalRPM,
        new Waypoint(ShootingConstants.lowGoalOptimalDistance, 0, 0)
      )
    );
    new JoystickButton(gamepad, Controls.VisionHighGoal).whileActiveOnce(
      new VisionAimAndShootTwoBalls(driveSubsystem, visionSubsystem, true)
    );
    new JoystickButton(gamepad, Controls.PrepareToShootBall).whenHeld(new LoadBallIntoStorage(storageSubsystem));
    new JoystickButton(gamepad, Controls.SpitBall).whenHeld(new SpitBall(storageSubsystem));
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
