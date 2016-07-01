import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.utility.Delay;
import lejos.hardware.sensor.*;
 
public class ObjectListener extends NotifyingThread  {

    private final float distanceToObject = 0.3f;
    public ObjectListener() {
    }
    
    @Override
    public void doRun() {
	   
	   while(true) {
		   if(!DataExchange.getInstance().checkingWhite && !DataExchange.getInstance().carryFood){
			   controlling();
		   }
		   else if (DataExchange.getInstance().carryFood) {
			   
			   Utilities u = Utilities.getInstance();
			   DataExchange de = DataExchange.getInstance();
			   if (u.getDistanceValue() < 0.3 && de.followLine) { 
				   
				   while(!u.claw.isStalled())
						u.claw.rotate(60);
					de.carryFood = false;
			   }
		   }
	   }
   }
    
    public void controlling() {
		DataExchange de = DataExchange.getInstance();
		Utilities u = Utilities.getInstance();
		   
    	if (u.getDistanceValue() < distanceToObject) {
    			
			   LCD.clear();
			   LCD.drawString("control dis " + u.getDistanceValue(), 0, 2, false);
			   de.followLine = false;
			   u.mB.setSpeed(100);
			   u.mC.setSpeed(100);
			   u.mB.forward();
			   u.mC.forward();
			   if (u.getDistanceValue() < 0.04) {
				   u.mB.stop();
				   u.mC.stop();
				   int pilarColor = u.getColorValue();
				   if (pilarColor == 7) {
					   de.foundBluePillar = true;
				   }
				   
				   if (pilarColor == 13) {
					   de.foundRedPillar = true;
				   }
				   else{
						while(!u.claw.isStalled())
							u.claw.rotate(-60);
						de.carryFood = true;
				   }
				   de.checkingWhite = true;

				   
			   }
		}
    	else {
			   DataExchange.getInstance().followLine = true;
		   }
    }
}