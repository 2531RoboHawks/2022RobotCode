package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.Controls;
import frc.robot.subsystems.ShootSubsystem;

public class ShootCommand extends CommandBase {
  private ShootSubsystem shootSubsystem;

  public ShootCommand(ShootSubsystem shootSubsystem) {
    this.shootSubsystem = shootSubsystem;
    addRequirements(shootSubsystem);

    SmartDashboard.putNumber("Elevator Target RPM", 2000);
    SmartDashboard.putNumber("Revwheel Target RPM", 5600);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    if (RobotContainer.gamepad.getRawButton(Controls.Shoot)) {
      shootSubsystem.setRevwheelRPM(SmartDashboard.getNumber("Revwheel Target RPM", 0));
    } else if (RobotContainer.gamepad.getRightBumper()) {
      shootSubsystem.setRevwheelRPM(0);
    } else {
      shootSubsystem.idleRevwheel();
    }

    shootSubsystem.setStorageBeforeShootRunning(RobotContainer.gamepad.getRawButton(8));
    shootSubsystem.setStorageAfterIntakeRunning(RobotContainer.gamepad.getRawButton(7));

    // if (RobotContainer.gamepad.getRawButton(Controls.Elevator)) {
    //   shootSubsystem.setElevatorRPM(SmartDashboard.getNumber("Elevator Target RPM", 0));
    // } else {
    //   shootSubsystem.stopElevator();
    // }

    // if (RobotContainer.gamepad.getRawButton(Controls.Traverse)) {
    //   shootSubsystem.setTraversePercent(0.85);
    // } else if (RobotContainer.gamepad.getRawButton(Controls.TraverseReverse)) {
    //   shootSubsystem.setTraversePercent(-0.85);
    // } else {
    //   shootSubsystem.stopTraverse();
    // }
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