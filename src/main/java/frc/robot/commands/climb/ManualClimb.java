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

  @Override
  public void execute() {
    double left = -InputUtils.deadzone(RobotContainer.helms.getRawAxis(1)) * 0.25;
    double right = -InputUtils.deadzone(RobotContainer.helms.getRawAxis(5)) * 0.25;
    climbSubsystem.leftTalon.setPower(left);
    climbSubsystem.rightTalon.setPower(right);
  }

  @Override
  public void end(boolean interrupted) {
    climbSubsystem.stop();
    climbSubsystem.zero();
  }
}
