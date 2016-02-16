package org.usfirst.frc.team991.robot.commands;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team991.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CameraVisionProcessing extends Command {

	NIVision.Rect rect;

	int imaqError;
	

	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(100, 120);	//Default hue range for yellow tote
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(150, 255);	//Default saturation range for yellow tote
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(100, 255);	//Default value range for yellow tote
	double AREA_MINIMUM = 1; //Default Area minimum for particle as a percentage of total image area
	double LONG_RATIO = 2.22; //Tote long side = 26.9 / Tote height = 12.1 = 2.22
	double SHORT_RATIO = 1.4; //Tote short side = 16.9 / Tote height = 12.1 = 1.4
	double SCORE_MIN = 75.0;  //Minimum score to be considered a tote
	double VIEW_ANGLE = 50.5; //View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	double SOLIDITY_MAX = 0.35; //Maximum solidity (area/convex_hull_area) to be considered target
	double SOLIDITY_MIN = 0.27; //Maximum solidity (area/convex_hull_area) to be considered target
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	
    public CameraVisionProcessing() {
        requires(Robot.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);
    	criteria[1] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_COMPACTNESS_FACTOR, SOLIDITY_MIN, SOLIDITY_MAX, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.camera.isCameraPluggedIn()){
			Robot.camera.camera.getImage(Robot.camera.frame); //Gets frame from camera

			//Applies a Robot.camera.binaryFrame threshold to the frame to separate particles
			NIVision.imaqColorThreshold(Robot.camera.binaryFrame, Robot.camera.frame, 255,
					NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE, TOTE_VAL_RANGE);

			int numParticles = NIVision.imaqCountParticles(Robot.camera.binaryFrame, 1); //Gets number of particles in frame
			
			//Filters out particles with small area
			imaqError = NIVision.imaqParticleFilter4(Robot.camera.binaryFrame, Robot.camera.binaryFrame, criteria, filterOptions, null);
			
			if(numParticles > 0)
			{
				//Measure particles and sort by particle size
				Vector<ParticleReport> particles = new Vector<ParticleReport>();
				for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
				{
					ParticleReport par = new ParticleReport();
					par.Solidity = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0,NIVision.MeasurementType.MT_AREA) /
									NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
					par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
					par.Area = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_AREA);
					par.BoundingRectTop = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
					par.BoundingRectLeft = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
					par.BoundingRectBottom = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
					par.BoundingRectRight = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
					
					particles.add(par);
				}
				particles.sort(null);
				
				//Collects/computes values of particle with largest area
				int top = (int)particles.elementAt(0).BoundingRectTop;
				int left = (int)particles.elementAt(0).BoundingRectLeft;
				int width = (int)particles.elementAt(0).BoundingRectRight - left;
				int height = (int)particles.elementAt(0).BoundingRectBottom - top;
				double center = (particles.elementAt(0).BoundingRectLeft + particles.elementAt(0).BoundingRectRight)/2;
				
				Robot.camera.RotatePower = -(140-center)/320 * 2; //Sets power of spinner motor
				
		    	rect = new NIVision.Rect(top, left, height, width); //Creates rectangle that encapsulates target
			}
			
			//Draws rectangle that encapsulates target on frame
			NIVision.imaqDrawShapeOnImage(Robot.camera.frame, Robot.camera.frame, rect,
	                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 255);
			
			
			//Pushes frame to driver station
			Robot.camera.server.setImage(Robot.camera.frame);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.camera.isCameraPluggedIn();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.camera.RotatePower = 0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		double PercentAreaToImageArea;
		double Area;
		double Solidity;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;
		
		public int compareTo(ParticleReport r)
		{
			return (int)(r.Area - this.Area);
		}
		
		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int)(r1.Area - r2.Area);
		}
	};
	
	double computeDistance (Image image, ParticleReport report) {
		double normalizedWidth, targetWidth;
		NIVision.GetImageSizeResult size;

		size = NIVision.imaqGetImageSize(image);
		normalizedWidth = 2*(report.BoundingRectRight - report.BoundingRectLeft)/size.width;
		targetWidth = 20.2;

		return  targetWidth/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
	}
	
	void testValues() {
		TOTE_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
		TOTE_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
		TOTE_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
		TOTE_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
		TOTE_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", TOTE_VAL_RANGE.minValue);
		TOTE_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
	}
}
    
