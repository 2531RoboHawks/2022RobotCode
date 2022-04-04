package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;
import frc.robot.Constants.Motors;
import frc.robot.Constants.Solenoids;

public class ClimbSubsystem extends SubsystemBase {
  public BetterTalonFX leftTalon = new BetterTalonFX(Motors.ClimbLeft);
  public BetterTalonFX rightTalon = new BetterTalonFX(Motors.ClimbRight);
  private Solenoid extendArmsSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Solenoids.ClimbExtend);

  private static final PIDSettings talonPid = new PIDSettings(0.014, 0, 0);
  private static final double secondsFromNeutralToFull = 1;

  public ClimbSubsystem() {
    leftTalon.configurePID(talonPid);
    leftTalon.configureRamp(secondsFromNeutralToFull);

    rightTalon.configurePID(talonPid);
    rightTalon.configureRamp(secondsFromNeutralToFull);

    setArmsExtended(false);

    rightTalon.getWPI().setNeutralMode(NeutralMode.Brake);
    leftTalon.getWPI().setNeutralMode(NeutralMode.Brake);
  }

  public double getArmExtensionTarget() {
    return leftTalon.getFixedEncoderTarget();
  }
  public void setArmExtensionTarget(double sensorUnits) {
    double MIN = 0;
    double MAX = 360000;
    if (sensorUnits < MIN) {
      System.out.println("CLIMB TOO LOW: " + sensorUnits);
      sensorUnits = MIN;
    }
    if (sensorUnits > MAX) {
      System.out.println("CLIMB TOO HIGH: " + sensorUnits);
      sensorUnits = MAX;
    }
    leftTalon.setFixedEncoderTarget(sensorUnits);
    rightTalon.setFixedEncoderTarget(sensorUnits);
  }
  public void zero() {
    leftTalon.zeroFixedEncoderTarget();
    rightTalon.zeroFixedEncoderTarget();
  }
  public void setExtensionPercent(double percent) {
    leftTalon.setPower(percent);
    rightTalon.setPower(percent);
  }

  public void stop() {
    leftTalon.stop();
    rightTalon.stop();
  }

  public void setArmsExtended(boolean extended) {
    System.out.println("Climb arms extended: " + extended);
    extendArmsSolenoid.set(extended);
  }
  public boolean areArmsExtended() {
    return extendArmsSolenoid.get();
  }
  public void toggleArmsExtended() {
    // don't use .toggle() here so that we get log messages from setArmsExtended
    setArmsExtended(!areArmsExtended());
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("Left Climb", leftTalon.getPositionSensorUnits());
    // SmartDashboard.putNumber("Right Climb", rightTalon.getPositionSensorUnits());
  }
}
