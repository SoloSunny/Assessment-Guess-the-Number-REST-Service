/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Guess;
import java.util.List;

/**
 *
 * @author solomon
 */
public interface GuessDao {

    List<Guess> getGuessesByGameId(int id);
    Guess addGuess(Guess guess);
            
}