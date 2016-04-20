package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing.VisionProcessingMode;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class Camera extends Subsystem {
	
	//Initialize camera server and buffer frame
	private CameraServer server;
	public Image frame;


	String[] cameraNames = {"cam0", "cam1"}; //Initializes list of camera
	USBCamera[] cameras = new USBCamera[cameraNames.length]; //Initializes array of USBCamera objects
	private int camIndex = 0; //Sets starting camera index




	public Camera() {

		//Fills array with USBCamera objects from cameraNames
		for(int i = camIndex; i < cameraNames.length; i++) {
			cameras[i] = new USBCamera(cameraNames[i]);
		}

		// Initializes buffer frame for vision processing
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

		cameras[camIndex].setBrightness(0); //Sets brightness to 0
		cameras[camIndex].setExposureManual(0); //Sets exposure to 0
		cameras[camIndex].updateSettings(); // Updates the camera settings
		cameras[camIndex].startCapture(); // Starts capture

		// Initializes camera server
		server = CameraServer.getInstance();
		server.setQuality(50);
	}

	public void changeCamera(int index){

		//Stops capture of current camera and closes it
		cameras[camIndex].stopCapture();
		cameras[camIndex].closeCamera();

		
		camIndex = index; //Sets passed index to camera index
		
		//Makes sure camera index in within range
		if(camIndex >= cameraNames.length){
			camIndex = 0;
		}

		//Sets lowered brightness/exposure for vision processing camera
		if(camIndex == 0) {
			cameras[camIndex].setBrightness(0);
			cameras[camIndex].setExposureManual(0);
		} else {
			cameras[camIndex].setBrightness(10);
//			cameras[camIndex].setExposureManual(100);
		}

		//Opens up new camera and starts capture
		cameras[camIndex].openCamera();
		cameras[camIndex].startCapture();
	}

	//Cycles between the two cameras
	public void cycleCamera(){
		changeCamera(camIndex+1);
	}	

	//Returns the current camera
	public USBCamera getCamera() {
		return cameras[camIndex];
	}

	//Sets the default command of subsystem
	public void initDefaultCommand() {
		setDefaultCommand(new CameraVisionProcessing(VisionProcessingMode.PASSIVE));
	}

	public CameraServer getServer() {
		return server;
	}

	public void setServer(CameraServer server) {
		this.server = server;
	}
	
	public double getCamIndex() {
		return camIndex;
	}
}
