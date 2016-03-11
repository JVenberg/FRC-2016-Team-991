package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.LedControl;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */



public class LEDs extends Subsystem {
	
	//Initializes led states enum
	public static enum LedColor {
		RED, GREEN, BLUE, BLINK, OFF;
	}
	
	//Initializes relays and Smartdashboard chooser
	Relay red, green, blue;
	public SendableChooser ledChooser;
	private LedColor color;

	public LEDs() {
		//Initializes relays
		red = new Relay(RobotMap.redLed);
		green = new Relay(RobotMap.greenLed);
		blue = new Relay(RobotMap.blueLed);
		color = LedColor.RED;

		//Initializes SendableChooser for Smartdashboard
		ledChooser = new SendableChooser();
		ledChooser.addDefault("Red", LedColor.RED);
		ledChooser.addObject("Blue", LedColor.BLUE);
		ledChooser.addObject("Green", LedColor.GREEN);
		SmartDashboard.putData("Led Colors", ledChooser);

	}

	//Sets default command for subsystem
	public void initDefaultCommand() {
		setDefaultCommand(new LedControl());
	}

	//Sets led color to red
	public void setRed() {
		color = LedColor.RED;
		red.set(Relay.Value.kOff);
		green.set(Relay.Value.kOn);
		blue.set(Relay.Value.kOn);
	}

	//Sets led color to green
	public void setGreen() {
		color = LedColor.GREEN;
		red.set(Relay.Value.kOn);
		green.set(Relay.Value.kOff);
		blue.set(Relay.Value.kOn);
	}

	//Sets led color to blue
	public void setBlue() {
		color = LedColor.BLUE;
		red.set(Relay.Value.kOn);
		green.set(Relay.Value.kOn);
		blue.set(Relay.Value.kOff);
	}

	//Turns leds off
	public void turnOff() {
		color = LedColor.OFF;
		red.set(Relay.Value.kOn);
		green.set(Relay.Value.kOn);
		blue.set(Relay.Value.kOn);
	}
	
	public SendableChooser getLedChooser() {
		return ledChooser;
	}
}

