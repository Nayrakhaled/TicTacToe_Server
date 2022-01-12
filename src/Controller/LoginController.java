/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Module.Player;

import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author AM STORE
 */
public class LoginController {

    private Player player;
    private boolean exist;
    private int existed;
    private String result;

    public LoginController() {
        player = new Player();

    }

    public JSONObject login(String message) {
        JSONObject jSONObject = null; 
        try {
            JSONObject obj = new JSONObject(message);
            JSONObject value = (JSONObject) obj.get("value");
            player.setUserName(value.get("user").toString());
            player.setPassword(value.get("pass").toString());
            
            DBAccess.Database.connect();
            exist = DBAccess.Database.checkPlayerExist(player);
            System.out.println("exist" + exist);
            
            if (exist) {// exist
                String pass = DBAccess.Database.checkPassword(player);
                System.out.println("pass" + pass);
                if (pass.equals(player.getPassword())) {
                    // check password
                    new ModeController().mode(obj);
                    result = "1";
                    System.out.println("Server==: " + "Here");
                } else {
                    result = "0"; // password wrong
                }
            } else { //  not exist
                result = "-1";
            }
            
            jSONObject = new JSONObject();
            jSONObject.put("Key", "login");
            jSONObject.put("response", result);
            System.out.println("json of login " + jSONObject.toString());
            try {
                DBAccess.Database.closeDB();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (JSONException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jSONObject;
    }

}
