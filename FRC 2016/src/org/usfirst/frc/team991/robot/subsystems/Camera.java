package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.commands.CameraDefault;

import com.ni.vision.NIVision;
import com.ni.vision.VisionException;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class Camera extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public CameraServer server;
	public USBCamera camera;
	public Image frame;
	public Image binaryFrame;
	public double RotatePower = 0;
	
	boolean cameraPluggedIn;
	
	
	public Camera() {
		// Initializes frames for vision processing
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
		
		//Tries to initialize camera
		try {
			camera = new USBCamera("cam1");
			camera.setBrightness(0); //Sets brightness to 0 so increase contrast between target and background
			camera.setExposureManual(0);//Sets exposure to 0 so increase contrast between target and background
			camera.updateSettings(); //Updates the camera settings
			camera.openCamera(); //Opens camera
			camera.startCapture(); //Starts capture
	    	
			//Initializes camera server
			server = CameraServer.getInstance();
	        server.setQuality(50);
	        
	        cameraPluggedIn = true; //Sets true of camera is plugged in
		} catch (VisionException e) {
			cameraPluggedIn = false;
			e.printStackTrace();
		}
	}
	
	public USBCamera getCamera() {
		return camera;
	}
	
	public Image getFrame() {
		return frame;
	}
	
	public Image getBinaryFrame() {
		return binaryFrame;
	}
	
	public boolean isCameraPluggedIn() {
		return cameraPluggedIn;
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new CameraDefault());
    }
}

