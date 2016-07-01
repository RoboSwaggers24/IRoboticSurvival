import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;


public class Utilities {

    private SampleProvider sampleProvider;
    private int sampleSize;
    
    private EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S2);
	private EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
    public RegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
	public RegulatedMotor mC = new EV3LargeRegulatedMotor(MotorPort.C);
	public RegulatedMotor claw = new EV3MediumRegulatedMotor(MotorPort.A);
	private EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S4);
    
    // SINGLETION
    private static Utilities thisInstance = null;
    
    protected Utilities() {
    	// Exist only to defeat more then one instance
    }

	public static Utilities getInstance() {
		if (thisInstance == null) {
			thisInstance = new Utilities();
		}
		return thisInstance;
	}
	///////////////////////////////////////
	
	private float[] getSample() {
        // Initializes the array for holding samples
        sampleSize = sampleProvider.sampleSize();
        float[] sample = new float[sampleSize];

        // Gets the sample an returns it
        sampleProvider.fetchSample(sample, 0);
        return sample;
    }
	
	public float getGyroValue(){
		// sample for gyro
		sampleProvider = gyro.getRateMode();
		
		float[] samples = getSample();
		float sum = 0;
		for (int i = 0; i < samples.length; i++) {
			sum += samples[i];	
		}
		return Math.abs((sum / samples.length));
	}
	
	public float changeInDeg() {
		sampleProvider = gyro.getAngleMode();
		float[] samples = getSample();
		float sum = 0;
		for (int i = 0; i < samples.length; i++) {
			sum += samples[i];	
		}
		return Math.abs((sum / samples.length));
	}
	
	public double getDistanceValue(){
		// sample for ultrasonic sensor
		sampleProvider = us.getDistanceMode();
		
		float[] samples = getSample();
		double sum = 0.0;
		for (int i = 0; i < samples.length; i++) {
			sum += samples[i];	
		}
		return Math.abs((sum / samples.length));
	}
	
	public float getAvgLightValue() {
		sampleProvider = colorSensor.getRedMode();
        
		float[] samples = getSample();
		float sum = 0;
		for (int i = 0; i < samples.length; i++) {
			sum += samples[i];	
		}
		return sum / samples.length;
	}
	public float getAvgAmbientLightValue() {
		sampleProvider = colorSensor.getAmbientMode();
        
		float[] samples = getSample();
		float sum = 0;
		for (int i = 0; i < samples.length; i++) {
			sum += samples[i];	
		}
		return sum / samples.length;
	}

	public int getColorValue() {
		return colorSensor.getColorID();
	}
	
	public void closeSensors(){
		colorSensor.close();
		us.close();
		gyro.close();
		mB.close();
		mC.close();
	}
	public void turnDeg(int deg){
		 mB.synchronizeWith(new RegulatedMotor[]{mC});
	     mB.startSynchronization();
	     mB.rotate(deg);
	     mC.rotate(deg);
	     mB.endSynchronization();
	}
	public void playmusic(){
		  Music music = new Music();
		  for(int i = 0; i < 2; i++) {
			  music.musicTone( "F#5" , 150);
			  Sound.pause(150);
			  music.musicTone( "C#6" , 150);
			  Sound.pause(150);
			  music.musicTone( "C#6" , 150);
			  music.musicTone( "B5" , 150);
			  music.musicTone( "C#6" , 150);
			  Sound.pause(150);
			  music.musicTone( "B5" , 150);
			  music.musicTone( "A5" , 150);
			  music.musicTone( "B5" , 150);
			  Sound.pause(150);
			  music.musicTone( "B5" , 150);
			  music.musicTone( "A5" , 150);
			  music.musicTone( "F#5" , 150);
			  music.musicTone( "A5" , 150);
		  }
	}


}