package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
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
    // if (RobotContainer.gamepad.getRawButton(8)) {
    //   // TODO: Need to see if the encoder position is saved between restarts, then this is easy
    //   // Otherwise we need to have robot reset encoder consistently
    //   // turretTargetPosition = 0;
    // } else {
    //   double delta = scale(RobotContainer.gamepad.getX() * 0.3);
    //   if (RobotContainer.gamepad.getTrigger()) {
    //     delta *= 2;
    //   }
    //   turretTargetPosition += delta;
    // }
    // shootSubsystem.setTurretPosition(turretTargetPosition);  
    // SmartDashboard.putNumber("Turret Target Position", turretTargetPosition);

    // TODO this is temporary
    if (RobotContainer.gamepad.getRawButton(5)) {
      shootSubsystem.setIntakeRPM(SmartDashboard.getNumber("Intake Target RPM", 0));
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