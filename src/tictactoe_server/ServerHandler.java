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

    public DataInputStream dataInputStream;
    public PrintStream printStream;
    public String username;
    public static Vector<ServerHandler> vectorOnline = new Vector<>() ;

    public ServerHandler handler;
    

    public ServerHandler(Socket socket) {
        try {
            handler = this;
            System.out.println("Constructor of server");
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
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
                        RegisterController reg = new RegisterController();
                        int res = reg.register(jsonObject, FXMLDocumentBase.socket);
                        if (res == 1) {
                            printStream.println(res);
                            ServerHandler.vectorOnline.add(handler);
                            for (ServerHandler s : vectorOnline) {
                                System.out.println("Vector" + s);
                            }
                        }
                        break;
                    case "Login":
                        LoginController login = new LoginController(jsonObject, FXMLDocumentBase.socket);
                        int resl = login.login(jsonObject, FXMLDocumentBase.socket);
                        if (resl == 1) {
                            printStream.println(resl);
                            ServerHandler.vectorOnline.add(handler);
                            for (ServerHandler s : vectorOnline) {
                                System.out.println("Vector" + s.username);
                            }
                        }
                        break;
                    case "Mode":
                        ModeController mode = new ModeController();
                        int m = mode.mode(jsonObject, FXMLDocumentBase.socket);
                        printStream.println(m);
                        break;
                    case "AvialableList":
                        new AvaliableList(FXMLDocumentBase.socket);
                        break;
                    case "requsetPlay":
                        JSONObject value = (JSONObject) jsonObject.get("value");
                        String player2 = value.get("aganist").toString();

                        System.out.println("aganist" + player2);
                        for (ServerHandler s : vectorOnline) {
                            System.out.println("Vector" + s.username);
                            if (s.username.equals(player2)) {
                                System.out.println("username" + s.username);
                                s.printStream.println("request");
                            }
                        }
                        break;
                }
            } catch (SocketException so) {
                try {
                    dataInputStream.close();
                    printStream.close();
                    ServerHandler.vectorOnline.remove(this);
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
