/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.servicelayer;

import com.sg.guessthenumber.data.GameDao;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Guess;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author solomon
 */
@SpringBootTest
public class GuessTheNumberServiceLayerTest {

    @Autowired
    GuessTheNumberServiceLayer service;
    
    @Autowired
    GameDao gameDao;

    public GuessTheNumberServiceLayerTest() {
    }

    @BeforeEach
    public void setUp() throws InvalidGameException {
        
        // need to use gameDao to delete games and see the answers of in-progress games
        List<Game> allGames = gameDao.getAllGames();
        
        for (Game game : allGames) {
            gameDao.deleteGameById(game.getGameId());
        }
        
    }

    @Test
    public void testAddGetGame() throws InvalidGameException {
        
        // adds 10 random games, to check that each game's answer has unique digits
        for (int i = 0; i < 10; i++) {
            Game game = service.addGame();
            // answer should be hidden
            assertTrue(game.getAnswer().equals("****"));
            assertTrue(game.isGameInProgress());
            game.setGameInProgress(false);
            gameDao.updateGame(game);
            game = service.getGameById(game.getGameId());
            String answer = game.getAnswer();
            //check answer...
            //checking every single answer, to make sure that every digit is unique
            // loop through the first string
            for (int j = 0; j < answer.length(); j++) {
                // loop through the second string
                for (int k = 0; k < answer.length(); k++) {
                    // compare both strings!
                    if (j != k && answer.charAt(j) == answer.charAt(k)) {
                        fail("game answer has duplicate digits");
                    }
                    
                }
                
            }
        }
        
    }

    @Test
    public void testAddSuccessfulGuess() throws InvalidGameException, InvalidGuessException {
        
        Game game = new Game();
        game.setAnswer("1234");
        game.setGameInProgress(true);
        // add game using gameDao so we can we can set the answer
        game = gameDao.addGame(game);
        Guess guess = new Guess();
        guess.setGameId(game.getGameId());
        guess.setAnswer("1234");
        // guess the answer exactly
        guess = service.addGuess(guess);
        game = service.getGameById(game.getGameId());
        // make sure game answer is no longer hidden
        assertTrue(game.getAnswer().equals("1234"));
        // make sure game is no longer playable
        assertFalse(game.isGameInProgress());
        // make sure guess reported an exact match
        assertTrue(guess.getResult().equals("e:4:p:0"));
        List<Guess> gameGuesses = service.getGuessesByGameId(game.getGameId());

        //there was only one guess
        assertEquals(gameGuesses.size(), 1);
        assertTrue(gameGuesses.contains(guess));

        // make sure guessing a finished game throws an exception
        try {
            Guess invalidGuess = new Guess();
            guess.setGameId(game.getGameId());
            guess.setAnswer("4321");
            service.addGuess(guess);
        } catch (InvalidGameException ex) {
            return;
        }

    }
    
    @Test
    public void testAddInvalidGuess() throws InvalidGuessException, InvalidGameException {
        
        Game game = service.addGame();
        
        try {
            Guess guess = new Guess();
            guess.setGameId(game.getGameId());
            guess.setAnswer("236");
            service.addGuess(guess);
        } catch(InvalidGuessException ex) {
            return;
        } catch(InvalidGameException ex) {
            fail("Incorrect exception was thrown!");
        }
        
    }
    
    @Test
    public void testGuessNonExistingGame() throws InvalidGameException, InvalidGuessException {

        Guess guess = new Guess();
        guess.setAnswer("1234");
        guess.setGameId(14);
        
        try {
            service.getGameById(14);
        } catch(InvalidGameException ex) {
            
        }
        
        try {
            service.addGuess(guess);
        } catch(InvalidGameException ex) {
            return;
        } catch(InvalidGuessException ex) {
            fail("Incorrect exception was thrown!");
        }
        
    }
    
}
