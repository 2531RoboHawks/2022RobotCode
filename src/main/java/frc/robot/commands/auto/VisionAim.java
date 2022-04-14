// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShootingConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class VisionAim extends CommandBase {
  private DriveSubsystem driveSubsystem;
  private VisionSubsystem visionSubsystem;
  private boolean foundValidTarget = false;

  private PIDController pidController = new PIDController(0.08, 0, 0);

  private double maxPower = 1;

  public VisionAim(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem) {
    this.visionSubsystem = visionSubsystem;
    this.driveSubsystem = driveSubsystem;
    addRequirements(this.visionSubsystem);
    addRequirements(this.driveSubsystem);
    pidController.setTolerance(1);
  }

  @Override
  public void initialize() {
    pidController.reset();
    visionSubsystem.ensureEnabled();
    foundValidTarget = false;
  }

  @Override
  public void execute() {
    if (!visionSubsystem.isReady()) {
      return;
    }
    if (visionSubsystem.hasValidTarget()) {
      foundValidTarget = true;
      pidController.setSetpoint(Math.toDegrees(Math.atan2(ShootingConstants.optimalVisionOffsetInches, visionSubsystem.getDistance())));
      double rotation = MathUtil.clamp(pidController.calculate(visionSubsystem.getX()), -maxPower, maxPower);
      driveSubsystem.driveWheelSpeeds(driveSubsystem.calculateRobotOriented(0, 0, -rotation));
    } else {
      driveSubsystem.stop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stop();
    visionSubsystem.noLongerNeeded();
  }

  @Override
  public boolean isFinished() {
    return foundValidTarget && pidController.atSetpoint();
  }
}
