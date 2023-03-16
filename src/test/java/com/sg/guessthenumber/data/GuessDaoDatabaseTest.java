/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Guess;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author solomon
 */
@SpringBootTest
public class GuessDaoDatabaseTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    GuessDao guessDao;
    
    public GuessDaoDatabaseTest() {
    }

    
    @BeforeEach
    public void setUp() {
        
        List<Game> allGames = gameDao.getAllGames();
        
        for (Game game : allGames) {
            gameDao.deleteGameById(game.getGameId());
        }

    }

    @Test
    public void testAddGetGuesses() {
        
        Game game = new Game();
        game.setAnswer("1234");
        game.setGameInProgress(true);
        game = gameDao.addGame(game);
        
        Game game2 = new Game();
        game2.setAnswer("9876");
        game2.setGameInProgress(true);
        game2 = gameDao.addGame(game2);
        
        Guess guess = new Guess();
        guess.setGameId(game.getGameId());
        guess.setAnswer("5246");
        guess.setResult("e:1:p:1");
        guess.setTime(LocalDateTime.now().minusSeconds(10));
        guess = guessDao.addGuess(guess);
        
        Guess game2guess = new Guess();
        game2guess.setGameId(game2.getGameId());
        game2guess.setAnswer("8670");
        game2guess.setResult("e:1:p:2");
        game2guess.setTime(LocalDateTime.now());
        game2guess = guessDao.addGuess(game2guess);
        
        Guess guess2 = new Guess();
        guess2.setGameId(game.getGameId());
        guess2.setAnswer("1234");
        guess2.setResult("e:4:p:0");
        guess2.setTime(LocalDateTime.now().minusSeconds(10));
        guess2 = guessDao.addGuess(guess2);
        
        game.setGameInProgress(false);
        gameDao.updateGame(game);
        
        List<Guess> guessesForGame = guessDao.getGuessesByGameId(game.getGameId());
        
        assertEquals(guessesForGame.size(), 2);
        assertTrue(guessesForGame.contains(guess));
        assertTrue(guessesForGame.contains(guess2));
        assertFalse(guessesForGame.contains(game2guess));
        
    }
    
}
