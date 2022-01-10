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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import tictactoe_server.ServerHandler;

/**
 *
 * @author AM STORE
 */
public class RequestPlayController {

    Player player;
    private int isBusy;
    private String result;

    public RequestPlayController() {
        player = new Player();

    }

    public String request(JSONObject obj, Socket socket, Vector<ServerHandler> vector) {
        DBAccess.Database.connect();
        
//        player.setUserName(value.get("aganist").toString());
//        isBusy = DBAccess.Database.checkBusyPlayer(player);
        return result;
    }
}
