package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShootSubsystem;

public class ShootCommand extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private double targetShooterPosition = 0;

  public ShootCommand(ShootSubsystem shootSubsystem) {
    this.shootSubsystem = shootSubsystem;
    addRequirements(shootSubsystem);

    SmartDashboard.putNumber("Intake Target RPM", 0);
    SmartDashboard.putNumber("Revwheel Target RPM", 0);
  }
  
  @Override
  public void initialize() {

  }

  public double scale(double n) {
    if (Math.abs(n) < 0.1) return 0;
    return n;
  }

  @Override
  public void execute() {
    double y = scale(RobotContainer.gamepad.getX() * 1.0);
    if (RobotContainer.gamepad.getTrigger()) {
      y *= 2;
    }
    targetShooterPosition += y;
    shootSubsystem.setTurretPosition(targetShooterPosition);

    shootSubsystem.setIntakeRPM(SmartDashboard.getNumber("Intake Target RPM", 0));
    shootSubsystem.setRevwheelRPM(SmartDashboard.getNumber("Revwheel Target RPM", 0));
  }

  @Override
  public void end(boolean interrupted) {
    shootSubsystem.stopEverything();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}