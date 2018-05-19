import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Controller{
    //TODO QUE SI LA CAJA ESTA VACIA EL NOMBRE SALGA EN ROJO
    private  Simulation simulation;

    @FXML
    private BarChart<Text, Integer> barChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private ListView<Text> listView;

    @FXML
    private ListView ListOperaciones;

    public Controller(){

    }

    public void initialize(){
        simulation = Main.getSimulation();
        y.setAutoRanging(false);
        y.setUpperBound(10);
        y.setLowerBound(0);
            //Create a timeline in which the supermarket is updated thenumber of iterations per cycle
            // each (ms) milliseconds
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(Main.getSleep()),
                    actionEvent -> {
                        //for (int i = 0; i < simulation.getDuration(); i++) {
                            simulation.nextIteration();
                                if (simulation.getTime().getCurrentTime() % Main.getNumberOfIterations() == 0) {
                                    updateValues(simulation.getTime().getCurrentTime());
                                    updateColor(simulation);
                            /*
                            if (cashiers[i].isIdle()) {
                                cashierState[i].setFill(IDLE);
                            } else {
                                cashierState[i].setFill(BUSY);
                            }
                            */
                            }
                        //}
                    }
            ));
        timeline.setCycleCount(simulation.getDuration());
        timeline.play();
    }

    private void updateColor(Simulation simulation) {
        ObservableList<Text> observableList=FXCollections.observableArrayList();
        for (int i = 0; i < simulation.getNumberOfQueues(); i++) {
            if(!simulation.getCashiers()[i].isLazy()) {
                Text text = new Text("Queue" + (i + 1));
                text.setFill(Color.RED);
                observableList.add(text);
            }else{
                Text text = new Text("Queue" + (i + 1));
                text.setFill(Color.GREEN);
                observableList.add(text);
            }
        }

        listView.setItems(observableList);

    }

    public void updateValues(int iteration){
            updateDurationOnScreen(iteration);
            updateQueues(simulation.getNumberOfQueues());
    }

    public void updateDurationOnScreen(int iteracion){
        ObservableList<Text> observableList=FXCollections.observableArrayList();
        Text text= new Text("Iteracion Numero: "+iteracion);
        text.setTextAlignment(TextAlignment.CENTER);
        observableList.add(text);
        ListOperaciones.setItems(observableList);




    }

    public void updateQueues(int numberOfQueues){
        XYChart.Series series = new XYChart.Series();
        barChart.getData().clear();
        for (int i = 0; i < numberOfQueues; i++) {
            if(simulation.getCashiers()[i].getQueue().size()>y.getUpperBound()){
                y.setUpperBound(y.getUpperBound()*2);
            }
            series.getData().add(new XYChart.Data("Queue" + (i + 1), simulation.getCashiers()[i].getQueue().size()));
            series.getData().add(new XYChart.Data("Queue" + (i + 1), simulation.getCashiers()[i].getQueue().size()));
            }
        barChart.getData().addAll(series);
    }
}