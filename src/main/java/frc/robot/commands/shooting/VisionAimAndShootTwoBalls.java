package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShootingConstants;
import frc.robot.commands.auto.VisionAim;
import frc.robot.commands.vision.EnableVision;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class VisionAimAndShootTwoBalls extends SequentialCommandGroup {
  public VisionAimAndShootTwoBalls(DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem) {
    addCommands(
      new VisionAim(visionSubsystem, driveSubsystem)
        .withTimeout(ShootingConstants.visionAimTimeout)
    );
    addCommands(
      new ShootTwoBalls(() -> {
        if (!visionSubsystem.isReady() || !visionSubsystem.hasValidTarget()) {
          // guess so the shooter spins up faster later
          return new SuppliedRPM(RPMCalculator.inchesToRPM(40), false);
        }
        return new SuppliedRPM(RPMCalculator.inchesToRPM(visionSubsystem.getDistance()), true);
      }).deadlineWith(new EnableVision(visionSubsystem))
    );
  }
}
