// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class CAN {
    public static final int Pigeon = 1;

    public static final int ClimbLeft = 21;
    public static final int ClimbRight = 22;

    public static final int DriveFrontLeft = 32;
    public static final int DriveFrontRight = 31;
    public static final int DriveBackLeft = 30;
    public static final int DriveBackRight = 33;

    public static final int IntakeSpinner = 20;
    public static final int IntakeStorage = 28;

    public static final int ShooterRevwheel = 15;
    public static final int ShooterStorage = 17;
  }

  public static final class Solenoids {
    public static final int ClimbExtend = 0;
    public static final int Intake = 1;
    public static final int ClimbSpikes = 2;
  }

  public static final class DigitalInputs {
    public static final int BallStorage = 0;
  }

  public static final class Controls {
    public static final int Slow = Button.kB.value;
    public static final int Turbo = Button.kY.value;

    public static final int Shoot = Button.kLeftBumper.value;

    public static final int ToggleIntakeDown = Button.kA.value;

    public static final int PrepareToShootBall = Button.kX.value;

    public static final int HighGoal = Axis.kRightTrigger.value;
    public static final int LowGoal = Axis.kLeftTrigger.value;
    public static final int VisionHighGoal = Button.kStart.value;
  }

  public static final class HelmsControls {
    public static final int SynchronizedClimb = Button.kStart.value;
    public static final int ManualClimb = Button.kBack.value;

    public static final int ToggleIntakeDown = Button.kA.value;
    public static final int ToggleClimbExtended = Button.kB.value;

    public static final int ToggleSpikes = Button.kY.value;
  }

  public static final class ShootingConstants {
    public static final double beforeShooterEjectRPM = 1750;
    public static final double beforeShooterPrepareVolts = 1.6;

    public static final double afterIntakeVolts = 3.1;
    public static final double afterIntakeSlowVolts = 2.5;

    public static final double waitForBallToShootTimeout = 1; // TODO: tune
    public static final double waitForBallToBePreparedTimeout = 1; // TODO: tune
    public static final double waitForShooterToReachSpeedTimeout = 2; // TODO: tune

    public static final double highGoalOptimalRPM = 4000;
    public static final double highGoalOptimalDistance = Units.inchesToMeters(34);
    public static final double highGoalOptimalRotation = 15;

    public static final double lowGoalOptimalRPM = 3000;
    public static final double lowGoalOptimalDistance = Units.inchesToMeters(0);

    public static final double visionAimTimeout = 2;
  }
}
