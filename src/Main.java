import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

    private static Simulation simulation;
    private static int sleep;
    private static int numberOfIterations;

    public static void main(String[] args){
        try{
            String fileIn = args[0];
            String fileOut = args[1];
            if(args.length > 2) {
                String numberOfIterationsS = args[2];
                if (!numberOfIterationsS.substring(0, 1).equals("-")) {
                    throw new IllegalArgumentException();
                }
                numberOfIterations = Integer.parseInt(numberOfIterationsS.substring(1));
                String sleepS = args[3];
                sleep = Integer.parseInt(sleepS);
                simulation = new Simulation(fileIn, fileOut);
                launch(args);
            }else{
                simulation = new Simulation(fileIn, fileOut);
                for (int i = 0; i < simulation.getDuration(); i++) {
                    simulation.nextIteration();
                }
            }
            simulation.getStats().saveData();
            System.exit(0);
        }catch(IllegalArgumentException e){
            System.out.println("Illegal argumensts.");
            System.exit(1);
        }
    }

    public static Simulation getSimulation(){
        return simulation;
    }

    public static int getSleep(){
        return sleep;
    }

    public static int getNumberOfIterations() {
        return numberOfIterations;
    }

    @Override
    public void start(Stage primaryStage){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("resources/View.fxml"));
            AnchorPane page = loader.load();
            primaryStage.setTitle("Queue viewer");
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
