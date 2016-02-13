
package org.usfirst.frc.team991.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.io.IOException;

import org.usfirst.frc.team991.robot.commands.ArcadeDriveJoystick;
import org.usfirst.frc.team991.robot.subsystems.Drivetrain;
import org.usfirst.frc.team991.robot.subsystems.Rotator;
import org.usfirst.frc.team991.robot.subsystems.Flywheels;
import org.usfirst.frc.team991.robot.subsystems.Sucker;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drivetrain drivetrain;
	public static Flywheels flywheels;
	public static Sucker sucker;
	public static Rotator flywheelrotator;
	public static OI oi;

    Command autonomousCommand;
    SendableChooser chooser;
	
	
	public static double frontWheel;
	public static double backWheel;
	
	public static double spin;
	
	public static NetworkTable table;
	public static double[] defaultValue;
	
	DigitalInput limSwitch;
	
	int session;
	
	private final NetworkTable grip = NetworkTable.getTable("grip");

	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	limSwitch = new DigitalInput(0);
    	
    	try {
            new ProcessBuilder("/home/lvuser/grip").inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	

		flywheels = new Flywheels();
		drivetrain = new Drivetrain();
		sucker = new Sucker();
		flywheelrotator = new Rotator();
		
		
    	defaultValue = new double[1];
    	defaultValue[0] = 200;
    	
        
		oi = new OI();
		
//        chooser = new SendableChooser();
//        chooser.addDefault("Default Auto", new ArcadeDriveJoystick());
//        chooser.addObject("My Auto", new ArcadeDriveJoystick());
//        SmartDashboard.putData("Auto mode", chooser);
		
        

    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
//		cameraPeriodic();
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
    	
//        autonomousCommand = (Command) chooser.getSelected();
        
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
//    	cameraPeriodic();
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    	
    	for (double area : grip.getNumberArray("targets/area", new double[0])) {
            System.out.println("Got contour with area=" + area);
        }
		
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	SmartDashboard.putBoolean("Switch", limSwitch.get());
    	table = NetworkTable.getTable("GRIP/myContoursReport");
    	double[] center = table.getNumberArray("area", defaultValue);
    	spin = (220-center[0])/2200;
    	SmartDashboard.putNumber("Spin", center[0]);
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
//    void cameraPeriodic() {
//    	targetCam.getImage(frame);  // retrieve a frame from the USBCamera class
//    	CameraServer.getInstance().setImage(frame);  // push that frame to the SmartDashboard using the CamServer class
//    }
}
