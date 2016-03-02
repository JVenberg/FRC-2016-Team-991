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
	
	//Initialize camera server and buffer frame
	private CameraServer server;
	private Image frame;

	//Initialize rotation and distance values
	private double RotatePower = 0;
	private double distance;

	String[] cameraNames = {"cam0", "cam1"}; //Initializes list of camera
	USBCamera[] cameras = new USBCamera[cameraNames.length]; //Initializes array of USBCamera objects
	private int camIndex = 0; //Sets starting camera index




	public Camera() {

		//Fills array with USBCamera objects from cameraNames
		for(int i = getCamIndex(); i < cameraNames.length; i++) {
			cameras[i] = new USBCamera(cameraNames[i]);
		}

		// Initializes buffer frame for vision processing
		setFrame(NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0));

		cameras[getCamIndex()].setBrightness(0); //Sets brightness to 0
		cameras[getCamIndex()].setExposureManual(0); //Sets exposure to 0
		cameras[getCamIndex()].updateSettings(); // Updates the camera settings
		cameras[getCamIndex()].openCamera(); // Opens camera
		cameras[getCamIndex()].startCapture(); // Starts capture

		// Initializes camera server
		setServer(CameraServer.getInstance());
		getServer().setQuality(50);
	}

	public void changeCamera(int index){

		//Stops capture of current camera and closes it
		cameras[getCamIndex()].stopCapture();
		cameras[getCamIndex()].closeCamera();

		
		this.setCamIndex(index); //Sets passed index to camera index
		
		//Makes sure camera index in within range
		if(getCamIndex() >= cameraNames.length){
			setCamIndex(0);
		}

		//Sets lowered brightness/exposure for vision processing camera
		if(getCamIndex() == 0) {
			cameras[getCamIndex()].setBrightness(0);
			cameras[getCamIndex()].setExposureManual(0);
			cameras[getCamIndex()].updateSettings();
		}

		//Opens up new camera and starts capture
		cameras[getCamIndex()].openCamera();
		cameras[getCamIndex()].startCapture();
	}

	//Cycles between the two cameras
	public void cycleCamera(){
		changeCamera(getCamIndex()+1);
	}	

	//Returns the current camera
	public USBCamera getCamera() {
		return cameras[getCamIndex()];
	}

	//Returns image buffer frame
	public Image getFrame() {
		return frame;
	}

	//Sets the default command of subsystem
	public void initDefaultCommand() {
		setDefaultCommand(new CameraVisionProcessing(CamMode.PASSIVE));
	}

	public int getCamIndex() {
		return camIndex;
	}

	public void setCamIndex(int camIndex) {
		this.camIndex = camIndex;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getRotatePower() {
		return RotatePower;
	}

	public void setRotatePower(double rotatePower) {
		RotatePower = rotatePower;
	}

	public void setFrame(Image frame) {
		this.frame = frame;
	}

	public CameraServer getServer() {
		return server;
	}

	public void setServer(CameraServer server) {
		this.server = server;
	}
}
