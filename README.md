# NCAAScraper
Scrapes data from the NCAA website

The scraper currently works by loading two kinds of pages from the stats.ncaa.org website. The first has stats on individual players on a team (for example,  http://stats.ncaa.org/team/stats?org_id=457&sport_year_ctl_id=12260 ). The second has stats for the entire team along with their conference and national rankings (for example, http://stats.ncaa.org/rankings/ranking_summary?academic_year=2016&division=1.0&org_id=457&ranking_period=90&sport_code=MBB ). The scraper takes advantage of the id system that the ncaa website uses to load both pages for the same team and takes the team's id as an argument (it uses UNC's id without an argument). You can get all the D1 ids from the links on this page: http://stats.ncaa.org/team/inst_team_list .

The individual stats are placed into player objects which are placed into a team object's roster. All the team stats from the rankings page are stored in the team object's _stats array. Finally, the team object is converted into a JSON file using gson and printed to the standard output.

The stats.ncaa.org website is a little temperamental at times so if you're getting an error wait a minute and try it again.
