
package org.usfirst.frc.team991.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team991.robot.commands.groups.AutoDefenceBreach;
import org.usfirst.frc.team991.robot.commands.groups.AutoDefenceBreach.AutoDefenceLocation;
import org.usfirst.frc.team991.robot.commands.groups.AutoDefenceBreach.AutoDefenceType;
import org.usfirst.frc.team991.robot.subsystems.Camera;
import org.usfirst.frc.team991.robot.subsystems.Drivetrain;
import org.usfirst.frc.team991.robot.subsystems.Turret;
import org.usfirst.frc.team991.robot.subsystems.Flywheels;
import org.usfirst.frc.team991.robot.subsystems.LEDs;
import org.usfirst.frc.team991.robot.subsystems.Pneumatics;
import org.usfirst.frc.team991.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public enum AutoType {
		BREACH_DEFENCE, SPYBOT, NONE;
	}

	public static Drivetrain drivetrain;
	public static Flywheels flywheels;
	public static Collector collector;
	public static Turret turret;
	public static Camera camera;
	public static LEDs leds;
	public static OI oi;
	public static Pneumatics pneumatics;

	Command autonomousCommand;
	
	
	public static SendableChooser locationChooser;
	public static SendableChooser typeChooser;


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		flywheels = new Flywheels();
		drivetrain = new Drivetrain();
		collector = new Collector();
		turret = new Turret();
		camera = new Camera();
		leds = new LEDs();
		pneumatics = new Pneumatics();

		oi = new OI();
		
		
		locationChooser = new SendableChooser();
		locationChooser.addDefault("No Auto", AutoDefenceLocation.NULL);
		locationChooser.addObject("Location 1", AutoDefenceLocation.LOCATION_1);
		locationChooser.addObject("Location 2", AutoDefenceLocation.LOCATION_2);
		locationChooser.addObject("Location 3", AutoDefenceLocation.LOCATION_3);
		locationChooser.addObject("Location 4", AutoDefenceLocation.LOCATION_4);
		SmartDashboard.putData("Defence Location", locationChooser);
		
		typeChooser = new SendableChooser();
		typeChooser.addDefault("Moat", AutoDefenceType.MOAT);
		typeChooser.addObject("Rough Terrain", AutoDefenceType.ROUGH_TERRAIN);
		typeChooser.addObject("Ramparts", AutoDefenceType.RAMPARTS);
		SmartDashboard.putData("Defence Type", typeChooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit(){

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
	public void autonomousInit() {
		autonomousCommand = new AutoDefenceBreach();
		if (autonomousCommand != null) autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		SmartDashboard.putBoolean("Collector On?", collector.getCurrentCommand() != null);
		if (autonomousCommand != null) autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		SmartDashboard.putData(collector);
		SmartDashboard.putNumber("Gyro", Robot.drivetrain.getGyroAngle());
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
