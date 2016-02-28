package comp110;

import org.jsoup.nodes.Element;

public class Player {
  private int _jersey, _height, _played, _started, _minutes, _fieldGoals, _fieldGoalsAttempted, _threesMade,
      _threesAttempted, _freeThrows, _freeThrowsAttempted, _totalPoints, _oRebounds, _dRebounds, _totalRebounds,
      _assists, _turnovers, _steals, _blocks, _fouls, _doubleDoubles, _tripleDoubles;
  private String _first, _last, _year, _position;
  private double _fgPercent, _threePercent, _ftPercent, _avgPoints, _avgReb;

  public Player(Element row) {
    // Reads in the <td> text and stores it in the appropriate instance variable
    _jersey = Scraper.getInt(row, 0);
    String rawName = row.child(1).text();
    String[] rawNameArr = rawName.split(", ");
    _last = rawNameArr[0];
    _first = rawNameArr[1];
    _year = row.child(2).text();
    _position = row.child(3).text();
    String[] rawHeight = row.child(4).text().split("-");
    // Toby Egbuna's height is not on the NCAA page so this handles that
    if (rawHeight.length > 1) {
      // Storing height in inches
      _height = Integer.parseInt(rawHeight[0]) * 12 + Integer.parseInt(rawHeight[1]);
    } else {
      _height = 0;
    }
    _played = Scraper.getInt(row, 5);
    _started = Scraper.getInt(row, 6);
    // Minutes on the page are stored like 345:00
    String rawMinutes = row.child(7).text();
    _minutes = Integer.parseInt(rawMinutes.substring(0, rawMinutes.length() - 3));
    _fieldGoals = Scraper.getInt(row, 8);
    _fieldGoalsAttempted = Scraper.getInt(row, 9);
    _fgPercent = Scraper.getDoub(row, 10);
    _threesMade = Scraper.getInt(row, 11);
    _threesAttempted = Scraper.getInt(row, 12);
    _threePercent = Scraper.getDoub(row, 13);
    _freeThrows = Scraper.getInt(row, 14);
    _freeThrowsAttempted = Scraper.getInt(row, 15);
    _ftPercent = Scraper.getDoub(row, 16);
    _totalPoints = Scraper.getInt(row, 17);
    _avgPoints = Scraper.getDoub(row, 18);
    _oRebounds = Scraper.getInt(row, 19);
    _dRebounds = Scraper.getInt(row, 20);
    _totalRebounds = Scraper.getInt(row, 21);
    _avgReb = Scraper.getDoub(row, 22);
    _assists = Scraper.getInt(row, 23);
    _turnovers = Scraper.getInt(row, 24);
    _steals = Scraper.getInt(row, 25);
    _blocks = Scraper.getInt(row, 26);
    _fouls = Scraper.getInt(row, 27);
    _doubleDoubles = Scraper.getInt(row, 28);
    _tripleDoubles = Scraper.getInt(row, 29);
  }

  public String toString() {
    return String.format("%2d %10s %10s %5d %5d %5.2f %5.2f", _jersey, _first, _last, _played, _minutes, _avgPoints,
        _avgReb);
  }

  public int getJersey() {
    return _jersey;
  }

  public int getHeight() {
    return _height;
  }

  public int getPlayed() {
    return _played;
  }

  public int getStarted() {
    return _started;
  }

  public int getMinutes() {
    return _minutes;
  }

  public int getFieldGoals() {
    return _fieldGoals;
  }

  public int getFieldGoalsAtt() {
    return _fieldGoalsAttempted;
  }

  public int getThrees() {
    return _threesMade;
  }

  public int getThreesAtt() {
    return _threesAttempted;
  }

  public int getFreeThrows() {
    return _freeThrows;
  }

  public int getFreeThrowsAtt() {
    return _freeThrowsAttempted;
  }

  public int getTotalPoints() {
    return _totalPoints;
  }

  public int getORebounds() {
    return _oRebounds;
  }

  public int getDRebounds() {
    return _dRebounds;
  }

  public int getTotalRebs() {
    return _totalRebounds;
  }

  public int getAssists() {
    return _assists;
  }

  public int getTurnovers() {
    return _turnovers;
  }

  public int getSteals() {
    return _steals;
  }

  public int getBlocks() {
    return _blocks;
  }

  public int getFouls() {
    return _fouls;
  }

  public String getFirst() {
    return _first;
  }

  public String getLast() {
    return _last;
  }

  public String getYear() {
    if (_year.equals("Sr")) {
      return "Senior";
    } else if (_year.equals("Jr")) {
      return "Junior";
    } else if (_year.equals("So")) {
      return "Sophomore";
    } else if (_year.equals("Fr")) {
      return "Freshman";
    } else
      return _year;
  }

  public String getPosition() {
    return _position;
  }

  public double getFgPercent() {
    return _fgPercent;
  }

  public double getThreePercent() {
    return _threePercent;
  }

  public double getFtPercent() {
    return _ftPercent;
  }

  public double getAvgPoints() {
    return _avgPoints;
  }

  public double getAvgRebs() {
    return _avgReb;
  }

  public int getDoubleDoubles() {
    return _doubleDoubles;
  }

  public int getTripleDoubles() {
    return _tripleDoubles;
  }

  public String getFullName() {
    return _first + " " + _last;
  }

}
