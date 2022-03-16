package frc.robot.subsystems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class ShootSubsystem extends SubsystemBase {
  private BetterTalonFX revwheel = new BetterTalonFX(15)
    .configureUnitsPerRevolution(1)
    .configureFeedforward(new SimpleMotorFeedforward(0.52799, 0.10687, 0.0071385), new PIDSettings(0.11555, 0, 0));

  private BetterTalonFX elevatorWheel = new BetterTalonFX(9)
    .configureInverted(true)
    .configurePID(new PIDSettings(0.1, 0.001, 5));

  private VictorSPX traverse = new VictorSPX(8);

  public ShootSubsystem() {
    traverse.setInverted(true);
  }

  public void setRevwheelRPM(double rpm) {
    // Max RPM: ~5600 RPM
    double revolutionsPerSecond = rpm / 60.0;
    revwheel.setLinearVelocityFeedforwardPID(revolutionsPerSecond);
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

  public void idleRevwheel() {
    revwheel.stop();
  }
  public void stopElevator() {
    elevatorWheel.stop();
  }
  public void stopEverything() {
    idleRevwheel();
    stopElevator();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Actual Revwheel RPM", revwheel.getRPM());
    // SmartDashboard.putNumber("Revwheel Voltage", revwheel.getWPI().getBusVoltage());
    // SmartDashboard.putNumber("Revwheel Current", revwheel.getWPI().getSupplyCurrent());
  }
}
