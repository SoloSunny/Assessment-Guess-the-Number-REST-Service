/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Game;
import java.util.List;

/**
 *
 * @author solomon
 */
public interface GameDao {

    Game addGame(Game game);    
    List<Game> getAllGames();  
    Game getGameById(int id);
    boolean updateGame(Game game);
    boolean deleteGameById(int id);
    
}
