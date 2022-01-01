/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_games.dbAccess;

import Module.Game;
import Module.Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author AM STORE
 */
public class DB {
    public static Connection con;
    public static ArrayList<Player> playerList;
    public static ArrayList<Game> gameList;

    public static void connect() {
        try {
            DriverManager.registerDriver(new ClientDriver());
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/XOGame", "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static ArrayList<Player> getPlayer() throws SQLException {
        PreparedStatement pst = con.prepareStatement("select * from Player");
        ResultSet res = pst.executeQuery();
        Player player;
        playerList = new ArrayList<>();
        while (res.next()) {
            player = new Player(res.getString("UserName"), res.getInt("Score"), res.getBoolean("Mode"));
            playerList.add(player);
        }
        return playerList;
    }
    
    public static ArrayList<Player> checkPlayer() throws SQLException {
        PreparedStatement pst = con.prepareStatement("select * from Player");
        ResultSet res = pst.executeQuery();
        Player player;
        playerList = new ArrayList<>();
        while (res.next()) {
            player = new Player(res.getString("UserName"), res.getString("Password"));
            playerList.add(player);
        }
        return playerList;
    }
    
    public static int insertPlayer(Player player) throws SQLException{
        PreparedStatement pst = con.prepareStatement("INSERT INTO Player VALUES (?, ?, ?, ?, ?)");
        pst.setString(1, player.getUserName());
        pst.setString(2, player.getPassword());
        pst.setBoolean(3, player.getMode());
        pst.setInt(4, player.getScore());
        int res = pst.executeUpdate();
        return res;
    }
    
     public static int updateScore(int id, Player player) throws SQLException{
        PreparedStatement pst = con.prepareStatement
        ("UPDATE Player SET Score = ? WHERE Id = ?");
        pst.setInt(1, player.getScore());
        pst.setInt(2, id);
        int res = pst.executeUpdate();
        return res;
    }
     
     public static int updateMode(int id, Player player) throws SQLException{
        PreparedStatement pst = con.prepareStatement
        ("UPDATE Player SET Mode = ? WHERE Id = ?");
        pst.setBoolean(1, player.getMode());
        pst.setInt(2, id);
        int res = pst.executeUpdate();
        return res;
    }
}
