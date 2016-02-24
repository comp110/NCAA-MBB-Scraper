package comp110;

public class TeamStat {
  String stat;
  double value;
  int rank;
  
  public TeamStat(String stat, double value, int rank){
    this.stat = stat;
    this.value = value;
    this.rank = rank;
  }
  
  public String getStat(){
    return stat;
  }
  
  public double getValue(){
    return value;
  }
  
  public int getRank(){
    return rank;
  }
}
