package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.ArcadeDriveJoystick;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drivetrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	SpeedController front_left_motor, back_left_motor, front_right_motor, back_right_motor;
	RobotDrive drive;
	AnalogGyro gyro;

	public Drivetrain() {
		front_left_motor = new CANTalon(RobotMap.frontleftMotor);
		back_left_motor = new CANTalon(RobotMap.backleftMotor);
		front_right_motor = new CANTalon(RobotMap.frontrightMotor);
		back_right_motor = new CANTalon(RobotMap.backrightMotor);

		drive = new RobotDrive(front_left_motor, back_left_motor, front_right_motor, back_right_motor);

		drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, false);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
		gyro = new AnalogGyro(RobotMap.gyro);
		gyro.initGyro();
		gyro.setSensitivity(0.0019);
		gyro.calibrate();
	}

	public void arcadeDrive(double y, double rot) {
		drive.arcadeDrive(y, rot, false);
	}

	public void stop() {
		drive.arcadeDrive(0, 0, false);
	}
	
	public void resetGryo() {
		gyro.reset();
	}
	
	public double getGyroAngle() {
		return gyro.getAngle();
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new ArcadeDriveJoystick());
	}
}

