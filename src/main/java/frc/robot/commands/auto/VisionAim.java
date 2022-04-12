// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class VisionAim extends CommandBase {
  private DriveSubsystem driveSubsystem;
  private VisionSubsystem visionSubsystem;
  private boolean limelightBecameReady = false;

  private PIDController pidController = new PIDController(0.05, 0, 0);

  public VisionAim(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem) {
    this.visionSubsystem = visionSubsystem;
    this.driveSubsystem = driveSubsystem;
    addRequirements(this.visionSubsystem);
    addRequirements(this.driveSubsystem);
    pidController.setTolerance(1);
    pidController.setSetpoint(0);
  }

  @Override
  public void initialize() {
    visionSubsystem.ensureEnabled();
    limelightBecameReady = false;
  }

  @Override
  public void execute() {
    if (!visionSubsystem.isReady()) {
      return;
    }
    limelightBecameReady = true;
    double maxPower = 1;
    double amount = MathUtil.clamp(pidController.calculate(visionSubsystem.getX()), -maxPower, maxPower);
    if (visionSubsystem.hasValidTarget()) {
      this.driveSubsystem.drivePercent(0, 0, -amount);
    } else {
      this.driveSubsystem.stop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stop();
    visionSubsystem.noLongerNeeded();
  }

  @Override
  public boolean isFinished() {
    return limelightBecameReady && pidController.atSetpoint();
  }
}