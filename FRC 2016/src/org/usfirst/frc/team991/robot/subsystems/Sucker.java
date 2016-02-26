package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Sucker extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	SpeedController sucker;
	DigitalInput limitSwitch;

	public Sucker() {
		sucker = new CANTalon(RobotMap.sucker);
		limitSwitch = new DigitalInput(RobotMap.suckerLimitSwitch);
	}
	public void initDefaultCommand() {
		//setDefaultCommand(new MySpecialCommand());
	}

	public void setSucker(Double speed) {
		sucker.set(speed);
	}

	public boolean isPressed() {
		return !limitSwitch.get();
	}

	public void stop() {
		sucker.set(0);
	}
}

