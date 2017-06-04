package sample;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {

    private double[] xValues = new double[] {-3,-2,0,1};
    private double[] yValues = new double[] {0,-2,4,1};
    private double[] l_x = new double[]{-3.5,-3.0,-2.0,-1.0,0.5,1.0,1.2};



    public double interp_value(double x,String type){
        Interpolation tt = new Interpolation(xValues,yValues,4);
        double res = 0.0;
        if(type.equals("lag")){
            res = tt.lagrangePolynomial(x);
        }
        if(type.equals("new")){
            res = tt.newtonPonynomial(x);
        }

        return res;
    }
    @FXML
    Label sqrt_label;

    @FXML
    Label sr_approx;

    @FXML
    LineChart<String, Double> lineChart;

    @FXML
    TableView<PutNumbersTable> sqrt_table;
    @FXML
    TableColumn<PutNumbersTable,Double> s_Xi;
    @FXML
    TableColumn<PutNumbersTable,Double> s_Yi;


    public void sqrt_btn(ActionEvent event) {
        Interpolation t_t = new Interpolation(xValues,yValues,4);
        int choise = cb.getSelectionModel().getSelectedIndex();

        ObservableList<PutNumbersTable> items_sqrt = FXCollections.observableArrayList();
        if(choise == 1){
            items_sqrt.add(new PutNumbersTable(l_x[0],t_t.middleSqrt(l_x[0],choise)));
            items_sqrt.add(new PutNumbersTable(l_x[xValues.length],t_t.middleSqrt(l_x[xValues.length],choise)));
        }
        else {
        for(int i = 0; i < l_x.length;i++){

            items_sqrt.add(new PutNumbersTable(l_x[i],t_t.middleSqrt(l_x[i],choise)));
        }
        }
        sqrt_table.itemsProperty().setValue(items_sqrt);


        s_Xi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PutNumbersTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<PutNumbersTable, Double> param) {
                return new SimpleDoubleProperty(param.getValue().getR1()).asObject();            }
        });
        s_Yi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PutNumbersTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<PutNumbersTable, Double> param) {
                return new SimpleDoubleProperty(param.getValue().getR2()).asObject();            }
        });

        XYChart.Series<String, Double> series3 = new XYChart.Series<String, Double>();

        if(choise == 1) {
            series3.getData().add(new XYChart.Data<String, Double>("" + l_x[0],t_t.middleSqrt(l_x[0],choise)));
            series3.getData().add(new XYChart.Data<String, Double>("" + l_x[xValues.length],t_t.middleSqrt(l_x[xValues.length],choise)));

        }
        else{
            for(int i =0;i<l_x.length;i++){
            series3.getData().add(new XYChart.Data<String, Double>("" + l_x[i],t_t.middleSqrt(l_x[i],choise)));
        }
        }
        lineChart.getData().add(series3);

        sqrt_table.setVisible(true);
        sqrt_label.setVisible(true);
        double Delta = 0;
        for(int i =0;i<xValues.length;i++){
            Delta += (yValues[i] - t_t.middleSqrt(yValues[i],choise))*(yValues[i] - t_t.middleSqrt(xValues[i],choise));
        }
        double S = Math.sqrt(Delta/(xValues.length+1));
        sr_approx.setText("S = " + S);


    }


    public void btn(ActionEvent event){

        XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();

        for(int i =0;i<l_x.length;i++){
            series.getData().add(new XYChart.Data<String, Double>("" + l_x[i],interp_value(l_x[i],"lag")));
        }

            lineChart.getData().add(series);
    }

    public void btn_newton(ActionEvent event){
        XYChart.Series<String, Double> series2 = new XYChart.Series<String, Double>();
            for (int i = 0; i < l_x.length; i++) {
                series2.getData().add(new XYChart.Data<String, Double>(String.valueOf(l_x[i]), interp_value(l_x[i], "new")));
            }

        lineChart.getData().add(series2);

    }

    public void btn_clear(ActionEvent event){
        lineChart.getData().retainAll();
        lb.setVisible(false);
        lagr_table.setVisible(false);
        newton_label.setVisible(false);
        newton_table.setVisible(false);
        sqrt_table.setVisible(false);
        sqrt_label.setVisible(false);
        sr_approx.setText("");

    }

    @FXML
    ChoiceBox cb = new ChoiceBox();


    @FXML
    Label lb;
    @FXML
    private Button l_btn;
    @FXML
    private Button n_btn;
    @FXML
    private Button s_btn;
    @FXML
    private Button clear_btn;


    @FXML
    private Label newton_label;

    @FXML
    private TableView<PutNumbersTable> newton_table;
    @FXML
    TableColumn<PutNumbersTable,Double> n_Xi;
    @FXML
    TableColumn<PutNumbersTable,Double> n_Yi;



    @FXML
    TableView<PutNumbersTable> lagr_table;
    @FXML
    TableColumn<PutNumbersTable,Double> l_Xi;
    @FXML
    TableColumn<PutNumbersTable,Double> l_Yi;

    @FXML
    TableView<PutNumbersTable> table;

    @FXML
    TableColumn<PutNumbersTable,Double> Xi;
    @FXML
    TableColumn<PutNumbersTable,Double> Yi;

    public void initialize(){

        cb.getItems().addAll(0,1,2);
        ObservableList<PutNumbersTable> items= FXCollections.observableArrayList(new PutNumbersTable(-3,0),new PutNumbersTable(-2,-2),
                new PutNumbersTable(0,4), new PutNumbersTable(1,1));

table.itemsProperty().setValue(items);

Xi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PutNumbersTable, Double>, ObservableValue<Double>>() {
    @Override
    public ObservableValue<Double> call(TableColumn.CellDataFeatures<PutNumbersTable, Double> param) {
        return new SimpleDoubleProperty(param.getValue().getR1()).asObject();
    }
});

        Yi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PutNumbersTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<PutNumbersTable, Double> param) {
                return new SimpleDoubleProperty(param.getValue().getR2()).asObject();
            }
        });

        Interpolation it = new Interpolation(xValues,yValues,4);
        ObservableList<PutNumbersTable> items_l = FXCollections.observableArrayList();

        for(int i = 0; i < l_x.length;i++){
            items_l.add(new PutNumbersTable(l_x[i],it.lagrangePolynomial(l_x[i])));
        }

        lagr_table.itemsProperty().setValue(items_l);


        l_Xi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PutNumbersTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<PutNumbersTable, Double> param) {
                return new SimpleDoubleProperty(param.getValue().getR1()).asObject();            }
        });
        l_Yi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PutNumbersTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<PutNumbersTable, Double> param) {
                return new SimpleDoubleProperty(param.getValue().getR2()).asObject();            }
        });

        Interpolation inter = new Interpolation(new double[]{0,1,2,3,4},new double[]{1,2,1,0,4},5);
        ObservableList<PutNumbersTable> items_newton = FXCollections.observableArrayList();
        for(int i = 0; i < l_x.length;i++){
            items_newton.add(new PutNumbersTable(l_x[i],it.newtonPonynomial(l_x[i])));
        }
        newton_table.itemsProperty().setValue(items_l);


        n_Xi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PutNumbersTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<PutNumbersTable, Double> param) {
                return new SimpleDoubleProperty(param.getValue().getR1()).asObject();            }
        });
        n_Yi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PutNumbersTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<PutNumbersTable, Double> param) {
                return new SimpleDoubleProperty(param.getValue().getR2()).asObject();            }
        });


        n_btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newton_label.setVisible(true);
                newton_table.setVisible(true);

            }
        });

        l_btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                    lb.setVisible(true);
                    lagr_table.setVisible(true);

            }
        });
    }
}
