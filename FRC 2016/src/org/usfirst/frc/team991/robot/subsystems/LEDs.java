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
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	Relay red, green, blue;
	public SendableChooser ledChooser;
	private double lastSecond;
	private int color;
	
	public LEDs() {
		red = new Relay(RobotMap.redLed);
		green = new Relay(RobotMap.greenLed);
		blue = new Relay(RobotMap.blueLed);
		
		ledChooser = new SendableChooser();
		ledChooser.addDefault("Blink", -1);
        ledChooser.addObject("Red", 0);
        ledChooser.addObject("Blue", 1);
        ledChooser.addObject("Green", 2);
        SmartDashboard.putData("Led Colors", ledChooser);
		
	}

    public void initDefaultCommand() {
        setDefaultCommand(new LedControl());
    }
    
    public void setRed() {
    	color = 0;
    	red.set(Relay.Value.kOff);
    	green.set(Relay.Value.kOn);
    	blue.set(Relay.Value.kOn);
    }
    
    public void setGreen() {
    	color = 1;
    	red.set(Relay.Value.kOn);
    	green.set(Relay.Value.kOff);
    	blue.set(Relay.Value.kOn);
    }
    
    public void setBlue() {
    	color = 2;
    	red.set(Relay.Value.kOn);
    	green.set(Relay.Value.kOn);
    	blue.set(Relay.Value.kOff);
    }
    
    public void turnOff() {
    	color = -1;
    	red.set(Relay.Value.kOn);
    	green.set(Relay.Value.kOn);
    	blue.set(Relay.Value.kOn);
    }
    
    public void blink() {
    	double currentTime = (int) System.currentTimeMillis() * 0.001;
    	if (currentTime != lastSecond) {
    		switch(color) {
    		case(0):
    			setGreen();
    			break;
    		case(1):
    			setBlue();
    			break;
    		case(2):
    			setRed();
    			break;
    		default:
    			setRed();
    		}
    	}
    	lastSecond = currentTime;
    	
    }
}

