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
	private double slope;
	private double intercept;

	public Flywheels() {
		//Initializes Talons
		front_motor = new CANTalon(RobotMap.fly_front_motor);
		back_motor = new CANTalon(RobotMap.fly_back_motor);

		//Sets mode to compensate speed based on voltage
//		front_motor.changeControlMode(TalonControlMode.Voltage);
//		back_motor.changeControlMode(TalonControlMode.Voltage);
//		front_motor.setVoltageCompensationRampRate(24.0);
//		back_motor.setVoltageCompensationRampRate(24.0);

		//Computes slope and intercept for shooting model
//		setSlope(computeSlope(MAX_SPEED_RANGE, MAX_DISTANCE_RANGE));
//		setIntercept(computeIntercept(DEFAULT_SPEED, getSlope(), DEFAULT_DISTANCE));
	}

	public void initDefaultCommand() {}

	//Stops flywheels
	public void stop() {
		front_motor.set(0);
		back_motor.set(0);
	}

	//Sets speed of flywheels
	public void setSpeed(double front, double back) {
		front_motor.set(front);
		back_motor.set(back);
	}

	//Computes slope of shooting model
	double computeSlope(double max_speed_range, double max_distance_range) {
		return max_speed_range/max_distance_range;
	}

	//Computes intercept of shooting model
	double  computeIntercept(double default_speed, double slope, double default_distance) {
		return default_speed - slope * default_distance;
	}

	//Computes speed from shooting model
	double computeFlywheelSpeed(double slope, double intercept, double distance) {
		return slope * distance + intercept;
	}

	public double getSlope() {
		return slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	public double getIntercept() {
		return intercept;
	}

	public void setIntercept(double intercept) {
		this.intercept = intercept;
	}

}

