package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing.CamMode;

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
	public Image frame;

	public double RotatePower = 0;
	public double distance;

	String[] cameraNames = {"cam0", "cam1"};
	USBCamera[] cameras = new USBCamera[cameraNames.length];
	public int camIndex = 0;




	public Camera() {

		for(int i = camIndex; i < cameraNames.length; i++) {
			cameras[i] = new USBCamera(cameraNames[i]);
		}

		// Initializes frames for vision processing
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

		cameras[camIndex].setBrightness(0);
		cameras[camIndex].setExposureManual(0);
		cameras[camIndex].updateSettings(); // Updates the camera settings
		cameras[camIndex].openCamera(); // Opens camera
		cameras[camIndex].startCapture(); // Starts capture

		// Initializes camera server
		server = CameraServer.getInstance();
		server.setQuality(50);
	}

	public void changeCamera(int index){

		cameras[camIndex].stopCapture();
		cameras[camIndex].closeCamera();

		this.camIndex = index;
		if(camIndex >= cameraNames.length){
			camIndex = 0;
		}

		if(camIndex == 0) {
			cameras[camIndex].setBrightness(0);
			cameras[camIndex].setExposureManual(0);
			cameras[camIndex].updateSettings();
		}

		cameras[camIndex].openCamera();
		cameras[camIndex].startCapture();
	}

	public void cycleCamera(){
		changeCamera(camIndex+1);
	}	

	public USBCamera getCamera() {
		return cameras[camIndex];
	}

	public Image getFrame() {
		return frame;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new CameraVisionProcessing(CamMode.PASSIVE));
	}
}
