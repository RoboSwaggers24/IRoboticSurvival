import lejos.hardware.Sound;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.utility.Delay;
 
public class DetectedObjectListener implements FeatureListener {

    public DataExchange DE;

    public DetectedObjectListener(DataExchange DE) {
    	this.DE = DE;
    }
 
    @Override
    public void featureDetected(final Feature feature, final FeatureDetector detector) {
        int range = (int)feature.getRangeReading().getRange();
        
    	System.out.println("detected object" + range);
        if(range >= 10){
        	DE.followLine = false;
        	DE.mC.forward();
        	DE.mB.forward();
        	float color = LFUtils.getAvgLightValue();
        	DE.mC.rotate(360 * range, DE.lvh.getBlue() == color || DE.lvh.getRed() == color);
        	
        	System.out.println("befor while");
        }
        while( DE.mB.isMoving()){
        	Delay.msDelay(100);
        }
        if (range <= 10){
        	DE.mC.stop();
        	DE.mB.stop();
        	DE.followLine = true;
        	Sound.beep();
        }
    }
}
