import lejos.hardware.sensor.EV3ColorSensor;

public class LineValueHolder {

	private float white;
	private float black;
	private float red;
	private float blue;
	
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

	public void setBlue(float threshold) {
		blue = threshold;
		
	}

	public void setRed(float threshold) {
		red = threshold;
		
	}
	public float getRed() {
		return red;
	}
	public float getBlue() {
		return blue;
	}

}