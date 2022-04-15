package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.InputUtils;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimbSubsystem;

public class ManualClimb extends CommandBase {
  private ClimbSubsystem climbSubsystem;

  public ManualClimb(ClimbSubsystem climbSubsystem) {
    this.climbSubsystem = climbSubsystem;
    // intake subsystem is used as a requirement to interrupt intake down command
    addRequirements(climbSubsystem);
  }

  private double scale(double n) {
    n *= 1.0;
    n = InputUtils.deadzone(n);
    return n * n * Math.signum(n);
  }

  @Override
  public void execute() {
    double left = -scale(RobotContainer.helms.getRawAxis(1));
    double right = -scale(RobotContainer.helms.getRawAxis(5));
    climbSubsystem.leftTalon.setPower(left);
    climbSubsystem.rightTalon.setPower(right);
  }

  @Override
  public void end(boolean interrupted) {
    climbSubsystem.stop();
    climbSubsystem.zero();
  }
}
