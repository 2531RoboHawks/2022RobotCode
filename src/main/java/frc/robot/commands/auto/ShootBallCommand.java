package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class ShootBallCommand extends SequentialCommandGroup {
  private DriveSubsystem driveSubsystem;
  private ShootSubsystem shootSubsystem;

  public ShootBallCommand(DriveSubsystem driveSubsystem, ShootSubsystem shootSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.shootSubsystem = shootSubsystem;
    addRequirements(driveSubsystem, shootSubsystem);

    addCommands(new InstantCommand(() -> {
      shootSubsystem.setRevwheelRPM(4200);
    }));
    addCommands(new WaitCommand(2));
    addCommands(new InstantCommand(() -> {
      shootSubsystem.setElevatorRPM(2000);
    }));
    addCommands(new WaitCommand(1));
    addCommands(new InstantCommand(() -> {
      shootSubsystem.stopEverything();
    }));
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    driveSubsystem.stop();
    shootSubsystem.stopEverything();
  }
}
