package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDs extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	Relay red, green, blue;
	
	public LEDs() {
		red = new Relay(RobotMap.redLed);
		green = new Relay(RobotMap.greenLed);
		blue = new Relay(RobotMap.blueLed);
		
		turnOff();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setRed() {
    	red.set(Relay.Value.kOff);
    	green.set(Relay.Value.kOn);
    	blue.set(Relay.Value.kOn);
    }
    
    public void setGreen() {
    	red.set(Relay.Value.kOn);
    	green.set(Relay.Value.kOff);
    	blue.set(Relay.Value.kOn);
    }
    
    public void setBlue() {
    	red.set(Relay.Value.kOn);
    	green.set(Relay.Value.kOn);
    	blue.set(Relay.Value.kOff);
    }
    
    public void turnOff() {
    	red.set(Relay.Value.kOn);
    	green.set(Relay.Value.kOn);
    	blue.set(Relay.Value.kOn);
    }
}

