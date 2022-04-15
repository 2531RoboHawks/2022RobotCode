package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.InputUtils;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class SynchronizedClimb extends CommandBase {
  private ClimbSubsystem climbSubsystem;
  private IntakeSubsystem intakeSubsystem;

  public SynchronizedClimb(ClimbSubsystem climbSubsystem, IntakeSubsystem intakeSubsystem) {
    this.climbSubsystem = climbSubsystem;
    this.intakeSubsystem = intakeSubsystem;
    addRequirements(climbSubsystem, intakeSubsystem);
  }

  @Override
  public void initialize() {
    intakeSubsystem.setDown(true);
  }

  @Override
  public void execute() {
    boolean armsExtended = climbSubsystem.areArmsExtended();

    double power = -InputUtils.deadzone(RobotContainer.helms.getRawAxis(1));
    double delta;
    if (armsExtended && power < 0) {
      delta = power * 1500;
    } else {
      delta = power * 4500;
    }

    double oldTarget = climbSubsystem.getArmExtensionTarget();
    double newTarget = oldTarget + delta;

    double minExtension = 3000;
    double maxExtensionUnextended = 275000;
    double maxExtensionExtended = 358750;
    double maxExtension = armsExtended ? maxExtensionExtended : maxExtensionUnextended;
    if (newTarget < minExtension) {
      System.out.println("Climb too low: " + newTarget);
      newTarget = minExtension;
    }
    // This limit should be one-way. If we're already above it then allow you to remain above it.
    if (newTarget > maxExtension && oldTarget <= maxExtension) {
      System.out.println("Climb too high: " + newTarget);
      newTarget = maxExtension;
    }

    climbSubsystem.setArmExtensionTarget(newTarget);

    // boolean areSpikesOut = climbSubsystem.getSpikes();
    // if (areSpikesOut && armsExtended) {
    //   double automaticReleaseThreshold = 200000;
    //   if (oldTarget > automaticReleaseThreshold && newTarget <= automaticReleaseThreshold) {
    //     System.out.println("Automatic unspike");
    //     climbSubsystem.setSpikes(false);
    //   }
    // }
  }

  @Override
  public void end(boolean interrupted) {
    climbSubsystem.stop();
  }
}
