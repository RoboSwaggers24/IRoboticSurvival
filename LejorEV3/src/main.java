import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
public class main {

    public static void main(String[] args){
    	EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
    	Motor.A.setSpeed(720);
    	Motor.C.setSpeed(720);
    	
    	while(true){
        	System.out.println("In loop");
    		if(colorSensor.getColorID() == 6){
            	System.out.println("I");
            	Motor.A.synchronizeWith(new NXTRegulatedMotor[] {Motor.C});
            	Motor.A.startSynchronization();
            	Motor.A.forward();
            	Motor.C.forward();
            	Motor.A.stop(colorSensor.getColorID() != 6);
            	Motor.C.stop(colorSensor.getColorID() != 6);
            	Motor.A.endSynchronization();
    			
    		}
    		Motor.A.rotate(5, colorSensor.getColorID() == 6);
    		if(colorSensor.getColorID() != 6)
    			Motor.C.rotate(10, colorSensor.getColorID() == 6);
    		
    		if(Button.ENTER.isDown()) {
    			break;
    		}
    	}
    	colorSensor.close();
    	
    }
}
