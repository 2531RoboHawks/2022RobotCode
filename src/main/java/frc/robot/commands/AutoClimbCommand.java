package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoClimbCommand extends CommandBase {
    private ClimbSubsystem climbSubsystem;
    private IntakeSubsystem intakeSubsystem;

    public AutoClimbCommand(ClimbSubsystem climbSubsystem, IntakeSubsystem intakeSubsystem) {
        this.climbSubsystem = climbSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(climbSubsystem, intakeSubsystem);
        // TODO
    }

    @Override
    public void initialize() {
        intakeSubsystem.setDown(true);
    }

    @Override
    public void end(boolean interrupted) {
        climbSubsystem.stop();
    }
}
