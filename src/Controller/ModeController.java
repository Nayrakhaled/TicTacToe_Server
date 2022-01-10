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
public class ModeController {

    private Player player;
    private PrintStream PrintStream;
    private int result;

    public ModeController() {

        player = new Player();
    }

    public int mode(JSONObject obj, Socket socket) {
        try {
            JSONObject value = (JSONObject) obj.get("value");
            player.setUserName(value.get("user").toString());
            System.out.println("user in mode server" + player.getUserName());
            DBAccess.Database.connect();
            int mode = DBAccess.Database.getMode(player);
            System.out.println("mode in controller" + mode);
            if (mode == 1) {
                player.setMode(0);
                System.out.println("mode if == 1  " + player.getMode());
            } else {
                player.setMode(1);
                System.out.println("mode else " + player.getMode());
            }
            result = DBAccess.Database.updateMode(player);

            if (result != 0) {
                result = 1;
            } else {
                result = 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
