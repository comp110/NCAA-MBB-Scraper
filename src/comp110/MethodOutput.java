package comp110;

public class MethodOutput {
	private String _name;
	private double _homeValue;
	private double _awayValue;
	
	public MethodOutput(String name, double homeValue, double awayValue) {
		_name = name;
		_homeValue = homeValue;
		_awayValue = awayValue;
	}

	public String getName() {
		return _name;
	}

	public double getHomeValue() {
		return _homeValue;
	}

	public double getAwayValue() {
		return _awayValue;
	}
	
	
}
