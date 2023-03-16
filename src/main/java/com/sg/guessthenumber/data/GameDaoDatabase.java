/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author solomon
 */
@Repository
public class GameDaoDatabase implements GameDao {
    
    private final JdbcTemplate jdbc;
    
    @Autowired
    public GameDaoDatabase(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional
    public Game addGame(Game game) {
        final String sql = "INSERT INTO Game (Answer, GameInProgress) VALUES(?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbc.update((Connection conn) -> {
            PreparedStatement pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStmt.setString(1, game.getAnswer());
            pStmt.setBoolean(2, game.isGameInProgress());
            return pStmt;
        }, keyHolder);
        
        game.setGameId(keyHolder.getKey().intValue());
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT GameId, Answer, GameInProgress FROM Game;";
        return jdbc.query(sql, new GameMapper());
    }

    @Override
    public Game getGameById(int id) {

        try {
            final String sql = "SELECT * FROM Game WHERE GameId = ?;";
            return jdbc.queryForObject(sql, new GameMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
        
    }

    @Override
    public boolean updateGame(Game game) {
        final String sql = "UPDATE Game SET GameInProgress = ? WHERE GameId = ?";
        return jdbc.update(sql, 
                game.isGameInProgress(), game.getGameId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteGameById(int id) {
        final String sql = "DELETE FROM Guess WHERE GameId = ?;";
        final String sqlGame = "DELETE FROM Game WHERE GameId = ?";
        jdbc.update(sql, id);
        return jdbc.update(sqlGame, id) == 1;
    }
    
    public static final class GameMapper implements RowMapper<Game> {
        
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            
            Game game = new Game();
            game.setGameId(rs.getInt("GameId"));
            game.setAnswer(rs.getString("Answer"));
            game.setGameInProgress(rs.getBoolean("GameInProgress"));
            return game;
            
        }
    }
    
}
