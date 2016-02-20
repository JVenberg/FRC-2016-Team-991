package org.usfirst.frc.team991.robot.commands;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team991.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CameraVisionProcessing extends Command {

	NIVision.Rect rect;
	Image binaryFrame;
	
	boolean isActive;
	

	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(95, 125);	//Default hue range for yellow tote
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(145, 255);	//Default saturation range for yellow tote
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(95, 255);	//Default value range for yellow tote
	double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
	double LONG_RATIO = 2.22; //Tote long side = 26.9 / Tote height = 12.1 = 2.22
	double SHORT_RATIO = 1.4; //Tote short side = 16.9 / Tote height = 12.1 = 1.4
	double SCORE_MIN = 75.0;  //Minimum score to be considered a tote
	double VIEW_ANGLE = 50.5; //View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	double COMPACTNESS_MIN = 30;
	double COMPACTNESS_MAX = 40;
	double AIM_CENTER = 170;
	
	double MAX_SPEED_RANGE = .1;
	double MAX_DISTANCE_RANGE = 4;
	double DEFAULT_SPEED = .55;
	double DEFAULT_DISTANCE = 9;
	public double slope, intercept;
	
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[2];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	
    public CameraVisionProcessing(boolean isActive) {
    	this.isActive = isActive;
        requires(Robot.camera);
        slope = computeSlope(MAX_SPEED_RANGE, MAX_DISTANCE_RANGE);
        intercept = computeIntercept(DEFAULT_SPEED, slope, DEFAULT_DISTANCE);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
    	criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);
    	criteria[1] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_COMPACTNESS_FACTOR, COMPACTNESS_MIN, COMPACTNESS_MAX, 0, 0);
    	
    	SmartDashboard.putNumber("Compactness Min", criteria[1].lower);
    	SmartDashboard.putNumber("Compactness Max", criteria[1].upper);
    	
    	Robot.camera.FlywheelCalculated = DEFAULT_SPEED;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	criteria[1].lower = (float)SmartDashboard.getNumber("Compactness Min", criteria[1].lower);
    	criteria[1].upper = (float)SmartDashboard.getNumber("Compactness Max", criteria[1].upper);
    	
    	if (Robot.camera.isCameraPluggedIn()){
			Robot.camera.camera.getImage(Robot.camera.frame);

			NIVision.imaqColorThreshold(binaryFrame, Robot.camera.frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE, TOTE_VAL_RANGE);

			int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			
			if(numParticles > 0)
			{
				//Measure particles and sort by particle size
				Vector<ParticleReport> particles = new Vector<ParticleReport>();
				for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
				{
					ParticleReport par = new ParticleReport();
					par.Solidity = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA) /
									NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
					par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
					par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
					par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
					par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
					par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
					par.Center = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
					particles.add(par);
				}
				particles.sort(null);
				int top = (int)particles.elementAt(0).BoundingRectTop;
				int left = (int)particles.elementAt(0).BoundingRectLeft;
				int width = (int)particles.elementAt(0).BoundingRectRight - left;
				int height = (int)particles.elementAt(0).BoundingRectBottom - top;
				double center = particles.elementAt(0).Center;
				
				if (isActive) {
					Robot.camera.RotatePower = -(AIM_CENTER-center)/320 * 2;
				} else {
					Robot.camera.RotatePower = 0;
				}
				
				double distance = computeDistance(Robot.camera.frame, particles.elementAt(0));
				SmartDashboard.putNumber("Distance", distance);
				Robot.camera.FlywheelCalculated = 0.55;
				
				
				
				
		    	rect = new NIVision.Rect(top, left, height, width);
				

		    	NIVision.imaqDrawShapeOnImage(Robot.camera.frame, Robot.camera.frame, rect,
		                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 255);
			} else {
				Robot.camera.RotatePower = 0;
			}
			
			Robot.camera.server.setImage(Robot.camera.frame);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.camera.FlywheelCalculated = DEFAULT_SPEED;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		double PercentAreaToImageArea;
		double Area;
		double Solidity;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;
		double Center;
		
		public int compareTo(ParticleReport r)
		{
			return (int)(r.Area - this.Area);
		}
		
		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int)(r1.Area - r2.Area);
		}
	};
	
	double computeDistance(Image image, ParticleReport report) {
		double normalizedWidth, targetWidth;
		NIVision.GetImageSizeResult size;

		size = NIVision.imaqGetImageSize(image);
		normalizedWidth = 2*(report.BoundingRectRight - report.BoundingRectLeft)/(double)size.width;
		targetWidth = 20.2;

		return  targetWidth/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
	}
	
	double computeSlope(double max_speed_range, double max_distance_range) {
		return max_speed_range/max_distance_range;
	}
	
	double  computeIntercept(double default_speed, double slope, double default_distance) {
		return default_speed - slope * default_distance;
	}
	
	double computeFlywheelSpeed(double slope, double intercept, double distance) {
		return slope * distance + intercept;
	}
	
	
}