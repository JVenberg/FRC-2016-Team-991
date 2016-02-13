package org.usfirst.frc.team991.robot;

import org.usfirst.frc.team991.robot.commands.Collect;
import org.usfirst.frc.team991.robot.commands.FlywheelRun;
import org.usfirst.frc.team991.robot.commands.ShootandCollect;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private Joystick joystick_0 = new Joystick(0);
	private Joystick joystick_1 = new Joystick(1);

	Button trig = new JoystickButton(joystick_0, 1);
	Button collect = new JoystickButton(joystick_0, 2);
	
	public OI() {
		trig.whenPressed(new ShootandCollect());
		collect.toggleWhenActive(new Collect());
//		trig.whenReleased(new Collect());
	}
	
	public Joystick getPrimaryJoystick() {
		return joystick_0;
	}
	
	public Joystick getSecondaryJoystick() {
		return joystick_1;
	}
	
	
}

