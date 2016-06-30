import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
public class LFUtils {

	static EV3ColorSensor colorSensor;
    private static SampleProvider sampleProvider;
    private static int sampleSize;

	public LFUtils(EV3ColorSensor colorSensor){
		this.colorSensor = colorSensor;
	}
	
	private static float[] getSample() {
        // Initializes the array for holding samples
        float[] sample = new float[sampleSize];

        // Gets the sample an returns it
        sampleProvider.fetchSample(sample, 0);
        return sample;
    }
	public static float getAvgLightValue() {
		sampleProvider = colorSensor.getRedMode();
        sampleSize = sampleProvider.sampleSize();
		float[] samples = getSample();
		float sum = 0;
		for (int i = 0; i < samples.length; i++) {
			sum += samples[i];	
		}
		return sum / samples.length;
	}
	public static float getAvgAmbientLightValue() {
		sampleProvider = colorSensor.getAmbientMode();
        sampleSize = sampleProvider.sampleSize();
		float[] samples = getSample();
		float sum = 0;
		for (int i = 0; i < samples.length; i++) {
			sum += samples[i];	
		}
		return sum / samples.length;
	}
	
	public static void closeColorSensor(){
		colorSensor.close();
	}

}