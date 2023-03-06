DROP DATABASE IF EXISTS GuessTheNumberDB;
CREATE DATABASE GuessTheNumberDB;
USE GuessTheNumberDB;

CREATE TABLE game (
	game_Id INT PRIMARY KEY AUTO_INCREMENT,
    answer char(4),
    finished BOOLEAN DEFAULT false
    );
    
INSERT INTO game(game_id, answer, finished) VALUES
	(1, "6851", true),
	(2, "1245", true),
	(3, "5872", true);

CREATE TABLE round (
	round_Id INT PRIMARY KEY AUTO_INCREMENT,
    game_id INT NOT NULL,
    guess_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    guess CHAR(4),
    result CHAR(8),
    FOREIGN KEY fk_gameid (game_id) REFERENCES game(game_id)
    );
    
    INSERT INTO round (round_Id, game_Id, guess_time, guess, result) VALUES 
    (1, 1, "2023-02-28 03:18:20", "1254", "e:0:p:1"),
    (2, 1, "2023-02-28 05:01:07", "6582", "e:0:p:2"),
    (3, 1, "2023-03-03 07:13:49", "4587", "e:3:p:0"),
    (4, 2, "2023-03-03 12:31:09", "2348", "e:4:p:0"),
    (5, 3, "2023-03-04 01:42:55", "1287", "e:0:p:0"),
    (6, 3, "2023-03-05 06:13:12", "1345", "e:2:p:0");
    
	select * from game;
    select * from round
    
    