# NCAAScraper
Scrapes data from the NCAA website

The scraper currently works on team stat pages in this format http://stats.ncaa.org/team/stats?org_id=457&sport_year_ctl_id=12260

It uses jsoup to scrape player info and stats and adds the players into a team along with the team's name and record and uses gson to output the team object in JSON format.

It should be mostly straigtforward to tweak this to use on game pages such as: http://stats.ncaa.org/game/index/3985950?org_id=457