select winning_team, count(*) as wins
from score
group by winning_team
order by count(*) desc
limit 1;
-- select team with least wins
select winning_team, count(*) as wins
from score
group by winning_team
order by count(*) asc
limit 1;
-- select player with most wins
select player_name, count(*) as wins
from player, score
where player.team_name = score.winning_team
and player.team_start_date <= score.date
and (player.team_end_date >= score.date or player.team_end_date is null)
group by winning_team
order by count(*) desc
limit 1;
-- select team record for @team variable
set @team = 'TEAM3';
select @team as team, sum(winning_team = @team) as wins, sum(losing_team = @team) as losses
from score
where winning_team = @team
or losing_team = @team;
-- select current team roster
set @team = 'TEAM1';
select * from player
where team_name = @team
and team_end_date is null;
-- insert new game and score
set @gamecity = 'TEMPE';
set @gamedate = '2020-09-30';
set @winner = 'TEAM1';
set @loser = 'TEAM3';
set @winscore = 5;
set @loserscore = 4;
insert into game values (@gamecity, @gamedate, @winner, @loser);
insert into score values (@gamedate, @gamecity, @winner, @loser, @winscore, @loserscore);
-- update player end_date
set @end_date = '2020-09-30';
set @player = 'STEVE';
set @team = 'TEAM2';
update player
set team_end_date = @end_date
where player_name = @player 
and team_name = @team;
-- delete score record
set @gamecity = 'TEMPE';
set @gamedate = '2020-09-30';
set @winner = 'TEAM1';
set @loser = 'TEAM3';
delete from score
where date = @gamedate
and city = @gamecity
and winning_team = @winner
and losing_team = @loser;
