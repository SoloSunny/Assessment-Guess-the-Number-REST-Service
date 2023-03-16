/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.models;

import java.util.Objects;

/**
 *
 * @author solomon
 */
public class Game {
    
    private int gameId;
    private String answer;
    private boolean GameInProgress;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isGameInProgress() {
        return GameInProgress;
    }

    public void setGameInProgress(boolean GameInProgress) {
        this.GameInProgress = GameInProgress;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.gameId;
        hash = 79 * hash + Objects.hashCode(this.answer);
        hash = 79 * hash + (this.GameInProgress ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.GameInProgress != other.GameInProgress) {
            return false;
        }
        return Objects.equals(this.answer, other.answer);
    }

}