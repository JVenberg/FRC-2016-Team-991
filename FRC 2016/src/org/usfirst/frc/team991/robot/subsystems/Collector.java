package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Collector extends Subsystem {
	
	//Initializes motor controller and limit switch
	CANTalon sucker;
	DigitalInput limitSwitch;
	DoubleSolenoid collectorArm;
	Talon collectorArmMotor;

	public Collector() {
		//Initializes motor controller and limit switch
		sucker = new CANTalon(RobotMap.sucker);
		limitSwitch = new DigitalInput(RobotMap.suckerLimitSwitch);
		collectorArm = new DoubleSolenoid(RobotMap.collectorArmFwd, RobotMap.collectorArmRev);
		collectorArmMotor = new Talon(RobotMap.collectorArmMotor);
	}
	
	public void initDefaultCommand() {}

	//Sets collector speed
	public void setSucker(Double speed) {
		sucker.set(speed);
	}

	//Returns whether ball is in collector
	public boolean isPressed() {
		return limitSwitch.get();
	}

	//Stops collector
	public void stop() {
		sucker.set(0);
	}
	
	public void armDown() {
		collectorArm.set(Value.kForward);
		collectorArmMotor.set(1);
	}
	
	public void armUp() {
		collectorArm.set(Value.kReverse);
		collectorArmMotor.set(0);
	}
}

