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
public class CameraFeed extends Command {

	NIVision.Rect rect;
	Image binaryFrame;
	

	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(100, 120);	//Default hue range for yellow tote
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(150, 255);	//Default saturation range for yellow tote
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(100, 255);	//Default value range for yellow tote
	double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
	double LONG_RATIO = 2.22; //Tote long side = 26.9 / Tote height = 12.1 = 2.22
	double SHORT_RATIO = 1.4; //Tote short side = 16.9 / Tote height = 12.1 = 1.4
	double SCORE_MIN = 75.0;  //Minimum score to be considered a tote
	double VIEW_ANGLE = 50.5; //View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	
    public CameraFeed() {
        requires(Robot.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
    	criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);
    	
//    	SmartDashboard.putNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
//		SmartDashboard.putNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
//		SmartDashboard.putNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
//		SmartDashboard.putNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
//		SmartDashboard.putNumber("Tote val min", TOTE_VAL_RANGE.minValue);
//		SmartDashboard.putNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
//		SmartDashboard.putNumber("Area min %", AREA_MINIMUM);
//		SmartDashboard.putNumber("Connectivity", 50);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.camera.isCameraPluggedIn()){
			Robot.camera.camera.getImage(Robot.camera.frame);
			
//			TOTE_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
//			TOTE_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
//			TOTE_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
//			TOTE_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
//			TOTE_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", TOTE_VAL_RANGE.minValue);
//			TOTE_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
//			int connectivity = (int)SmartDashboard.getNumber("Connectivity", 50);

			NIVision.imaqColorThreshold(binaryFrame, Robot.camera.frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE, TOTE_VAL_RANGE);

//			NIVision.imaqConvexHull(binaryFrame, binaryFrame, connectivity);
			int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			
			SmartDashboard.putNumber("Masked particles", numParticles);
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
					par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
					par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
					par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
					par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
					par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
					particles.add(par);
				}
				particles.sort(null);
				int top = (int)particles.elementAt(0).BoundingRectTop;
				int left = (int)particles.elementAt(0).BoundingRectLeft;
				int width = (int)particles.elementAt(0).BoundingRectRight - left;
				int height = (int)particles.elementAt(0).BoundingRectBottom - top;
				double center = (particles.elementAt(0).BoundingRectLeft + particles.elementAt(0).BoundingRectRight)/2;
				
				Robot.camera.RotatePower = -(140-center)/320 * 2;
				
//				SmartDashboard.putNumber("top", top);
//				SmartDashboard.putNumber("left", left);
//				SmartDashboard.putNumber("width", width);
//				SmartDashboard.putNumber("height", height);
//				SmartDashboard.putNumber("Power", Robot.camera.RotatePower);

		    	rect = new NIVision.Rect(top, left, height, width);
				

			}
			
//			NIVision.GetImageSizeResult size;
//			size = NIVision.imaqGetImageSize(binaryFrame);
//			SmartDashboard.putNumber("Image width", size.width);
			
			NIVision.imaqDrawShapeOnImage(Robot.camera.frame, Robot.camera.frame, rect,
	                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 255);
			
			
			
			Robot.camera.server.setImage(Robot.camera.frame);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
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
}
    
