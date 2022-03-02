package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class AutoShootHeldBallCommand extends SequentialCommandGroup {
    private DriveSubsystem driveSubsystem;
    private ShootSubsystem shootSubsystem;
    private IntakeSubsystem intakeSubsystem;

    public AutoShootHeldBallCommand(DriveSubsystem driveSubsystem, ShootSubsystem shootSubsystem, IntakeSubsystem intakeSubsystem) {
        this.driveSubsystem = driveSubsystem;
        this.shootSubsystem = shootSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(driveSubsystem, shootSubsystem, intakeSubsystem);

        addCommands(new InstantCommand(() -> {
            shootSubsystem.setRevwheelRPM(4000);
        }));
        addCommands(new WaitCommand(5));
        addCommands(new InstantCommand(() -> {
            shootSubsystem.setElevatorRPM(2000);
        }));
        addCommands(new WaitCommand(1));
        addCommands(new InstantCommand(() -> {
            shootSubsystem.stopEverything();
        }));
        addCommands(new AutoDriveCommand(driveSubsystem, 3, -0.2));
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        driveSubsystem.stop();
        intakeSubsystem.stop();
        shootSubsystem.stopEverything();
    }
}
