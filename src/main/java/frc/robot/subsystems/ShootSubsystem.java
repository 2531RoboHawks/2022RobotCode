package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterSparkMaxBrushless;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class ShootSubsystem extends SubsystemBase {
    private BetterSparkMaxBrushless turret = new BetterSparkMaxBrushless(17);
    private static final PIDSettings turretPidSettings = new PIDSettings(0.2, 0, 0);
    private static final double turretMaxPower = 0.2;
    private static final double turretGearRatio = 215.0 / 16.0;

    private BetterTalonFX revwheel = new BetterTalonFX(15);
    private static final PIDSettings revwheelPidSettings = new PIDSettings(0.075, 0.00009, 0);

    private BetterTalonFX elevatorWheel = new BetterTalonFX(9);
    private static final PIDSettings elevatorPidSettings = new PIDSettings(0.1, 0.001, 5);

    private VictorSPX traverse = new VictorSPX(8);


    public ShootSubsystem() {
        turret.configurePID(turretPidSettings);
        turret.setMaxPIDOutput(turretMaxPower);
        turret.setInverted(true);

        revwheel.configurePID(revwheelPidSettings);
        revwheel.configureRamp(0.5);

        elevatorWheel.setInverted(true);
        elevatorWheel.configurePID(elevatorPidSettings);
    }

    public void setRevwheelRPM(double rpm) {
        // Max RPM: ~5600 RPM
        revwheel.setRPM(rpm);
    }
    public void setRevwheelPercent(double percent) {
        revwheel.setPower(percent);
    }

    public void setTraversePercent(double percent) {
        traverse.set(VictorSPXControlMode.PercentOutput, percent);
    }
    public void stopTraverse() {
        traverse.set(VictorSPXControlMode.PercentOutput, 0);
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
        System.out.println("Set turret percent: " + percent);
        turret.set(percent);
    }
    public void zeroTurret() {
        System.out.println("Zeroed turret");
        turret.zero();
    }

    public void stopRevwheel() {
        revwheel.stop();
    }
    public void stopElevator() {
        elevatorWheel.stop();
    }
    public void stopTurret() {
        turret.stop();
    }
    public void stopEverything() {
        stopRevwheel();
        stopElevator();
        stopTurret();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Actual Revwheel RPM", revwheel.getRPM());
        SmartDashboard.putNumber("Actual Elevator RPM", elevatorWheel.getRPM());
        SmartDashboard.putNumber("Actual Turret Position", turret.getPosition());
    }
}
