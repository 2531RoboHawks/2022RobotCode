package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.InputUtils;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShootSubsystem;

public class ShootCommand extends CommandBase {
  private ShootSubsystem shootSubsystem;
  private double turretTargetPosition = 0;

  public ShootCommand(ShootSubsystem shootSubsystem) {
    this.shootSubsystem = shootSubsystem;
    addRequirements(shootSubsystem);

    SmartDashboard.putNumber("Elevator Target RPM", 2000);
    SmartDashboard.putNumber("Revwheel Target RPM", 5000);
  }

  @Override
  public void initialize() {
    shootSubsystem.zeroTurret();
    
  }

  public double scale(double n) {
    if (Math.abs(n) < 0.07) return 0;
    return n * n * Math.signum(n);
  }

  @Override
  public void execute() {
    // double turretAim = -(
    //   InputUtils.deadzone(RobotContainer.helms.getRawAxis(Constants.Controls.TurretRight)) -
    //   InputUtils.deadzone(RobotContainer.helms.getRawAxis(Constants.Controls.TurretLeft))
    // );
    // turretAim *= 0.1;
    // turretTargetPosition += turretAim;
    shootSubsystem.setTurretPosition(turretTargetPosition);

    if (RobotContainer.gamepad.getRawButton(Constants.Controls.Shoot)) {
      shootSubsystem.setRevwheelRPM(SmartDashboard.getNumber("Revwheel Target RPM", 0));
    } else {
      shootSubsystem.stopRevwheel();
    }
    if (RobotContainer.gamepad.getRawButton(Constants.Controls.Elevator)) {
      shootSubsystem.setElevatorRPM(SmartDashboard.getNumber("Elevator Target RPM", 0));
    } else {
      shootSubsystem.stopElevator();
    }

    if (RobotContainer.gamepad.getRawButton(Constants.Controls.Traverse)) {
      shootSubsystem.setTraversePercent(0.8);
    } else if (RobotContainer.gamepad.getRawButton(Constants.Controls.TraverseReverse)) {
      shootSubsystem.setTraversePercent(-0.8);
    } else {
      shootSubsystem.stopTraverse();
    }
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