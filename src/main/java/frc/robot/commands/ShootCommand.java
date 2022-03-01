package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShootSubsystem;

public class ShootCommand extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private double turretTargetPosition = 0;

  public ShootCommand(ShootSubsystem shootSubsystem) {
    this.shootSubsystem = shootSubsystem;
    addRequirements(shootSubsystem);

    // TODO this is temporary
    SmartDashboard.putNumber("Intake Target RPM", 2000);
    SmartDashboard.putNumber("Revwheel Target RPM", 4000);
  }

  @Override
  public void initialize() {

  }

  public double scale(double n) {
    if (Math.abs(n) < 0.07) return 0;
    return n * n * Math.signum(n);
  }

  @Override
  public void execute() {
    if (RobotContainer.gamepad.getRawButton(Constants.Controls.TurnShoot)) {
      shootSubsystem.setElevatorRPM(SmartDashboard.getNumber("Intake Target RPM", 0));
      shootSubsystem.setRevwheelRPM(SmartDashboard.getNumber("Revwheel Target RPM", 0));
    } else {
      shootSubsystem.stop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    shootSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}