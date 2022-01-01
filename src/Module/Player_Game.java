/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.sql.Date;
import java.sql.Timestamp;


/**
 *
 * @author AM STORE
 */
public class Player_Game {
    
    private String playerX, playerO, winner;
    private Date date;
    private Timestamp time;
    
    
    public Player_Game(){}
    public Player_Game(String playerX, String playerO, String winner, Date date, Timestamp time){
        this.playerO = playerO;
        this.playerX = playerX;
        this.winner  = winner;
        this.date = date;
        this.time = time;
    }
    
    
    public void setPlayerX(String palyerX){ this.playerX = playerX;}
    public void setPlayerO(String palyerO){ this.playerO = playerO;}
    public void setWinner(String winner){ this.winner = winner;}
    public void setDate(Date date){ this.date = date;}
    public void setTime(Timestamp time){ this.time = time;}
    
    public String getPalyerX(){return playerX;}
    public String getPlayerO(){return playerO;}
    public String getWinner(){return winner;}
    public Date getDate(){return date;}
    public Timestamp getTime(){return time;}
    
}
