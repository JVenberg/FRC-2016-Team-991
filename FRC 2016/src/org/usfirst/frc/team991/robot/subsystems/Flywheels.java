package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Flywheels extends Subsystem {
	//Initializes motor controllers
	CANTalon front_motor, back_motor;

	//Initializes constants for speed variation by distance
	double MAX_SPEED_RANGE = .1;
	double MAX_DISTANCE_RANGE = 4;
	double DEFAULT_SPEED = .55;
	double DEFAULT_DISTANCE = 9;

	public Flywheels() {
		//Initializes Talons
		front_motor = new CANTalon(RobotMap.fly_front_motor);
		back_motor = new CANTalon(RobotMap.fly_back_motor);
		front_motor.changeControlMode(TalonControlMode.Voltage);
		back_motor.changeControlMode(TalonControlMode.Voltage);
		front_motor.setVoltageCompensationRampRate(24.0);
		back_motor.setVoltageCompensationRampRate(24.0);
	}

	public void initDefaultCommand() {}

	//Stops flywheels
	public void stop() {
		front_motor.set(0);
		back_motor.set(0);
	}

	//Sets speed of flywheels
	public void setSpeed(double front, double back) {
		front_motor.set(front*12.5);
		back_motor.set(back*12.5);
	}

}

