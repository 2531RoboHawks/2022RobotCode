package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {
    /* 
    TODO: 
    Figure out how the falcons switch thing works
    Do not let the falcon destroy itself please, add a current limit or something
    if we are doing a double system, keep the falcons at around the same encoder value somehow
    DONT BREAK THE FALCONS PLEASE
    */
    public ClimbSubsystem() {}

    public void stop() {
        //Todo: Stop the climb motor
    }

    @Override
    public void periodic() {}

}
