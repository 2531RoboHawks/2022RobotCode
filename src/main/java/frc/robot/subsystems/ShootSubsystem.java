package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;

public class ShootSubsystem extends SubsystemBase {
    private BetterSparkMaxBrushless turret = new BetterSparkMaxBrushless(17);
    private static final PIDSettings turretPidSettings = new PIDSettings(0.2, 0, 0);
    private static final double turretMaxPower = 0.2;
    private static final double turretGearRatio = 215.0 / 16.0;

    private BetterTalonFX revwheel = new BetterTalonFX(15);
    private static final PIDSettings revwheelPidSettings = new PIDSettings(0.15, 0.001, 0);

    private BetterTalonFX elevatorWheel = new BetterTalonFX(9);
    private static final PIDSettings intakePidSettings = new PIDSettings(0.1, 0.001, 5);

    public ShootSubsystem() {
        turret.configurePID(turretPidSettings);
        turret.setMaxPIDOutput(turretMaxPower);
        turret.setInverted(true);

        revwheel.configurePID(revwheelPidSettings);

        elevatorWheel.setInverted(true);
        elevatorWheel.configurePID(intakePidSettings);
    }

    public void setRevwheelRPM(double rpm) {
        // Max RPM: ~5600 RPM
        revwheel.setRPM(rpm);
    }
    public void setRevwheelPercent(double percent) {
        revwheel.setPower(percent);
    }

    public void setElevatorRPM(double rpm) {
        elevatorWheel.setRPM(rpm);
    }
    public void setElevatorPercent(double percent) {
        elevatorWheel.setPower(percent);
    }

    public void setTurretPosition(double turns) {
        turret.setPosition(turns);
    }
    public void setTurretPercent(double percent) {
        turret.set(percent);
    }
    public void zeroTurret() {
        turret.zero();
    }

    public void stop() {
        revwheel.stop();
        elevatorWheel.stop();
        turret.stop();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Actual Revwheel RPM", revwheel.getRPM());
        SmartDashboard.putNumber("Actual Intake RPM", elevatorWheel.getRPM());
        SmartDashboard.putNumber("Actual Turret Position", turret.getPosition());
    }
}
