package frc.robot;

import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;

/**
 * Generic mecanum drive information container.
 */
public class MecanumDriveInfo {
  public final double frontLeft;
  public final double frontRight;
  public final double backLeft;
  public final double backRight;

  public MecanumDriveInfo(double frontLeft, double frontRight, double backLeft, double backRight) {
    this.frontLeft = frontLeft;
    this.frontRight = frontRight;
    this.backLeft = backLeft;
    this.backRight = backRight;
  }

  public MecanumDriveWheelSpeeds toWheelSpeeds() {
    return new MecanumDriveWheelSpeeds(
      frontLeft,
      frontRight,
      backLeft,
      backRight
    );
  }

  public MecanumDriveMotorVoltages toMotorVoltages() {
    return new MecanumDriveMotorVoltages(
      frontLeft,
      frontRight,
      backLeft,
      backRight
    );
  }
}
