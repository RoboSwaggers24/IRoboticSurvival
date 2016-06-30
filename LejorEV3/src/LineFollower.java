import lejos.hardware.Button;
import lejos.robotics.SampleProvider;

public class LineFollower extends Thread {

	
	//EV3IRSensor proxY = new EV3IRSensor(SensorPort.S2);
	Boolean gyroWentLeft = false;
	LFJfedor lfjf;
	int maxInt = Integer.MAX_VALUE;
	float color;
	float turn;
	float deg;
	boolean pilar = true;
	float prevDeg = 0;
	int cTurn;
	int bTurn;
	int prevcTurn = maxInt;
	int prevbTurn = maxInt;
	final int maxPower = 300;
	float power = 100;
	float threshold;
	DataExchange DE;
	SampleProvider gyroSampleProvider;
    private static int sampleSize;
	//int[][] map = new int [maxInt][maxInt];
	public LineFollower(LFJfedor lfjf, DataExchange DE) {
		this.DE = DE;
		gyroSampleProvider = DE.gyro.getRateMode();
		this.lfjf = lfjf;
	}
	
	private static float[] getSample(SampleProvider sampleProvider) {
        // Initializes the array for holding samples
        float[] sample = new float[sampleSize];
        // Gets the sample an returns it
        sampleProvider.fetchSample(sample, 0);
        return sample;
    }
	
	
	public float getSampleValue(SampleProvider sampleProvider){
		sampleSize = sampleProvider.sampleSize();
		int deduct = 0;
		float[] samples = getSample(sampleProvider);
		float sum = 0;
		for (int i = 0; i < samples.length; i++) {
			//System.out.println(samples[i]);
			//if(Float.isInfinite(samples[i]))
			//	samples[i] = 100;
			sum += samples[i];	
		}
		return Math.abs((sum / samples.length));
	}
	
	public void isSwiffeling(){
        float deg = getSampleValue(gyroSampleProvider );
		if(power > 100 && deg >= 90.0){
			power = 100;
		}
		else if(power < maxPower && deg < 70.0){
			power = power + 1;
		}
	}
	public void run(){

		
		while(!Button.ESCAPE.isDown()){
			move();
		}
		LFUtils.closeColorSensor();
	}
	public void move(){
		while (DE.followLine) {
			if(Button.UP.isDown()){
				power = power + 10;
			}
			if(Button.DOWN.isDown()){
				power = power - 10;
			}
			threshold = (lfjf.lvh.getBlack() + lfjf.lvh.getWhite())/2;
			color = LFUtils.getAvgLightValue();
			
			double turn = (2 * power) * ((threshold-color)/(lfjf.lvh.getWhite() - lfjf.lvh.getBlack()));
			/*if(power < maxPower && Math.abs(threshold - color) <= (0.5 * threshold)){
				power += 1;
			}
			else if(Math.abs(threshold - color) > (0.5 * threshold)){
				power = 100;
			}*/
			
			cTurn = (int) (power - turn);
			bTurn = (int) (power + turn);
			if (cTurn > 30){
				DE.mC.setSpeed(cTurn);
				DE.mC.forward();
			}
			else{
				DE.mC.setSpeed((int) (0.5 *bTurn));
				DE.mC.backward();
			}
			
			if (bTurn > 30){
				DE.mB.setSpeed(bTurn);
				DE.mB.forward();
			}
			else{
				DE.mB.setSpeed((int) (0.5 *cTurn));
				DE.mB.backward();
			}
			isSwiffeling();
			addToMap();
		}
	}
	
	private void addToMap(){
		if(prevcTurn > maxPower){
			prevcTurn = cTurn;
			prevbTurn = bTurn;
		}
		else{
			float degDiff = prevDeg;
		}
	}
	/*
	public void pilar(){
		SampleProvider proxySampleProvider = proxY.getDistanceMode();
		while (!Button.ESCAPE.isDown()) {
			mC.setSpeed(50);
			mB.setSpeed(50);
			float sample = getSampleValue(proxySampleProvider);
			if (sample< 50.0 ) {
				//Double degrees = 360.0;
				//Double rotate_degree = degrees * sample;
				
				mC.forward();
				mB.forward();
			}
			else {
				mC.forward();
				mB.backward();
				
			}
			
		}
	}
	*/
	public void close(){
		lfjf.colorSensor.close();
	}
}
