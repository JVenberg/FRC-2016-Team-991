package org.usfirst.frc.team991.robot;

import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing;
import org.usfirst.frc.team991.robot.commands.Collect;
import org.usfirst.frc.team991.robot.commands.Collect.MotorMode;
import org.usfirst.frc.team991.robot.commands.MoveToDistance;
import org.usfirst.frc.team991.robot.commands.SwapCamera;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing.VisionProcessingMode;
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

	//Initialize joysticks
	private Joystick joystick_0 = new Joystick(0);
	private Joystick joystick_1 = new Joystick(1);

	//Initialization of buttons
	Button trig_button = new JoystickButton(getSecondaryJoystick(), 11);
	Button collect_button = new JoystickButton(getPrimaryJoystick(), 2);
	Button aim_button = new JoystickButton(getSecondaryJoystick(), 12);
	Button moveToDistance_button = new JoystickButton(getPrimaryJoystick(), 4);
	Button toggle_cam = new JoystickButton(getPrimaryJoystick(), 5);
//	Button red = new JoystickButton(getSecondaryJoystick(), 6);


	//Initialization of commands
	Command shootGroup = new ShootGroup();
	Command collect = new Collect(MotorMode.FORWARD);
	Command moveToDistance = new MoveToDistance(8.35, 2);
	Command cameraVisionProcessing = new CameraVisionProcessing(VisionProcessingMode.ACTIVE);


	public OI() {
		trig_button.toggleWhenActive(shootGroup); //Toggle shooting when pressed
		collect_button.toggleWhenActive(new Collect(MotorMode.FORWARD)); //Toggle collecting when pressed
		aim_button.whileHeld(cameraVisionProcessing); //Track and move shooter while held
		moveToDistance_button.toggleWhenActive(moveToDistance); //Move robot to firing position
		toggle_cam.whenPressed(new SwapCamera()); //Swap between camera when pressed
	}

	//Return the first joystick (Drive)
	public Joystick getPrimaryJoystick() {
		return joystick_0;
	}
	
	//Return the second joystick (Actuators)
	public Joystick getSecondaryJoystick() {
		return joystick_1;
	}


}

