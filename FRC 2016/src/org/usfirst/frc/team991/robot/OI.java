package org.usfirst.frc.team991.robot;

import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing;
import org.usfirst.frc.team991.robot.commands.Collect;
import org.usfirst.frc.team991.robot.commands.MoveToDistance;
import org.usfirst.frc.team991.robot.commands.SwapCamera;
import org.usfirst.frc.team991.robot.commands.groups.ShootGroup;

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

	Button trig_button = new JoystickButton(getSecondaryJoystick(), 11);
	Button collect_button = new JoystickButton(getPrimaryJoystick(), 2);
	Button aim_button = new JoystickButton(getSecondaryJoystick(), 12);
	
	Button moveToDistance_button = new JoystickButton(getSecondaryJoystick(), 8);
	
	Button toggle_cam = new JoystickButton(getSecondaryJoystick(), 7);
	

	Command shootGroup = new ShootGroup();
	Command collect = new Collect();
	public Command cameraVisionProcessing = new CameraVisionProcessing(true);


	public OI() {
		trig_button.whenPressed(shootGroup);
		collect_button.toggleWhenActive(collect);
		aim_button.whileHeld(cameraVisionProcessing);
		
		moveToDistance_button.whenPressed(new MoveToDistance(9, 1));
		
		toggle_cam.whenPressed(new SwapCamera());
		
	}

	public Joystick getPrimaryJoystick() {
		return joystick_0;
	}

	public Joystick getSecondaryJoystick() {
		return joystick_1;
	}


}

