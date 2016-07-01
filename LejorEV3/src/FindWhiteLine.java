import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class FindWhiteLine extends NotifyingThread  {
	private boolean turned = false;
	
	public FindWhiteLine() {
		
	}
	
	@Override
	public void doRun(){
		DataExchange de = DataExchange.getInstance();
		while(true){
			if (de.checkingWhite)
				findLine();
		}
	}
	
	public void findLine() {
		Utilities u = Utilities.getInstance();
		DataExchange de = DataExchange.getInstance();
		u.mB.setSpeed(150);
		u.mC.setSpeed(150);
		if (de.foundBluePillar && !de.maze) {
			driveToWhiteLine();			
		}
		if (de.foundBluePillar && de.maze){
			turnDriveToWhiteLine();
		}
		if (de.foundRedPillar && de.maze){
			u.mB.stop();
			u.mC.stop();
			u.playmusic();
		}
		if ((de.foundRedPillar || de.carryFood) && !de.maze) {
			LCD.drawString("red", 0, 2, false);
			turnDriveToWhiteLine();
			de.checkingWhite = false;
		}
	}

	private void driveToWhiteLine(){
		Utilities u = Utilities.getInstance();
		DataExchange de = DataExchange.getInstance();
		LCD.drawString("blue", 0, 2, false);
		boolean onWhite = Math.abs(de.lvh.getWhite()-u.getAvgLightValue()) <= 0.1 * u.getAvgLightValue();
		
		while(!(Math.abs(de.lvh.getWhite()-u.getAvgLightValue()) <= 0.1 * u.getAvgLightValue())) {

			LCD.drawString("find line", 0, 2, false);
			u.mB.forward();
			u.mC.forward();
		}
		u.mB.stop();
		u.mC.stop();
		LCD.drawString("line found", 0, 2, false);
		de.checkingWhite = false;
		de.followLine = true;

	}
	private void turnDriveToWhiteLine(){
		Utilities u = Utilities.getInstance();
		DataExchange de = DataExchange.getInstance();
		
		u.turnDeg(360);
		while(!(Math.abs(de.lvh.getWhite()-u.getAvgLightValue()) <= 0.1 * u.getAvgLightValue())) {

			LCD.drawString("find line", 0, 2, false);
			u.mB.forward();
			u.mC.forward();
		}

		u.mB.stop();
		u.mC.stop();
		de.followLine = true;
	}
}
