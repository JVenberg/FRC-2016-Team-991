package org.usfirst.frc.team991.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// CAN //

	// Subsystems
	public static int fly_front_motor = 7;
	public static int fly_back_motor = 5;
	public static int spinner = 4;
	public static int sucker = 0;

	// Drivetrain
	public static int frontleftMotor = 6;
	public static int backleftMotor = 2;
	public static int frontrightMotor = 3;
	public static int backrightMotor = 1;

	// SENSORS //

	//Analog Input
	public static int gyro = 1;

	//Digital Input
	public static int suckerLimitSwitch = 1;

	public static int encoderA = 2;
	public static int encoderB = 3;

	public static int redLed = 0;
	public static int greenLed = 1;
	public static int blueLed = 2;
}
