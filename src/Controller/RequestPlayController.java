/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Module.Player;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author AM STORE
 */
public class RequestPlayController {

    private int isBusy;

    public JSONObject sendRequest(String message) {

        JSONObject jSONObject = null;
        try {
            System.out.println("message in server request " + message);
            JSONObject obj = new JSONObject(message);
            JSONObject value = (JSONObject) obj.get("value");
            String aganist = value.get("aganist").toString();
            String player = value.get("player").toString();

            DBAccess.Database.connect();
            isBusy = DBAccess.Database.checkBusyPlayer(aganist);
            System.out.println("exist" + isBusy);

            jSONObject = new JSONObject();
            jSONObject.put("Key", "requestPlay");
            jSONObject.put("player", player);
            jSONObject.put("aganist", aganist);
            jSONObject.put("response", isBusy);
            System.out.println("json of request " + jSONObject.toString());
            try {
                DBAccess.Database.closeDB();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(RequestPlayController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jSONObject;
    }

    public JSONObject responseRequest(String message) {
        JSONObject object = null;
        try {
            JSONObject obj = new JSONObject(message);
            String response = obj.get("response").toString();

            if (response.equals("accept")) {
                DBAccess.Database.connect();
                Player player = new Player();
                player.setUserName(obj.get("vs").toString());
                player.setBusy(1);
                isBusy = DBAccess.Database.updateBusy(player);
                System.out.println("exist" + isBusy);
                if (isBusy != 0) {
                    object = new JSONObject();
                    object.put("Key", "responseRequest");
                    object.put("vs", player.getUserName());
                    object.put("response", "1");
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(RequestPlayController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RequestPlayController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return object;
    }

}
