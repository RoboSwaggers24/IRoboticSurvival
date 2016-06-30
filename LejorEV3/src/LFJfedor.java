import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class LFJfedor {

	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
	static LineValueHolder lvh = new LineValueHolder();

	protected final static double PILOT_SPEED = 50.0;
    protected final static int PILOT_ACCELERATION = 25;
    protected final static float MAX_DISTANCE = 55;
    protected final static int INTERVAL = 500;
    protected final static double WHEEL_DIAMETER = 30.0f;
    protected final static double DISTANCE_BETWEEN_WHEELS = 170.0;
	
	
	
	LineFollower lineFollower;
	static DataExchange DE;
	
	
	
	public LFJfedor() throws InterruptedException {

		waitForUser("white");
		lvh.setWhite(getThreshold());

		waitForUser("black");
		lvh.setBlack(getThreshold());
		
		waitForUser("blue");
		lvh.setBlue(getThreshold());
		
		waitForUser("red");
		lvh.setRed(getThreshold());
		colorSensor.close();
	}

	private synchronized void waitForUser(String message)
			throws InterruptedException {
		if (message != null) {
			LCD.drawString(message, 0, 2, false);
		}
		//Sound.twoBeeps();
		Button.ENTER.waitForPressAndRelease();
	}

	private float getThreshold() {
		LFUtils calib = new LFUtils(colorSensor);
		float value = calib.getAvgLightValue();
		LCD.drawInt((int) value, 4, 0, 3);
		return value;
	}

	private void initialize() {
		EV3IRSensor infraredSensor = new EV3IRSensor(SensorPort.S2);
		DE = new DataExchange(lvh);
		configureInfraredSensor(infraredSensor);
		lineFollower = new LineFollower(this,DE);
		lineFollower.start();
		
	}

	private static void configureInfraredSensor(final EV3IRSensor infraredSensor) {
        final RangeFinderAdapter rangeFinderAdaptor = new RangeFinderAdapter(infraredSensor.getDistanceMode());
        final RangeFeatureDetector rangeFeatureDetector = new RangeFeatureDetector(rangeFinderAdaptor, MAX_DISTANCE, INTERVAL);
        final FeatureListener detectedObjectListener = new DetectedObjectListener(DE);
        rangeFeatureDetector.addListener(detectedObjectListener);
    }

	public static void main(String[] args) throws InterruptedException {
		LFJfedor lfJfedor = new LFJfedor();
		lfJfedor.initialize();
	}
}