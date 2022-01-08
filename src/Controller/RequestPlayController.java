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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author AM STORE
 */
public class RequestPlayController {

    Player player;
    boolean isBusy;
    PrintStream printStream;

    public RequestPlayController(JSONObject obj, Socket socket) {
        player = new Player();
        try {
            printStream = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBAccess.Database.connect();
        JSONObject value = (JSONObject) obj.get("value");
        System.out.println("aganist" + value.get("aganist").toString());
        player.setUserName(value.get("aganist").toString());
        isBusy = DBAccess.Database.checkBusyPlayer(player);
        printStream.println(isBusy);
        
        
        
    }
}
