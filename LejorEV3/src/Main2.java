import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class Main2 implements ThreadCompleteListener {


	protected final static double PILOT_SPEED = 50.0;
    protected final static int PILOT_ACCELERATION = 25;
    protected final static float MAX_DISTANCE = 55;
    protected final static int INTERVAL = 500;
    protected final static double WHEEL_DIAMETER = 30.0f;
    protected final static double DISTANCE_BETWEEN_WHEELS = 170.0;
	
	LineFollower lineFollower;
	ObjectListener objectListener;
	
	public Main2() throws InterruptedException {
		waitForUser("white");
		DataExchange.getInstance().lvh.setWhite(getThreshold());
		waitForUser(null);
		waitForUser("black");
		DataExchange.getInstance().lvh.setBlack(getThreshold());
		waitForUser(null);

	}

	private synchronized void waitForUser(String message)
			throws InterruptedException {
		if (message != null) {
			LCD.clear();
			LCD.drawString(message, 0, 2, false);
		}
		//Sound.twoBeeps();
		Button.ENTER.waitForPressAndRelease();
	}

	private float getThreshold() {
		
		float value = Utilities.getInstance().getAvgLightValue();
		LCD.drawInt((int) value, 4, 0, 3);
		return value;
	}

	private void initialize() {
		// Creating the singletons
		Utilities.getInstance();
		DataExchange.getInstance();
		NotifyingThread lineFollower = new LineFollower();
		NotifyingThread objectListener = new ObjectListener();
		NotifyingThread fwl = new FindWhiteLine();
		NotifyingThread gridTask = new GridTask();
		fwl.addListener(this);
		lineFollower.addListener(this);
		objectListener.addListener(this);
		gridTask.addListener(this);
		lineFollower.start();	
		fwl.start();
		objectListener.start();
		gridTask.start();
		
	}
	public static void main(String[] args) throws InterruptedException {
		Main2 main = new Main2();
		main.initialize();
	}
	
	@Override
	public void notifyOfThreadComplete(NotifyingThread notifyingThread) {
		// TODO Auto-generated method stub
		LCD.drawString(notifyingThread.toString() + " completed", 0, 3);
		
	}
}