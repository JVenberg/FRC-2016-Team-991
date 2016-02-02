package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.FlywheelDSControl;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
	private SpeedController front_motor, back_motor, spinner;
	
	public Shooter() {
		front_motor = new Victor(RobotMap.fly_front_motor);
		back_motor = new Victor(RobotMap.fly_back_motor);
		spinner = new Victor(RobotMap.spinner);
	}

    public void initDefaultCommand() {
        setDefaultCommand(new FlywheelDSControl());
    }
    
    public void stop() {
    	front_motor.set(0);
    	back_motor.set(0);
    }
    public void setSpeed(double front, double back) {
    	front_motor.set(front);
    	back_motor.set(back);
    }
    public void setSpin(double spin) {
    	spinner.set(spin);
    }
}

