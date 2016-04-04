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
	public enum CamMode {
		ACTIVE, PASSIVE;
	}

	NIVision.Rect rect;
	Image binaryFrame;

	CamMode mode;

	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(95, 125);	
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(145, 255);	
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(95, 255);

	double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
	double VIEW_ANGLE = 50.5; //View angle for camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	double COMPACTNESS_MIN = .10;
	double COMPACTNESS_MAX = .50;
	double AIM_CENTER = 170;

	double distance;

	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[2];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);

	public CameraVisionProcessing(CamMode mode) {
		this.mode = mode;
		requires(Robot.camera);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);
		criteria[1] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_COMPACTNESS_FACTOR, COMPACTNESS_MIN, COMPACTNESS_MAX, 0, 0);

//		AIM_CENTER = Robot.pref.getDouble("Center Aim", 170);
//		publishValues();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.camera.getCamera().getImage(Robot.camera.frame);

//		getValues();

		NIVision.imaqColorThreshold(binaryFrame, Robot.camera.frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE, TOTE_VAL_RANGE);

		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);

		if(numParticles > 0) {
			//Measure particles and sort by particle size
			Vector<ParticleReport> particles = new Vector<ParticleReport>();
			for(int particleIndex = 0; particleIndex < numParticles; particleIndex++) {
				ParticleReport par = new ParticleReport();
				par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
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

			if (mode == CamMode.ACTIVE) {
				Robot.camera.setRotatePower(-(AIM_CENTER-center)/320 * 2);
			} else {
				Robot.camera.setRotatePower(0);
			}

			distance = computeDistance(Robot.camera.frame, particles.elementAt(0));
			Robot.camera.setDistance(distance);
			SmartDashboard.putNumber("Distance", distance);


			rect = new NIVision.Rect(top, left, height, width);
			NIVision.imaqDrawShapeOnImage(Robot.camera.frame, Robot.camera.frame, rect,
					DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 255);
		} else {
			Robot.camera.setRotatePower(0);
		}

		Robot.camera.getServer().setImage(Robot.camera.frame);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.camera.setRotatePower(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

	public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		double PercentAreaToImageArea;
		double Area;
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
		targetWidth = 20.1;

		return  targetWidth/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
	}
	
	void publishValues() {
		SmartDashboard.putNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
		SmartDashboard.putNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
		SmartDashboard.putNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
		SmartDashboard.putNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
		SmartDashboard.putNumber("Tote val min", TOTE_VAL_RANGE.minValue);
		SmartDashboard.putNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
		SmartDashboard.putNumber("Compactness Min", COMPACTNESS_MIN);
		SmartDashboard.putNumber("Compactness Max", COMPACTNESS_MAX);
		SmartDashboard.putNumber("Area min %", AREA_MINIMUM);
	}
	
	void getValues() {
		TOTE_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
		TOTE_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
		TOTE_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
		TOTE_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
		TOTE_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", TOTE_VAL_RANGE.minValue);
		TOTE_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
		COMPACTNESS_MIN = (double) SmartDashboard.getNumber("Compactness Min", COMPACTNESS_MIN);
		COMPACTNESS_MAX = (double) SmartDashboard.getNumber("Compactness Max", COMPACTNESS_MAX);
		AREA_MINIMUM = (double) SmartDashboard.getNumber("Area min %", AREA_MINIMUM);
	}

}