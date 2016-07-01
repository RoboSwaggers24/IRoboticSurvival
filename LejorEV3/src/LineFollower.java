import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.*;

public class LineFollower extends NotifyingThread  {

	
	Boolean gyroWentLeft = false;
	Main2 lfjf;
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

    
    public LineFollower() {
	}
	
	public void isSwiffeling(){
        float deg = Utilities.getInstance().getGyroValue();
		if(power > 100 && deg >= 60.0){
			power = 100;
		}
		else if(power < maxPower && deg < 60.0){
			power = power + 1;
		}
	}
	@Override
	public void doRun(){
		while(!Button.ESCAPE.isDown()){
			move();
		}
		Utilities.getInstance().closeSensors();
	}
	public void move(){
		DataExchange de = DataExchange.getInstance();
		while (de.followLine) {
			isSwiffeling();
			LCD.clear();
			LCD.drawString("followline", 0, 2, false);
			if(Button.UP.isDown()){
				power = power + 10;
			}
			if(Button.DOWN.isDown()){
				power = power - 10;
			}
			threshold = (de.lvh.getBlack() + de.lvh.getWhite())/2;
			color = Utilities.getInstance().getAvgLightValue();
			
			double turn = (2 * power) * ((threshold-color)/(de.lvh.getWhite() - de.lvh.getBlack()));
			
			
			cTurn = (int) (power - turn);
			bTurn = (int) (power + turn);
			Utilities u = Utilities.getInstance();
			if (cTurn > 30){
				u.mC.setSpeed(cTurn);
				u.mC.forward();
			}
			else{
				u.mC.setSpeed((int) (0.5 *bTurn));
				u.mC.backward();
			}
			
			if (bTurn > 30){
				u.mB.setSpeed(bTurn);
				u.mB.forward();
			}
			else{
				u.mB.setSpeed((int) (0.5 *cTurn));
				u.mB.backward();
			}
		}
	}
	

}
