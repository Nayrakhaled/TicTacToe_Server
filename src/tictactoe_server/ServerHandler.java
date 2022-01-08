/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_server;

import Controller.AvaliableList;
import Controller.RegisterController;
import Controller.LoginController;
import Controller.ModeController;
import Controller.RequestPlayController;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author AM STORE
 */
public class ServerHandler extends Thread {

    DataInputStream dataInputStream;
    PrintStream printStream;
    static Vector<ServerHandler> vector = new Vector<ServerHandler>();
    RegisterController regLog;

    public ServerHandler(Socket socket) {
        try {
            System.out.println("Constructor of server");
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            ServerHandler.vector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Thread of server");
                String str = dataInputStream.readLine();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(str);
                String check = String.valueOf(jsonObject.get("Key"));
                switch (check) {
                    case "Register":
                        new RegisterController(jsonObject, FXMLDocumentBase.socket);
                        break;
                    case "Login":
                        new LoginController(jsonObject, FXMLDocumentBase.socket);
                        break;
                    case "Mode":
                        new ModeController(jsonObject, FXMLDocumentBase.socket);
                        break;
                    case "AvialableList":
                        new AvaliableList(FXMLDocumentBase.socket);
                        break;
                    case "requsetPlay":
                        new RequestPlayController(jsonObject, FXMLDocumentBase.socket);
                        break;
                }
            } catch (SocketException so) {
                try {
                    dataInputStream.close();
                    printStream.close();
                    ServerHandler.vector.remove(this);
                } catch (IOException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
