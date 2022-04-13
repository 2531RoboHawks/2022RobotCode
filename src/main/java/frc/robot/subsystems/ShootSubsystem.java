package frc.robot.subsystems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;
import frc.robot.RobotContainer;
import frc.robot.Constants.CAN;

public class ShootSubsystem extends SubsystemBase {
  private BetterTalonFX revwheel = new BetterTalonFX(CAN.ShooterRevwheel)
    .configureBrakes(false)
    .configureUnitsPerRevolution(1)
    .configureFeedforward(new SimpleMotorFeedforward(0.52166, 0.10843, 0.0062646), new PIDSettings(0.10962, 0, 0));

  public ShootSubsystem() {
    idleRevwheel();
  }

  public void setRevwheelRPM(double rpm) {
    // Max RPM: ~6540 RPM
    // SmartDashboard.putNumber("Target Revwheel RPM", rpm);
    double revolutionsPerSecond = rpm / 60.0;
    revwheel.setLinearVelocityFeedforwardPID(revolutionsPerSecond);
  }

  public double getRevwheelRPM() {
    return revwheel.getRPM();
  }

  public void idleRevwheel() {
    revwheel.stop();
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("Actual Revwheel RPM", revwheel.getRPM());
  }
}
