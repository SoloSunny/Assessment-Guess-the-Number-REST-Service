/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Guess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author solomon
 */
@Repository
public class GuessDaoDatabase implements GuessDao {
    
    private final JdbcTemplate jdbc;
    
    @Autowired
    public GuessDaoDatabase(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Guess> getGuessesByGameId(int id) {
        final String sql = "SELECT * FROM Guess WHERE GameId = ?";
        List<Guess> guesses = jdbc.query(sql, new GuessMapper(), id);
        return guesses;
    }

    @Override
    public Guess addGuess(Guess guess) {
        final String sql = "INSERT INTO Guess (Time, Answer, Result, GameId) VALUES (?,?,?,?)";
        jdbc.update(sql, Timestamp.valueOf(guess.getTime()), guess.getAnswer(), guess.getResult(), guess.getGameId());
        return guess;
    }

    public static final class GuessMapper implements RowMapper<Guess> {
        
        @Override
        public Guess mapRow(ResultSet rs, int index) throws SQLException {
            Guess guess = new Guess();
            guess.setGameId(rs.getInt("GameId"));
            guess.setAnswer(rs.getString("Answer"));
            guess.setResult(rs.getString("Result"));
            guess.setTime(rs.getTimestamp("Time").toLocalDateTime());
            return guess;
        }
        
    }
    
}