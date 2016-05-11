import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class main {

    public static void main(String[] args){
    	
    	move();
    }

    private final static int Kp = 1000;
	private final static int Ki = 100;
	private final static int Kd = 10000;
	private final static int offset = 45;
	private final static int Tp = 5;
	private static int integral = 0;
	private static int lastError = 45;
	private static int derivative = 0;
	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);

	
	public static void move(){
		System.out.println("start");
		Button.waitForAnyPress();
		while (true) {
			if (Button.ENTER.isDown()) {
				break;
			}
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
				System.out.println("powerA above 0");
			}
			else {
				powerA = powerA * (-1);
				Motor.A.setSpeed(powerA);
				Motor.A.backward();
				System.out.println("powerA below 0");
			}
			
			if (powerC > 0) {
				Motor.C.setSpeed(powerC);
				Motor.C.forward();
				System.out.println("powerC above 0");
			}
			else {
				powerC = powerC * (-1);
				Motor.C.setSpeed(powerC);
				Motor.C.backward();
				System.out.println("powerC below 0");
			}
			 
		
			lastError = error;
		}
		colorSensor.close();
		Motor.A.close();
		Motor.C.close();
	}

    
}

/*
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.TachoMotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.RegulatedMotor;
public class main {

    public static void main(String[] args){
    	EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
    	
    	RegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
    	RegulatedMotor mC = new EV3LargeRegulatedMotor(MotorPort.C);
    	mA.synchronizeWith(new RegulatedMotor[] {mC});
    	
    	
    	while(true){
    		System.out.println(colorSensor.getRedMode());
    		
    		mA.startSynchronization();
    		if(Button.ENTER.isDown()) {
    			break;
    		}
        	System.out.println(colorSensor.getColorID());
    		if(colorSensor.getColorID() != 7){
            	System.out.println("not black");
            	mA.forward();
            	mC.forward();
    		} else {
    			mA.stop();
    			mC.stop();
        		

    		}
    		mA.endSynchronization();
    		if(colorSensor.getColorID() == 7){
	        	System.out.println("black");
	    		mA.rotate(15, colorSensor.getColorID() != 7);
	    		if(colorSensor.getColorID() == 7)
	    			mC.rotate(30, colorSensor.getColorID() != 7);
    		}
    	}
    	colorSensor.close();
    	mA.close();
    	mC.close();
    	
    }
}
*/
