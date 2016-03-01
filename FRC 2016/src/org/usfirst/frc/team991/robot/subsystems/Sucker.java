package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Sucker extends Subsystem {
	
	//Initializes motor controller and limit switch
	CANTalon sucker;
	DigitalInput limitSwitch;

	public Sucker() {
		//Initializes motor controller and limit switch
		sucker = new CANTalon(RobotMap.sucker);
		limitSwitch = new DigitalInput(RobotMap.suckerLimitSwitch);
	}
	
	public void initDefaultCommand() {}

	//Sets collector speed
	public void setSucker(Double speed) {
		sucker.set(speed);
	}

	//Returns whether ball is in collector
	public boolean isPressed() {
		return !limitSwitch.get();
	}

	//Stops collector
	public void stop() {
		sucker.set(0);
	}
}

