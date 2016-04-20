package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.ArcadeDriveJoystick;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drivetrain extends Subsystem {

	//Initialize controllers and sensors
	CANTalon front_left_motor, back_left_motor, front_right_motor, back_right_motor;
	RobotDrive drive;
//	ADXRS450_Gyro gyro;
	AnalogGyro gyro;
	Encoder enc;
	
	double distanceFromTarget = 0;


	public Drivetrain() {
		//Drive motor initialization
		front_left_motor = new CANTalon(RobotMap.frontleftMotor);
		back_left_motor = new CANTalon(RobotMap.backleftMotor);
		front_right_motor = new CANTalon(RobotMap.frontrightMotor);
		back_right_motor = new CANTalon(RobotMap.backrightMotor);

		//Drive system
		drive = new RobotDrive(front_left_motor, back_left_motor, front_right_motor, back_right_motor);

		//Invert drive motors
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, false);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

		//Gyro initialization
//		gyro = new ADXRS450_Gyro();
		gyro = new AnalogGyro(1);
		gyro.calibrate();

		//Encoder initialization
//		enc = new Encoder(RobotMap.encoderA, RobotMap.encoderB, false, Encoder.EncodingType.k4X);
//		enc.setMaxPeriod(.1);
//		enc.setMinRate(10);
//		enc.setDistancePerPulse(5);
//		enc.setSamplesToAverage(7);
	}

	//Sets arcade drive
	public void arcadeDrive(double y, double rot) {
		drive.arcadeDrive(y, rot, false);
	}
	
	public void tankDrive(double y_1, double y_2) {
		drive.tankDrive(y_1, y_2);
	}

	//Stops drivetrain
	public void stop() {
		drive.arcadeDrive(0, 0, false);
	}

	//Resets gyro to zero
	public void resetGryo() {
		gyro.reset();
	}

	//Gets currect gyro angle
	public double getGyroAngle() {
		return gyro.getAngle();
	}

	public void setDistanceFromTarget(double distance) {
		distanceFromTarget = distance;
	}
	
	public double getDistanceFromTarget() {
		return distanceFromTarget;
	}
	//Sets the default command of subsystem
	public void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveJoystick());
	}
}

