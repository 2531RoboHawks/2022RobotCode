package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class JoystickAxis {
  private GenericHID gamepad;
  private int axis;

  public JoystickAxis(GenericHID gamepad, int axis) {
    this.gamepad = gamepad;
    this.axis = axis;
  }

  public void whenAboveThreshold(double threshold, Command command) {
    CommandScheduler.getInstance().addButton(new Runnable() {
      private boolean oldValue = false;

      @Override
      public void run() {
        boolean newValue = gamepad.getRawAxis(axis) > threshold;
        if (oldValue != newValue) {
          if (newValue) {
            command.schedule();
          } else {
            command.cancel();
          }
          oldValue = newValue;
        }
      }
    });
  }

  public void whenActivated(Command command) {
    whenAboveThreshold(0.5, command);
  }
}
