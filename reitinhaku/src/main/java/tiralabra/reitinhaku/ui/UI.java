/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhaku.ui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.InputStream;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 *
 * @author mlkul
 */
public class UI extends Application {
    
    private Stage stage;
    
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Reitinhaku");
        this.stage = stage;
        
        Canvas canvas = new Canvas(850, 550);
        GraphicsContext piirtoalusta = canvas.getGraphicsContext2D();
        
        Image tubekartta = new Image("file:london_tube.png");
   
        GridPane grid = new GridPane();
        grid.setPrefSize(900, 500);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 20, 20, 20));
        //grid.add(tubekartta, 0, 0);
        grid.add(canvas, 0, 0);
        GridPane.setColumnSpan(canvas, 7);
        //piirraTausta(canvas.getGraphicsContext2D());
        piirtoalusta.drawImage(tubekartta, 0, 0);
        
        Label lahtoTeksti = new Label("Mistä?");
        TextField lahtoKentta = new TextField();
        Label maaliTeksti = new Label("Mihin?");
        TextField maaliKentta = new TextField();
        
        //HBox valintaRyhma = new HBox();
        ToggleGroup valintaNapit = new ToggleGroup();
        RadioButton rb_AStar = new RadioButton();
        RadioButton rb_Fringe = new RadioButton();
        rb_AStar.setToggleGroup(valintaNapit);
        rb_AStar.setText("A*");
        rb_Fringe.setToggleGroup(valintaNapit);
        rb_Fringe.setText("Fringe");
        //valintaRyhma.getChildren().add(rb_AStar);
        //valintaRyhma.getChildren().add(rb_Fringe);
        
        Button hakuNappi = new Button("Hae");
//        Button maaliButton = new Button("Mihin?");
//        GridPane.setHalignment(lahtoButton, HPos.CENTER);
//        GridPane.setHalignment(maaliButton, HPos.CENTER);
        grid.add(lahtoTeksti, 0, 1);
        grid.add(lahtoKentta, 1, 1);
        grid.add(maaliTeksti, 2, 1);
        grid.add(maaliKentta, 3, 1);
        grid.add(rb_AStar, 4, 1);
        grid.add(rb_Fringe, 5, 1);
        grid.add(hakuNappi, 6, 1);
        //GridPane.setHalignment(rb_AStar, HPos.CENTER);
        
        
        
        
        Scene scene = new Scene(grid, 1000, 600);
       
        stage.setScene(scene);
        stage.show();
           
               
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
