package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase {
  private ClimbSubsystem climbSubsystem;
  private boolean manualOverride;

  public ClimbCommand(ClimbSubsystem climbSubsystem) {
    this.climbSubsystem = climbSubsystem;
    addRequirements(climbSubsystem);
  }
  
  @Override
  public void initialize() {
    climbSubsystem.reset();
    manualOverride = false;
  }

  @Override
  public void execute() {
    if (RobotContainer.gamepad.getRawButtonPressed(7)) {
      manualOverride = !manualOverride;
      System.out.println("Toggled climber manual override: " + manualOverride);
    }

    if (manualOverride) {

    } else {
      climbSubsystem.setArmExtension(10000);
    }
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