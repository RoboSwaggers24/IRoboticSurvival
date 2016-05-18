import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.Button;
import lejos.hardware.lcd.*;


public class Cruiser extends Thread {
	LFJfedor lfjf;
	RegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
	RegulatedMotor mC = new EV3LargeRegulatedMotor(MotorPort.C);
	float color;
	float turn;
	int cTurn;
	int bTurn;
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
			
			threshold = (lfjf.lvh.getBlack() + lfjf.lvh.getWhite())/2;
			color = LFUtils.getAvgLightValue();
			double turn = (2.5 * power) * ((threshold-color)/(lfjf.lvh.getWhite() - lfjf.lvh.getBlack()));
			
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