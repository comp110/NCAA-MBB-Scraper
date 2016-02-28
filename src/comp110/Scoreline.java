package comp110;

class Scoreline {

  String _label;
  double _homeValue;
  double _awayValue;

  Scoreline(String label, double awayValue, double homeValue) {
    _label = label;
    _homeValue = homeValue;
    _awayValue = awayValue;
  }

  String getLabel() {
    return _label;
  }

  double getHomeValue() {
    return _homeValue;
  }

  double getAwayValue() {
    return _awayValue;
  }

}