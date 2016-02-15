package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.commands.CameraFeed;

import com.ni.vision.NIVision;
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
	boolean cameraPluggedIn;
	public double RotatePower = 0;
	
	public Camera() {
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		
		try {
			camera = new USBCamera("cam1");
			camera.setBrightness(0);
			camera.setExposureManual(0);
			camera.updateSettings();
			camera.openCamera();
			camera.startCapture();
			
	    	
			server = CameraServer.getInstance();
	        server.setQuality(50);
	        
	        cameraPluggedIn = true;
	        
		} catch (Throwable e) {
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
	
	public boolean isCameraPluggedIn() {
		return cameraPluggedIn;
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new CameraFeed());
    }
}

