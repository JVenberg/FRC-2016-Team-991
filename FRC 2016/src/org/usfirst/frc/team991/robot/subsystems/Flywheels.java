package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Flywheels extends Subsystem {
	private CANTalon front_motor, back_motor;

	double MAX_SPEED_RANGE = .1;
	double MAX_DISTANCE_RANGE = 4;
	double DEFAULT_SPEED = .55;
	double DEFAULT_DISTANCE = 9;
	public double slope, intercept;

	public Flywheels() {
		front_motor = new CANTalon(RobotMap.fly_front_motor);
		back_motor = new CANTalon(RobotMap.fly_back_motor);

		front_motor.changeControlMode(TalonControlMode.Voltage);
		back_motor.changeControlMode(TalonControlMode.Voltage);
		front_motor.setVoltageCompensationRampRate(24.0);
		back_motor.setVoltageCompensationRampRate(24.0);





		slope = computeSlope(MAX_SPEED_RANGE, MAX_DISTANCE_RANGE);
		intercept = computeIntercept(DEFAULT_SPEED, slope, DEFAULT_DISTANCE);
	}

	public void initDefaultCommand() {
	}

	public void stop() {
		front_motor.set(0);
		back_motor.set(0);
	}

	public void setSpeed(double front, double back) {
		front_motor.set(front * 12);
		back_motor.set(back * 12);
	}

	double computeSlope(double max_speed_range, double max_distance_range) {
		return max_speed_range/max_distance_range;
	}

	double  computeIntercept(double default_speed, double slope, double default_distance) {
		return default_speed - slope * default_distance;
	}

	double computeFlywheelSpeed(double slope, double intercept, double distance) {
		return slope * distance + intercept;
	}

}

