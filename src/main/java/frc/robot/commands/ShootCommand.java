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
  private long startedRevvingAt;

  public ShootCommand(ShootSubsystem shootSubsystem) {
    this.shootSubsystem = shootSubsystem;
    addRequirements(shootSubsystem);

    SmartDashboard.putNumber("Elevator Target RPM", 2000);
    SmartDashboard.putNumber("Revwheel Target RPM", 4000);
  }

  @Override
  public void initialize() {
    shootSubsystem.zeroTurret();
    startedRevvingAt = 0;
  }

  public double scale(double n) {
    if (Math.abs(n) < 0.07) return 0;
    return n * n * Math.signum(n);
  }

  @Override
  public void execute() {
    double turretAim = -(
      InputUtils.deadzone(RobotContainer.helms.getRawAxis(Constants.Controls.TurretRight)) -
      InputUtils.deadzone(RobotContainer.helms.getRawAxis(Constants.Controls.TurretLeft))
    );
    turretAim *= 0.1;
    turretTargetPosition += turretAim;
    shootSubsystem.setTurretPosition(turretTargetPosition);

    if (RobotContainer.gamepad.getRawButton(Constants.Controls.Shoot)) {
      if (startedRevvingAt == 0) {
        startedRevvingAt = System.currentTimeMillis();
      }
      shootSubsystem.setRevwheelRPM(SmartDashboard.getNumber("Revwheel Target RPM", 0));
      if (System.currentTimeMillis() > startedRevvingAt + 2000) {
        shootSubsystem.setElevatorRPM(SmartDashboard.getNumber("Elevator Target RPM", 0));
      }
    } else {
      startedRevvingAt = 0;
      shootSubsystem.stopRevwheel();
      shootSubsystem.stopElevator();
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