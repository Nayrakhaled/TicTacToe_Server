/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Module.Player;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import tictactoe_server.ServerHandler;

/**
 *
 * @author AM STORE
 */
public class LoginController {

    private Player player;
    private boolean exist;
    private int existed;
    private int result;

    public LoginController(JSONObject obj, Socket socket) {
        player = new Player();

    }

    public int login(JSONObject obj, Socket socket) {
        JSONObject value = (JSONObject) obj.get("value");
        player.setUserName(value.get("user").toString());
        player.setPassword(value.get("pass").toString());

        DBAccess.Database.connect();
        exist = DBAccess.Database.checkPlayerExist(player);
        System.out.println("exist" + exist);

        if (exist) {// exist   
            String pass = DBAccess.Database.checkPassword(player);
            System.out.println("pass" + pass);
            if (pass.equals(player.getPassword())) {  // check password
                result = 1;
                ServerHandler.vectorOnline.get(0).username = player.getUserName();
                System.out.println("Server==: " + "Here");
            } else {
                result = 0; // password wrong 
            }
        } else { //  not exist
            result = -1;
        }
        try {
            DBAccess.Database.closeDB();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
