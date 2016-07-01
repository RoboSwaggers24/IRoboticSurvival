import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.RegulatedMotor;

public class DataExchange {
	
	LineValueHolder lvh;
	public boolean followLine = true;
	public boolean foundRedPillar = false;
	public boolean foundBluePillar = false;
    public boolean checkingWhite = false;
	public boolean carryFood = false;
	public boolean homePillar = false;
	public boolean maze = false;
	public boolean grid = false;
	// SINGLETION
    private static DataExchange thisInstance = null;
    
    protected DataExchange(LineValueHolder lvh2) {
    	// Exist only to defeat more then one instance
    	this.lvh = lvh2;
    }

	public static DataExchange getInstance() {
		if (thisInstance == null) {
			thisInstance = new DataExchange(new LineValueHolder());
		}
		return thisInstance;
	}
	///////////////////////////////////////
}
