package org.usfirst.frc.team991.robot;

import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing;
import org.usfirst.frc.team991.robot.commands.StartCollector;
import org.usfirst.frc.team991.robot.commands.groups.ShootandCollect;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private Joystick joystick_0 = new Joystick(0);
	private Joystick joystick_1 = new Joystick(1);

	Button trig = new JoystickButton(joystick_0, 1);
	Button collect = new JoystickButton(joystick_0, 2);
	Button aim = new JoystickButton(joystick_1, 2);

	Command shootAndCollect = new ShootandCollect();
	Command startCollector = new StartCollector();
	public Command cameraVisionProcessing = new CameraVisionProcessing();


	public OI() {
		trig.whenPressed(shootAndCollect);
		collect.toggleWhenActive(startCollector);
		aim.whileHeld(cameraVisionProcessing);
	}

	public Joystick getPrimaryJoystick() {
		return joystick_0;
	}

	public Joystick getSecondaryJoystick() {
		return joystick_1;
	}


}

