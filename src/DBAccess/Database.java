/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import Module.Game;
import Module.Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.iapi.reference.ClassName;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author AM STORE
 */
public class Database {

    public static Connection con;
    public static ArrayList<Player> playerList;
    public static ArrayList<String> playerListOnline;
    public static ArrayList<Game> gameList;
    public static String url = "jdbc:derby://localhost:1527/XOGame";
    static int count;
    static int res;

    public static void connect() {
        try {
            DriverManager.registerDriver(new ClientDriver());
            con = DriverManager.getConnection(url, "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    public static void createGameTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Game (\n"
                + " gameId integer PRIMARY KEY,\n"
                + " choosenShape varchar(1) ,\n"
                + " row integer ,\n"
                + " col integer ,\n"
                + ");";
        try {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
     */
    public static void createPlayerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Player (\n"
                + " id integer PRIMARY KEY AUTO_INCREMENT,\n"
                + " userName varchar(20) NOT NULL,\n"
                + " password varchar(20) NOT NULL,\n"
                + " score integer ,\n"
                + " mode integer ,\n"
                + " gameId integer ,\n"
                + " FOREIGN KEY (gameId) REFERENCES Game(gameId) ,\n"
                + ");";
        try {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    public static void createPlayerGameTable() {
        String sql = "CREATE TABLE IF NOT EXISTS PlayerGame (\n"
                + " playerX integer ,\n"
                + " playerO integer ,\n"
                + " winner varchar(20) ,\n"
                + " date varchar(20) ,\n"
                + " time varchar(20) ,\n"
                + " PRIMARY KEY(palyerX, playerO, time, date)  ,\n"
                + " score integer ,\n"
                + " gameId integer ,\n"
                + ");";
        try {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
     */
    public static ArrayList<Player> getPlayer() throws SQLException {
        PreparedStatement pst = con.prepareStatement("select * from player");
        ResultSet res = pst.executeQuery();
        Player player;
        playerList = new ArrayList<>();
        while (res.next()) {
            player = new Player(res.getString("username"), res.getInt("score"), res.getInt("mode"));
            playerList.add(player);
        }
        return playerList;
    }

    public static ArrayList<String> getPlayerOnline() throws SQLException {
        PreparedStatement pst = con.prepareStatement("select username from player WHERE mode = 1");
        ResultSet res = pst.executeQuery();
        String player;
        playerListOnline = new ArrayList<>();
        while (res.next()) {
            player = res.getString("username");
            playerListOnline.add(player);
            System.out.println("Player online in database" + player);
        }
        return playerListOnline;
    }

    public static ArrayList<Player> checkPlayer() throws SQLException {
        PreparedStatement pst = con.prepareStatement("select * from player");
        ResultSet res = pst.executeQuery();
        Player player;
        playerList = new ArrayList<>();
        while (res.next()) {
            player = new Player(res.getString("username"), res.getString("password"));
            playerList.add(player);
        }
        return playerList;
    }

    public static String checkPassword(Player player) {
        String result = null;
        try {
            PreparedStatement pst = con.prepareStatement("SELECT password FROM player WHERE username = ? ");
            System.out.println(player.getUserName());
            pst.setString(1, player.getUserName());
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result = res.getString("password");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return result;
    }

    public static boolean checkPlayerExist(Player player) {
        boolean exist = false;
        try {
            PreparedStatement pst = con.prepareStatement("SELECT username FROM player WHERE username = ? ");
            System.out.println(player.getUserName());
            pst.setString(1, player.getUserName());
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                exist = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            exist = false;
        }
        System.out.println("boolean Update" + exist);
        return exist;
    }

    public static int insertPlayer(Player player) {
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO player VALUES (?, ?, ?, ?)");
            pst.setString(1, player.getUserName());
            pst.setString(2, player.getPassword());
            pst.setInt(3, player.getScore());
            pst.setInt(4, player.getMode());
            int res = pst.executeUpdate();
        } catch (SQLException ex) {
        }
        return res;
    }

    public static int updateScore(int id, Player player) throws SQLException {
        PreparedStatement pst = con.prepareStatement("UPDATE player SET score = ? WHERE id = ?");
        pst.setInt(1, player.getScore());
        pst.setInt(2, id);
        int res = pst.executeUpdate();
        return res;
    }

    public static int updateMode(Player player) {
        int result = 0;
        try {
            PreparedStatement pst = con.prepareStatement("UPDATE player SET mode = ? WHERE username = ? ");
            System.out.println("mode in db" + player.getMode());
            System.out.println("name in db" + player.getUserName());

            pst.setInt(1, player.getMode());
            pst.setString(2, player.getUserName());
            result = pst.executeUpdate();
            System.out.println("update mode" + result);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static int getMode(Player player) throws SQLException {
        int mode;
        String result = null;
        PreparedStatement pst = con.prepareStatement("SELECT mode from player WHERE username = ?");
        pst.setString(1, player.getUserName());
        ResultSet res = pst.executeQuery();
        if (res.next()) {
            result = res.getString("mode");
        }
        mode = Integer.parseInt(result);
        System.out.println("result in db mode" + res.getString("mode"));
        return mode;
    }

    public static void closeDB() throws SQLException {
        con.close();
    }
}
