package comp110;
/**
 * A convenient container for team stats that includes the name, value, and ranks
 */
public class TeamStat {
	private String _name;
	private int _nationalRank, _conferenceRank;
	private double _value;
	
	public TeamStat(String name, double value, int conferenceRank, int nationalRank) {
		_name = name;
		_value = value;
		_conferenceRank = conferenceRank;
		_nationalRank = nationalRank;
	}
}
