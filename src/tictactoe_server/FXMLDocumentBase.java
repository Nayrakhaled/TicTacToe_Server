package tictactoe_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FXMLDocumentBase extends AnchorPane {

    protected final Button serverBtn;
    protected final PieChart pieChart;

    ServerSocket server;
    public Socket socket;
    boolean state = false;
    private Stage stage;

    public FXMLDocumentBase(Stage stage) throws SQLException {

        serverBtn = new Button();
        pieChart = new PieChart();
        this.stage = stage;
        
        
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        serverBtn.setLayoutX(258.0);
        serverBtn.setLayoutY(309.0);
        serverBtn.setMnemonicParsing(false);
        serverBtn.setPrefHeight(25.0);
        serverBtn.setPrefWidth(62.0);
        serverBtn.setText("Start");
        serverBtn.setFont(new Font("System Bold", 14.0));

        pieChart.setLayoutX(170.0);
        pieChart.setLayoutY(21.0);
        pieChart.setPrefHeight(254.0);
        pieChart.setPrefWidth(238.0);

        getChildren().add(serverBtn);
        getChildren().add(pieChart);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Online", 14),
                    new PieChart.Data("Offline", 2)
        );
        

        pieChart.setClockwise(true);

        //Setting the length of the label line 
        pieChart.setLabelLineLength(100);

        //Setting the labels of the pie chart visible  
        pieChart.setLabelsVisible(true);

        //Setting the start angle of the pie chart  
        pieChart.setStartAngle(180);

        
        pieChart.setData(pieChartData);
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
