package org.usfirst.frc.team991.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private Joystick joystick_0 = new Joystick(0);
	private Joystick joystick_1 = new Joystick(1);
	
	public Joystick getPrimaryJoystick() {
		return joystick_0;
	}
	
	public Joystick getSecondaryJoystick() {
		return joystick_1;
	}
	
}

