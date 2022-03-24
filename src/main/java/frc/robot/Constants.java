// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController.Axis;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class Solenoids {
    public static final int ClimbExtend = 0;
    public static final int Intake = 1;
    public static final int ClimbGrab = 2;
  }

  public static final class RotatePID {
    // (in degrees)
    public static final double kP = 0.013;
    public static final double kI = 0.01;
    public static final double kD = 0;
  }

  public static final class Controls {
    public static final int Slow = 2;
    public static final int Turbo = 3;

    public static final int Shoot = 5;

    public static final int ToggleIntakeDown = 1;

    public static final int PrepareToShootBall = 3;
    public static final int AutoAimShoot = Axis.kLeftTrigger.value;
    public static final int AutoShoot = 10;
  }

  public static final class HelmsControls {
    public static final int SynchronizedClimb = 8;
    public static final int ManualClimb = 7;

    public static final int ToggleIntakeDown = 1;
    public static final int ToggleClimbExtended = 2;
    public static final int ToggleClimbGrab = 3;
  }
}
