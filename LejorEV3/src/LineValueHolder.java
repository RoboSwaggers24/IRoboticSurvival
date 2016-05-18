import lejos.hardware.sensor.EV3ColorSensor;

public class LineValueHolder {

	private float white;
	private float black;
	public LineValueHolder() {
	}

	public void setWhite(float value) {
		white = value;
		System.out.println(white);
	}

	public float getWhite() {
		return white;
	}

	public void setBlack(float value) {
		black = value;
		System.out.println(black);
	}

	public float getBlack() {
		return black;
	}

}