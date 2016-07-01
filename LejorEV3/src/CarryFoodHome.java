
public class CarryFoodHome extends NotifyingThread {

	public CarryFoodHome() {
		
	}

	@Override
	public void doRun() {
		// TODO Auto-generated method stub
		DataExchange de = DataExchange.getInstance();
		de.followLine = true; // starting to try to follow line
	}
	
	
}
