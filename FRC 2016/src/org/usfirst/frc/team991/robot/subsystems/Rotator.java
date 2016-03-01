package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.Rotate;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Rotator extends Subsystem {
	
	//Initializes motor controller for shooter rotator
	CANTalon spinner;
	
	public Rotator() {
		//Initializes Talon for rotator
		spinner = new CANTalon(RobotMap.spinner);
	}

	//Sets default command for subsystem
	public void initDefaultCommand() {
		setDefaultCommand(new Rotate());
	}

	//Sets rotation speed of rotator
	public void setSpin(double spin) {
		spinner.set(spin);
	}

	//Stops rotator
	public void stop() {
		spinner.set(0);
	}
}

