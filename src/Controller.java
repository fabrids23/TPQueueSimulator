import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class Controller{
    private  Simulation simulation;

    @FXML
    private BarChart<Text, Integer> barChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private ListView<Text> listView;

    public Controller(){

    }

    public void initialize(){
        simulation = Main.getSimulation();
        y.setAutoRanging(false);
        y.setUpperBound(10);
        y.setLowerBound(0);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(Main.getSleep()), actionEvent -> { simulation.nextIteration();
                    if (simulation.getTime().getCurrentTime() % Main.getNumberOfIterations() == 0) {
                        updateValues(simulation.getTime().getCurrentTime());
                        updateColor(simulation);
                    }
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
            series.getData().add(new XYChart.Data("Queue" + (i + 1), simulation.getCashiers()[i].getQueue().size()));
            series.getData().add(new XYChart.Data("Queue" + (i + 1), simulation.getCashiers()[i].getQueue().size()));
        }
        barChart.getData().addAll(series);
    }
}