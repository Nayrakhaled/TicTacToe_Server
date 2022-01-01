package tictactoe_server;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

public class FXMLDocumentBase extends AnchorPane {

    protected final ToggleButton togglebtn;
    protected final CategoryAxis categoryAxis;
    protected final NumberAxis numberAxis;
    protected final LineChart chart;

    public FXMLDocumentBase() {

        togglebtn = new ToggleButton();
        categoryAxis = new CategoryAxis();
        numberAxis = new NumberAxis();
        chart = new LineChart(categoryAxis, numberAxis);

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        togglebtn.setLayoutX(269.0);
        togglebtn.setLayoutY(337.0);
        togglebtn.setMnemonicParsing(false);
        togglebtn.setPrefHeight(25.0);
        togglebtn.setPrefWidth(76.0);
        togglebtn.setText("start");

        categoryAxis.setLabel("Online");
        categoryAxis.setSide(javafx.geometry.Side.BOTTOM);

        numberAxis.setLabel("Offline");
        numberAxis.setSide(javafx.geometry.Side.LEFT);
        chart.setLayoutX(37.0);
        chart.setLayoutY(40.0);
        chart.setPrefHeight(284.0);
        chart.setPrefWidth(498.0);
        chart.setTitle("online_offline");

        getChildren().add(togglebtn);
        getChildren().add(chart);

    }
}
