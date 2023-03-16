/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.servicelayer;

import com.sg.guessthenumber.data.GameDao;
import com.sg.guessthenumber.data.GuessDao;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Guess;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author solomon
 */
@Service
public class GuessTheNumberServiceFileImpl implements GuessTheNumberServiceLayer {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    GuessDao guessDao;
    
    
    private String generateNumber() {
        
        ArrayList<Integer> list = new ArrayList<>();
        
        
       
        for(int i = 0; i < 9; i++) {
            list.add(i);
        }
        
       
        Collections.shuffle(list);
        String generatedNumber = "";
        
        
        for(int i = 0; i < 4; i ++){
            generatedNumber += list.get(i);
        }
        return generatedNumber;
        
    }

    @Override
    public Game addGame() throws InvalidGameException {
        
        Game game = new Game();
        game.setGameInProgress(true);
        game.setAnswer(generateNumber());
        gameDao.addGame(game);
        hideGameAnswer(game);
        return game;
        
    }
    
    @Override
    public Guess addGuess(Guess guess) throws InvalidGameException, InvalidGuessException {
        
        int id = guess.getGameId();
        Game game = gameDao.getGameById(id);
        
        if(game == null) {
            throw new InvalidGameException("No game found for Game ID : " + id);
        }
        
        if(!game.isGameInProgress()) {
            throw new InvalidGameException("This game is finished.");
        }
        
        int answerLength = game.getAnswer().length();
        
        if(guess.getAnswer().length() != answerLength) {
            throw new InvalidGuessException("Answer length is not equal to : " + answerLength);
        }
        
        calculateResult(guess);
        guess.setTime(LocalDateTime.now());
        return guessDao.addGuess(guess);
        
    }
    
    private void calculateResult(Guess guess) {
        Game game = gameDao.getGameById(guess.getGameId());
        int exactCount = 0;
        int partialCount = 0;
        
        
        char[] userAnswer = guess.getAnswer().toCharArray();
        char[] answer = game.getAnswer().toCharArray();
        
     
        for (int i = 0; i < userAnswer.length; i++) {
            
            if (userAnswer[i] == answer[i]) {
              
                exactCount ++;
            } else {
                
                for (int j = 0; j < answer.length; j++) {
                    if (userAnswer[i] == answer[j]) {
                        partialCount++;
                    }
                }
                
            }
            
        }
        
        if (exactCount == 4) {
            game.setGameInProgress(false);
            gameDao.updateGame(game);
        }
        guess.setResult("e:" + exactCount + ":p:" + partialCount);
    }
    
    
    private void hideGameAnswer(Game game) {
        game.setAnswer("****");
    }

    @Override
    public List<Game> getAllGames() throws InvalidGameException {
        
        List<Game> games = gameDao.getAllGames();
        
        if(games.size() == 0) {
            throw new InvalidGameException("No games created!");
        }
        
        for (Game game : games) {
            if (game.isGameInProgress()) {
                hideGameAnswer(game);
            }
        }
        return games;
        
    }

    @Override
    public Game getGameById(int id) throws InvalidGameException {
        
        Game game = gameDao.getGameById(id);
        
        if(game == null) {
            throw new InvalidGameException("No game found for Game ID : " + id);
        }
        
        if (game.isGameInProgress()) {
                hideGameAnswer(game);
            }
        return game;
        
    }

    @Override
    public List<Guess> getGuessesByGameId(int gameId) throws InvalidGameException {
        Game game = gameDao.getGameById(gameId);
        if(game == null) {
            throw new InvalidGameException("No game found for Game ID : " + gameId);
        }
        List<Guess> guesses = guessDao.getGuessesByGameId(gameId);
        return guesses;
    }
    
}