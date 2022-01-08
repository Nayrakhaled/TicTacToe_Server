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

/**
 *
 * @author AM STORE
 */
public class LoginController {

    private Player player;
    private boolean exist;
    private int existed;
    private PrintStream printStream;

    public LoginController(JSONObject obj, Socket socket) {
        player = new Player();
        try {
            printStream = new PrintStream(socket.getOutputStream());

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                printStream.println(1);
                System.out.println("Server==: " + "Here");
            } else {
                printStream.println(0); // password wrong 
            }

        } else { //  not exist
            printStream.println(-1);
        }

        try {
            printStream.flush();
            DBAccess.Database.closeDB();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
