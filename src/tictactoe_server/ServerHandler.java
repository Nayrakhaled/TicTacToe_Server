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
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author AM STORE
 */
public class ServerHandler extends Thread {

    public DataInputStream dataInputStream;
    public PrintStream printStream;
    public String username;
    public static Vector<ServerHandler> vectorOnline = new Vector<>();
    public ServerHandler handler;

    public ServerHandler(Socket socket) {
        try {

            handler = this;
            System.out.println("Constructor of server");
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            th.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Thread thread = new Thread() {
        public void run() {
            while (true) {
                new AvaliableList(FXMLDocumentBase.socket);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    Thread th = new Thread() {
        public void run() {
            while (true) {
                try {
                    System.out.println("Thread of server");
                    String message = dataInputStream.readLine();
                    JSONObject jSONObject = new JSONObject(message); 
                    switch (jSONObject.getString("Key")) {
                        case "Register":
                            RegisterController reg = new RegisterController();
                            int res = reg.register(message);
                            System.out.println("result register server" + res);
                            if (res == 1) {
                                JSONObject jsono = (JSONObject) jSONObject.get("value");
                                username = jsono.get("user").toString();
                                ServerHandler.vectorOnline.add(handler);
                                for (ServerHandler handler : vectorOnline) {
                                    System.out.println("register server" + handler.username);
                                    if (handler.username.equals(username)) {
                                        handler.printStream.println(res);
                                    }
                                }
                                thread.start();
                            }
                            break;
                        case "Login":
                            LoginController login = new LoginController();
                            JSONObject obj = login.login(message);
                            String response = obj.get("response").toString();
                            System.out.println("result of login in server" + response);
                            if (response.equals("1")) {
                                JSONObject jsono = (JSONObject) jSONObject.get("value");
                                username = jsono.get("user").toString();
                                ServerHandler.vectorOnline.add(handler);
                                for (ServerHandler handler : vectorOnline) {
                                    System.out.println("register server" + handler.username);
                                    if (handler.username.equals(username)) {
                                        System.out.println("json server" + obj);
                                        handler.printStream.println(obj);
                                    }
                                }
                                thread.start();
                            }
                            break;
                    
                        case "requsetPlay":
                            //String player2 = value.get("aganist").toString();

//                            System.out.println("aganist" + player2);
//                            for (ServerHandler s : vectorOnline) {
//                                System.out.println("Vector" + s.username);
//                                if (s.username.equals(player2)) {
//                                    System.out.println("username" + s.username);
//                                    s.printStream.println("request");
//                                } else {
//                                    s.printStream.println("request failed");
//                                }
//                            }
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
                }catch (JSONException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    };

}
