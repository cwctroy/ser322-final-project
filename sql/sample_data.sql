use sports;
insert into city values
('TEMPE', 'AZ'),
('DALLAS', 'TX'),
('GOLDEN', 'CO'),
('LOS ANGELES', 'CA'),
('HOUSTON', 'TX');
insert into team values
('TEAM1', 'TEMPE'),
('TEAM2', 'DALLAS'),
('TEAM3', 'GOLDEN'),
('TEAM4', 'LOS ANGELES'),
('TEAM5', 'HOUSTON');
insert into game values
('TEMPE', '2020-01-01', 'TEAM1', 'TEAM2'),
('DALLAS', '2019-10-30', 'TEAM3', 'TEAM1'),
('GOLDEN', '2020-07-13', 'TEAM2', 'TEAM3'),
('LOS ANGELES', '2020-08-01', 'TEAM3', 'TEAM4'),
('HOUSTON', '2018-07-06', 'TEAM4', 'TEAM2');
insert into player values
('JOE BLO', '1967-02-15', '2012-02-15', null, 'TEAM1'),
('STEVE', '2000-09-30', '2019-08-01', null, 'TEAM2'),
('TIM TOM TAM', '1989-10-30', '2020-09-30', null, 'TEAM3'),
('JEFF ZU', '1986-07-07', '2020-08-30', null, 'TEAM4'),
('KEVIN HAK', '1997-06-24', '2020-07-26', null, 'TEAM5');
insert into score values
('2020-01-01', 'TEMPE', 'TEAM1', 'TEAM2', 2, 1),
('2019-10-30', 'DALLAS', 'TEAM3', 'TEAM1', 4, 0),
('2020-07-13', 'GOLDEN', 'TEAM3', 'TEAM2', 3, 0),
('2020-08-01', 'LOS ANGELES', 'TEAM4', 'TEAM3', 3, 2),
('2018-07-06', 'HOUSTON', 'TEAM4', 'TEAM2', 9, 1);
