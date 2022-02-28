package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.InputUtils;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class ManualClimbCommand extends CommandBase {
    private ClimbSubsystem climbSubsystem;
    private IntakeSubsystem intakeSubsystem;

    public ManualClimbCommand(ClimbSubsystem climbSubsystem, IntakeSubsystem intakeSubsystem) {
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
        if (RobotContainer.gamepad.getRawButtonPressed(Constants.Controls.ToggleClimbArmManually)) {
            climbSubsystem.togglePistonExtended();
        }
        // Temporary
        if (RobotContainer.gamepad.getRawButtonPressed(Constants.Controls.ToggleIntakeDown)) {
            intakeSubsystem.toggleDown();
        }
        // if (intakeSubsystem.isDown()) {
        //     intakeSubsystem.setPower(0.7);
        // } else {
        //     intakeSubsystem.stop();
        // }

        double left = -InputUtils.deadzone(RobotContainer.gamepad.getRawAxis(1)) * 0.25;
        double right = -InputUtils.deadzone(RobotContainer.gamepad.getRawAxis(5)) * 0.25;
        climbSubsystem.leftTalon.setPower(left);
        climbSubsystem.rightTalon.setPower(right);
    }

    @Override
    public void end(boolean interrupted) {
        climbSubsystem.stop();
    }
}
