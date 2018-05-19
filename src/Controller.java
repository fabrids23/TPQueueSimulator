import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Controller{
    //TODO QUE SI LA CAJA ESTA VACIA EL NOMBRE SALGA EN ROJO
    private  Simulation simulation;

    @FXML
    private BarChart<Text, Integer> barChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

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

    public void updateValues(int iteration){
            updateDurationOnScreen(iteration);
            updateQueues(simulation.getNumberOfQueues());
    }

    public void updateDurationOnScreen(int duration){
        Text text = new Text("Unidades de tiempo: "+ duration);
        VBox vBox = new VBox(text);
        vBox.sceneToLocal(200, 300);
    }

    public void updateQueues(int numberOfQueues){
        XYChart.Series series = new XYChart.Series();
        barChart.getData().clear();
        for (int i = 0; i < numberOfQueues; i++) {
            if(simulation.getCashiers()[i].getQueue().size()>y.getUpperBound()){
                y.setUpperBound(y.getUpperBound()*2);
            }
            if(simulation.getCashiers()[i].isLazy()) {
                Text text = new Text("Queue" + (i + 1));
                text.setFill(Color.RED);
                series.getData().add(new XYChart.Data("Queue" + (i + 1), simulation.getCashiers()[i].getQueue().size()));
            }else{
                Text text = new Text("Queue" + (i + 1));
                text.setFill(Color.GREEN);
                series.getData().add(new XYChart.Data("Queue" + (i + 1), simulation.getCashiers()[i].getQueue().size()));
            }
        }
        barChart.getData().addAll(series);
    }
}