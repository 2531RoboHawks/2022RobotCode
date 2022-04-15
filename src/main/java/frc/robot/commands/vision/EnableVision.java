package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.VisionSubsystem;

public class EnableVision extends StartEndCommand {
  public EnableVision(VisionSubsystem visionSubsystem) {
    super(
      () -> {
        visionSubsystem.ensureEnabled();
      },
      () -> {
        visionSubsystem.noLongerNeeded();
      }
    );
  }
}
