// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.RotatePID;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class LimelightTrackCommand extends CommandBase {
  private DriveSubsystem driveSubsystem;
  private VisionSubsystem visionSubsystem;
  private PIDController pidController;

  /** Creates a new LimelightTrackCommand. */
  public LimelightTrackCommand(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem) {
    this.visionSubsystem = visionSubsystem;
    this.driveSubsystem = driveSubsystem;
    addRequirements(this.visionSubsystem);
    addRequirements(this.driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pidController = new PIDController(RotatePID.kP, RotatePID.kI, RotatePID.kD);
    pidController.setTolerance(1);
    pidController.setSetpoint(0);
    visionSubsystem.ensureEnabled();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!visionSubsystem.isReady()) {
      return;
    }
    double maxPower = 1;
    double amount = MathUtil.clamp(pidController.calculate(visionSubsystem.getX()), -maxPower, maxPower);
    if (visionSubsystem.hasValidTarget()) {
      this.driveSubsystem.drivePercent(0, 0, -amount);
    } else {
      this.driveSubsystem.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stop();
    visionSubsystem.noLongerNeeded();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return pidController.atSetpoint() && visionSubsystem.isReady();
  }
}
