import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.Button;
import lejos.hardware.lcd.*;


public class Cruiser extends Thread {
	LFJfedor lfjf;
	RegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
	RegulatedMotor mC = new EV3LargeRegulatedMotor(MotorPort.C);
	EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
	float color;
	float turn;
	int cTurn;
	int bTurn;
	final int maxPower = 250;
	float power = 100;
	float threshold;
	
	public Cruiser(LFJfedor lfjf) {
		this.lfjf = lfjf;
	}

	public void run() {

		LCD.clear();
		LCD.drawString("Started Cruiser", 0, 2);
		
		
		while (!Button.ESCAPE.isDown()) {
			if(Button.UP.isDown()){
				power = power + 10;
				System.out.println("power is: " + power);
			}
			if(Button.DOWN.isDown()){
				power = power - 10;
				System.out.println("power is: " + power);
			}
			//while(touch.getTouchMode() ==){
				
			//}
			threshold = (lfjf.lvh.getBlack() + lfjf.lvh.getWhite())/2;
			color = LFUtils.getAvgLightValue();
			double turn = (2.5 * power) * ((threshold-color)/(lfjf.lvh.getWhite() - lfjf.lvh.getBlack()));
			if(power < maxPower && Math.abs(threshold - color) <= (0.5 * threshold)){
				power += 1;
			}
			else if(Math.abs(threshold - color) > (0.5 * threshold)){
				System.out.println(power);
				power = 100;
			}
			
			cTurn = (int) (power - turn);
			bTurn = (int) (power + turn);
			
				
			if (cTurn > 10){
				mC.setSpeed(cTurn);
				mC.forward();
			}
			else{
				mC.setSpeed((int) (0.5 *bTurn));
				mC.backward();
			}

			
			if (bTurn > 10){
				mB.setSpeed(bTurn);
				mB.forward();
			}
			else{
				mB.setSpeed((int) (0.5 *cTurn));
				mB.backward();
			}

		}
		mB.close();
		mC.close();
		lfjf.colorSensor.close();
	}

}