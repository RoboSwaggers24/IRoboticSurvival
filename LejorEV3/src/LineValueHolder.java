import lejos.hardware.sensor.EV3ColorSensor;

public class LineValueHolder {

	private float white;
	private float black;
	public LineValueHolder() {
	}

	public void setWhite(float value) {
		white = value;
	}

	public float getWhite() {
		return white;
	}

	public void setBlack(float value) {
		black = value;
	}

	public float getBlack() {
		return black;
	}

}