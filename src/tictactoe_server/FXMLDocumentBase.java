package tictactoe_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class FXMLDocumentBase extends AnchorPane {

    protected final CategoryAxis categoryAxis;
    protected final NumberAxis numberAxis;
    protected final LineChart chart;
    protected final Button serverBtn;
    ServerSocket server;
    public static Socket socket;
    boolean state = false;

    public FXMLDocumentBase() {

        categoryAxis = new CategoryAxis();
        numberAxis = new NumberAxis();
        chart = new LineChart(categoryAxis, numberAxis);
        serverBtn = new Button();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        categoryAxis.setLabel("Online");
        categoryAxis.setSide(javafx.geometry.Side.BOTTOM);

        numberAxis.setLabel("Offline");
        numberAxis.setSide(javafx.geometry.Side.LEFT);
        chart.setLayoutX(37.0);
        chart.setLayoutY(40.0);
        chart.setPrefHeight(284.0);
        chart.setPrefWidth(498.0);
        chart.setTitle("online_offline");

        serverBtn.setLayoutX(274.0);
        serverBtn.setLayoutY(331.0);
        serverBtn.setMnemonicParsing(false);
        serverBtn.setText("Start");

        getChildren().add(chart);
        getChildren().add(serverBtn);

        serverBtn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("handle of server");
                if (state == false) {
                    try {
                        state = true;
                        serverBtn.setText("Stop");
                        server = new ServerSocket(63000);
                        /*
                         System.out.println("run of server");
                          
                         */

                        System.out.println("before run of server");

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (true) {
                                        socket = server.accept();
                                        new ServerHandler(socket);
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }).start();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    try {
                        System.out.println("Server Close");
                        state = false;
                        serverBtn.setText("Start");
                        socket.close();
                        server.close();
                    } catch (SocketException e) {
                        e.printStackTrace();
                        try {
                            socket.close();
                            server.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            // Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        //Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
    }
}
