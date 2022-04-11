package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;
import frc.robot.Constants.CAN;
import frc.robot.Constants.Solenoids;

public class ClimbSubsystem extends SubsystemBase {
  public BetterTalonFX leftTalon = new BetterTalonFX(CAN.ClimbLeft)
    .configureBrakes(true);
  public BetterTalonFX rightTalon = new BetterTalonFX(CAN.ClimbRight)
    .configureBrakes(true);

  private Solenoid extendArmsSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Solenoids.ClimbExtend);
  private Solenoid spikeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Solenoids.ClimbSpikes);

  private static final PIDSettings talonPid = new PIDSettings(0.5, 0, 0);
  private static final double secondsFromNeutralToFull = 0;
  private static final double deadband = 0.04;

  public ClimbSubsystem() {
    leftTalon.configurePID(talonPid);
    leftTalon.configureRamp(secondsFromNeutralToFull);
    leftTalon.getWPI().configNeutralDeadband(deadband);

    rightTalon.configurePID(talonPid);
    rightTalon.configureRamp(secondsFromNeutralToFull);
    rightTalon.getWPI().configNeutralDeadband(deadband);

    setArmsExtended(false);
    setSpikes(false);
  }

  public double getArmExtensionTarget() {
    return leftTalon.getFixedEncoderTarget();
  }
  public void setArmExtensionTarget(double sensorUnits) {
    SmartDashboard.putNumber("Climb Target", sensorUnits);
    double MIN = 11000;
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

  public void setSpikes(boolean spikes) {
    System.out.println("Spikes: " + spikes);
    spikeSolenoid.set(spikes);
  }
  public boolean getSpikes() {
    return spikeSolenoid.get();
  }

  public double getLeftExtension() {
    return leftTalon.getPositionSensorUnits();
  }

  public double getRightExtension() {
    return rightTalon.getPositionSensorUnits();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left Climb", getLeftExtension());
    SmartDashboard.putNumber("Right Climb", getRightExtension());
  }
}
