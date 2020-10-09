create schema sports;

create table sports.city (
city_name	varchar(45)	not null,
state		char(2)		not null,
primary key (state, city_name),
index (city_name)
);

create table sports.team (
team_name	varchar(45)	not null,
home_city	varchar(45)	not null,
primary key (team_name),
foreign key (home_city) references sports.city (city_name)
);

create table sports.player (
player_name		varchar(45)	not null,
dob			date		not null,
team_start_date	date,
team_end_date	date,
team_name		varchar(45)	not null,
primary key (dob, player_name, team_name),
foreign key (team_name) references sports.team (team_name)
);

create table sports.game (
city		varchar(45)	not null,
date		date		not null,
home_team	varchar(45)	not null,
away_team	varchar(45)	not null,
primary key (date, city),
foreign key (city) references sports.city (city_name),
foreign key (home_team) references sports.team (team_name),
foreign key (away_team) references sports.team(team_name)
);

Create table sports.score (
date			date		not null,
city			varchar(45)	not null,
winning_team		varchar(45)	not null,
losing_team		varchar(45)	not null,
winning_score		int		not null,
losing_score		int		not null,
primary key (date, city),
foreign key (date) references sports.game (date),
foreign key (city) references sports.city (city_name),
foreign key (winning_team) references sports.team (team_name),
foreign key (losing_team) references sports.team (team_name)
);
