import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;


public class Cruiser extends Thread {
	/*
	LFJfedor lfjf;
	//RegulatedMotor mD = new EV3LargeRegulatedMotor(MotorPort.D);
	//EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
    protected final static double NINETY_DEGREES = 90.0;
    protected final static double PILOT_SPEED = 50.0;
    protected final static int PILOT_ACCELERATION = 25;
    protected final static float MAX_DISTANCE = 55;
    protected final static int INTERVAL = 500;
    protected final static double WHEEL_DIAMETER = 30.0f;
    protected final static double DISTANCE_BETWEEN_WHEELS = 170.0;
	RegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
	RegulatedMotor mC = new EV3LargeRegulatedMotor(MotorPort.C);
	
	
	 
	final EV3IRSensor infraredSensor = new EV3IRSensor(SensorPort.S2);
	public Cruiser(LFJfedor lfjf) {
		this.lfjf = lfjf;
		
		final DifferentialPilot pilot = new DifferentialPilot(WHEEL_DIAMETER, DISTANCE_BETWEEN_WHEELS, mB, mC);
	    
		configurePilot(pilot);
		configureInfraredSensor(infraredSensor, pilot);
	
	}

	public void run() {
		LCD.clear();
		LCD.drawString("Started Cruiser", 0, 2);
		controller.move();
		//controller.pilar();
		controller.close();
		infraredSensor.close();

	}
	
	private static void configureInfraredSensor(final EV3IRSensor infraredSensor, DifferentialPilot pilot) {
        final RangeFinderAdapter rangeFinderAdaptor = new RangeFinderAdapter(infraredSensor.getDistanceMode());
        final RangeFeatureDetector rangeFeatureDetector = new RangeFeatureDetector(rangeFinderAdaptor, MAX_DISTANCE, INTERVAL);
        final FeatureListener detectedObjectListener = new DetectedObjectListener(pilot);
        rangeFeatureDetector.addListener(detectedObjectListener);
    }
    private static void configurePilot(final DifferentialPilot pilot) {
        pilot.setAcceleration(PILOT_ACCELERATION);
        pilot.setRotateSpeed(PILOT_SPEED);
        pilot.setTravelSpeed(PILOT_SPEED);
    }
*/
}