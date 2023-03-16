/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.servicelayer;

import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Guess;
import java.util.List;

/**
 *
 * @author solomon
 */
public interface GuessTheNumberServiceLayer {

    public Game addGame() throws InvalidGameException;
    public Guess addGuess(Guess guess) throws InvalidGameException, InvalidGuessException;
    public List<Game> getAllGames() throws InvalidGameException;
    public Game getGameById(int id) throws InvalidGameException;
    public List<Guess> getGuessesByGameId(int gameId) throws InvalidGameException;
    
}