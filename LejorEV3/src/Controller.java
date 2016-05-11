import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class Controller {
	
	private final static int Kp = 1000;
	private final static int Ki = 100;
	private final static int Kd = 10000;
	private final static int offset = 45;
	private final static int Tp = 50;
	private static int integral = 0;
	private static int lastError = 45;
	private static int derivative = 0;
	EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);

	public Controller() {
		
	}
	
	public void move(){
		while (true) {
			SensorMode lightvalue = colorSensor.getRedMode();
			int error = lightvalue.sampleSize() - offset;
			integral = integral + error;
			derivative = error - lastError;
			float turn = Kp*error + Ki*integral + Kd*derivative;
			turn = turn /100;
			float powerA = Tp + turn;
			float powerC = Tp - turn;
			
			if (powerA > 0) {
				Motor.A.setSpeed(powerA);
				Motor.A.forward();
			}
			else {
				powerA = powerA * (-1);
				Motor.A.setSpeed(powerA);
				Motor.A.backward();
			}
			
			if (powerC > 0) {
				Motor.C.setSpeed(powerC);
				Motor.C.forward();
			}
			else {
				powerC = powerC * (-1);
				Motor.C.setSpeed(powerC);
				Motor.C.backward();
			}
			 
		
			lastError = error;
		}
	}
	
}
