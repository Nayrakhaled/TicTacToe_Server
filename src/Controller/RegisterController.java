/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Module.Player;
import java.io.IOException;
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
public class RegisterController {

    private Player player;
    private boolean exist;
    private int inserted;
    private PrintStream printStream;

    public RegisterController(JSONObject obj, Socket socket) {
        player = new Player();
        try {
            printStream = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject value = (JSONObject) obj.get("value");
        player.setUserName(value.get("user").toString());
        player.setPassword(value.get("pass").toString());

        DBAccess.Database.connect();
        exist = DBAccess.Database.checkPlayerExist(player);

        System.out.println(exist);
        if (exist == false) {
            System.out.println("In Exist false");
            player.setMode(1);
            player.setScore(0);
            inserted = DBAccess.Database.insertPlayer(player);
            if (inserted == 0) { // insert
                System.out.println("insert");
                printStream.println(1);
            } else { // insert failed
                System.out.println("not insert");
                printStream.println(0);
            }
        } else { // user exist
                System.out.println("Exist");
                printStream.println(2);
        }
        try {
            DBAccess.Database.closeDB();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
