package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShootingConstants;
import frc.robot.commands.auto.VisionAim;
import frc.robot.commands.vision.EnableVision;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class VisionShootTwoBalls extends SequentialCommandGroup {
  public VisionShootTwoBalls(DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem) {
    addCommands(
      new VisionAim(0, visionSubsystem, driveSubsystem)
        .withTimeout(ShootingConstants.visionAimTimeout)
    );
    addCommands(
      new ShootTwoBalls(() -> {
        if (!visionSubsystem.isReady()) {
          return 0.0;
        }
        return RPMCalculator.inchesToRPM(visionSubsystem.getDistance());
      }).deadlineWith(new EnableVision(visionSubsystem))
    );
  }
}
