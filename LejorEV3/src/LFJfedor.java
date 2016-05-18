import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;

public class LFJfedor {

	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
	static LineValueHolder lvh = new LineValueHolder();

	public LFJfedor() throws InterruptedException {

		waitForUser("white");
		lvh.setWhite(getThreshold());
		waitForUser(null);

		waitForUser("black");
		lvh.setBlack(getThreshold());
		waitForUser(null);

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
		Thread cruiser = new Thread(new Cruiser(this));
		cruiser.start();
	}

	public static void main(String[] args) throws InterruptedException {
		LFJfedor lfJfedor = new LFJfedor();
		lfJfedor.initialize();
	}
}