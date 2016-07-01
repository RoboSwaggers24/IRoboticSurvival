
public class GridTask extends NotifyingThread{
	
	public int numOfCorners = 1;
	
	@Override
	public void doRun() {
		// TODO Auto-generated method stub
		Utilities u = Utilities.getInstance();
		DataExchange de = DataExchange.getInstance();
		float degree = numOfCorners*88.0f;
		while(!de.maze && de.grid) {
			if (numOfCorners >= 6) {
				de.lvh.swapColors();
			}
			if (u.changeInDeg() > degree) {
				this.numOfCorners += 1;
			}
			
			if (this.numOfCorners >= 9) {
				de.maze = true;
				de.lvh.swapColors();
			}
		}
	}

}
