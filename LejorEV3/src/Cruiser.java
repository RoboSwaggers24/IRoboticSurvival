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
	float power = 222;
	float threshold;
	
	public Cruiser(LFJfedor lfjf) {
		this.lfjf = lfjf;
	}

	public void run() {

		LCD.clear();
		LCD.drawString("Started Cruiser", 0, 2);

		
		while (!Button.ESCAPE.isDown()) {
			
			threshold = (lfjf.lvh.getBlack() + lfjf.lvh.getWhite())/2;
			color = LFUtils.getAvgLightValue();
			cTurn = (int) (power - (2*power) * (threshold-color)/(lfjf.lvh.getWhite() - lfjf.lvh.getBlack()));
			mC.setSpeed(cTurn);
			mC.forward();

			bTurn = (int) (power + (2*power) * (threshold-color)/(lfjf.lvh.getWhite() - lfjf.lvh.getBlack()));
			mB.setSpeed(bTurn);
			mB.forward();


		}
	}

}