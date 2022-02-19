package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase {
  private ClimbSubsystem climbSubsystem;

  public ClimbCommand(ClimbSubsystem climbSubsystem) {
    this.climbSubsystem = climbSubsystem;
    addRequirements(climbSubsystem);
  }
  
  @Override
  public void initialize() {
    climbSubsystem.reset();
  }

  @Override
  public void execute() {
    climbSubsystem.setArmExtension(10000);
  }

  @Override
  public void end(boolean interrupted) {
    climbSubsystem.stopAll();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}