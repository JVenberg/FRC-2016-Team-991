package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.Rotate;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Turret extends Subsystem {
	
	//Initializes motor controller for shooter turret
	CANTalon spinner;
	
	double visionProcessingRotate = 0;
	
	public Turret() {
		//Initializes Talon for turret
		spinner = new CANTalon(RobotMap.spinner);
	}

	//Sets default command for subsystem
	public void initDefaultCommand() {
		setDefaultCommand(new Rotate());
	}

	//Sets rotation speed of turret
	public void setSpin(double spin) {
		spinner.set(spin);
	}

	//Stops turret
	public void stop() {
		spinner.set(0);
	}
	
	public void setVisionRotatePower(double power) {
		visionProcessingRotate = power;
	}
	
	public double getVisionRotatePower() {
		return visionProcessingRotate;
	}
}

