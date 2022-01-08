/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Module.Player;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author AM STORE
 */
public class AvaliableList {

    ArrayList<String> playerOnline;
    private PrintStream printStream;

    public AvaliableList(Socket socket) {

        try {
            try {
                printStream = new PrintStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            DBAccess.Database.connect();
            playerOnline = new ArrayList<>();
            playerOnline = DBAccess.Database.getPlayerOnline();
            if (playerOnline.size() != 0) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Key", "List");
                jsonObject.put("AvaliableList", playerOnline);
                System.out.println("json server online" + jsonObject);
                printStream.println(jsonObject);
                
            } else {
                printStream.println(0);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AvaliableList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
