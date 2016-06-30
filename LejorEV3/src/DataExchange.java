import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.RegulatedMotor;

public class DataExchange {
	
	LineValueHolder lvh;
	public boolean followLine = true;
	RegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
	RegulatedMotor mC = new EV3LargeRegulatedMotor(MotorPort.C);
	EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S4);

	public DataExchange(LineValueHolder lvh2) {
		this.lvh = lvh2;
	}
}
