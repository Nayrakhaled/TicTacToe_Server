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
    Socket socket;

    public ServerHandler(Socket socket) {
        try {
            this.socket = socket;
            handler = this;
            System.out.println("Constructor of server");
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            System.out.println("Thread of server");
            authenticate();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Thread thread = new Thread() {
        public void run() {
            boolean running = true;
            while (running) {
                new AvaliableList(socket);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
//                    running = false;
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    private void authenticate() {
        try {
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
                        th.start();
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
                            //  System.out.println("register server" + handler.username);
                            if (handler.username.equals(username)) {
                                System.out.println("json server" + obj);
                                handler.printStream.println(obj);
                            }
                        }
                        thread.start();
                        th.start();
                    }

                    break;
            }
        } catch (JSONException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    Thread th = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    String message = dataInputStream.readLine();
                    JSONObject jSONObject = new JSONObject(message);
                    System.out.println(".run()  " + message);
                    switch (jSONObject.getString("Key")) {
                        case "requsetPlay":
                            RequestPlayController request = new RequestPlayController();
                            JSONObject object = request.sendRequest(message);
                            JSONObject value = (JSONObject) jSONObject.get("value");
                            String player2 = value.get("aganist").toString();
                            System.out.println("aganist" + player2);
                            System.out.println("aganist" + jSONObject.toString());
                            for (ServerHandler s : vectorOnline) {
                                System.out.println("Vector" + s.username);
                                if (s.username.equals(player2)) {
                                    System.out.println("username" + s.username);
                                    s.printStream.println(object);
                                }
                            }
                            break;
                        case "responsePlay":
                            RequestPlayController req = new RequestPlayController();
                            JSONObject ob = req.responseRequest(message);
                            System.out.println("response play " + message);
                            String player = jSONObject.get("vs").toString();
                            for (ServerHandler s : vectorOnline) {
                                System.out.println("Vector" + s.username);
                                if (s.username.equals(player)) {
                                    System.out.println("username" + s.username);
                                    s.printStream.println(ob);
                                }
                            }
                            break;
                        case "logout":
                            LoginController logout = new LoginController();
                            JSONObject objj = logout.login(message);
                            System.out.println("response play " + message);

                            break;
                    }// switch 
                } catch (SocketException sx) {
                    try {
                        dataInputStream.close();
                        printStream.close();
                        socket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    };

}
