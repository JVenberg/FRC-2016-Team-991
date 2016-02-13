
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
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

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
	
	private final NetworkTable grip = NetworkTable.getTable("grip");
	
	CameraServer server;
	USBCamera camera;
	Image frame;
	boolean cameraPluggedIn;

	
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
		
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		
		cameraPluggedIn = false;
		try {
			camera = new USBCamera("cam0");
			camera.setBrightness(0);
			camera.setExposureManual(0);
			camera.updateSettings();
			camera.openCamera();
			camera.startCapture();
			
	    	
			server = CameraServer.getInstance();
	        server.setQuality(50);
	        
	        cameraPluggedIn = true;
	        
		} catch (Throwable e) {
			e.printStackTrace();
		}
        

    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		cameraPeriodic();
		
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
    	cameraPeriodic();
    	
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        
    	
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    	
    	
		
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        cameraPeriodic();
        
    	for (double area : grip.getNumberArray("targets/area", new double[0])) {
            System.out.println("Got contour with area=" + area);
        }
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    private void cameraPeriodic() {
    	if (cameraPluggedIn){
	    	NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
			camera.getImage(frame);
			NIVision.imaqDrawShapeOnImage(frame, frame, rect,
	                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0.0f);
			server.setImage(frame);
    	}
    }
}
