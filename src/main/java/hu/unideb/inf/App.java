package hu.unideb.inf;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.io.IOException;

public class App extends Application {
    Stage WINDOW;
    Scene scene1,scene2,scene3;
    String character1 = "Player 1";
    String character2="Player 2";

    String key1;
    String key2;

    Label char1 = new Label();
    Label char2 = new Label();
    TextField player1= new TextField();
    TextField player2= new TextField();

    private String currentPlayer = character1;
    private Cell[][] cell = new Cell [3][3];

    private Label statusMsg = new Label();
    private Label statusMsg1 = new Label();
    private Label statusMsg2 = new Label();

    //to display first player must play

    @Override
    public void start(Stage primaryStage) throws IOException{
        WINDOW = primaryStage;
        Label label1 = new Label("Tic Tac Toe");
        label1.setAlignment(Pos.CENTER);
        label1.setPadding(new Insets(20, 0, 30, 180));

        Label lblName = new Label("Player 1:");
        lblName.setMinWidth(75);
        lblName.setPadding(new Insets(5, 10, 0, 65));
        player1.setMinWidth(200);

        Label lblName2 = new Label("Player 2:");
        lblName2.setMinWidth(75);
        lblName2.setPadding(new Insets(5, 10, 0, 60));
        player2.setMinWidth(200);

        Button button = new Button("save");
        //log.info(player1+" saved");
        Button button2 = new Button("save");
        //log.info(player2+" saved");

        HBox pane3 = new HBox(2, lblName, player1, button);
        HBox pane4 = new HBox(2, lblName2, player2, button2);

        Label resp1 = new Label();
        Label resp2 = new Label();

        resp1.setTextFill(Color.RED);
        resp2.setTextFill(Color.RED);
        //HERREEEE
        button.setOnAction(e -> {
            key1 = player1.getText();
            char1.setText("Player 1 name is " + key1);
            char1.setPadding(new Insets(5, 10, 0, 60));
        });

        button2.setOnAction(e -> {
            key2 = player2.getText();
            char2.setText("Player 2 name is " + key2);
            char2.setPadding(new Insets(5, 10, 0, 60));
        });

        Label playerName1 = new Label(player1.getText());
        Label playerName2 = new Label(player2.getText());

        VBox layout = new VBox(5);
        layout.getChildren().addAll(label1, pane3, char1, char2, pane4,resp1,resp2);
        layout.setSpacing(5);
        layout.setPadding(new Insets(10, 10, 0, 10));

        Button button1 = new Button("Start Game");
        //log.info("Game Started");
        button1.setOnAction( e -> {
            if(player1.getText().isEmpty() || player2.getText().isEmpty()){
                resp1.setText("*Field cannot be empty");
            }
            else{
                WINDOW.setScene(scene2);
            }
        });

        HBox hPane = new HBox(10, layout);
        BorderPane bPane = new BorderPane();
        bPane.setCenter(hPane);
        bPane.setBottom(button1);
        bPane.setAlignment(button1, Pos.BOTTOM_CENTER);
        bPane.setMargin(button1, new Insets(0, 0, 50, 0));
        scene1 = new Scene(bPane, 450, 300);

        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cell[i][j] = new Cell();
                pane.add(cell[i][j], j, i);
            }
        }

        BorderPane borderPane = new BorderPane();

        borderPane.setTop(statusMsg);
        borderPane.setCenter(pane);

        statusMsg1 = new Label("Player 1: ");
        statusMsg2 = new Label("Player 2: ");

        HBox leftB = new HBox(statusMsg1);
        leftB.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(leftB, Priority.ALWAYS);
        HBox rightB = new HBox(statusMsg2);
        rightB.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(rightB, Priority.ALWAYS);

        Button btn1 = new Button();
        btn1.setShape(new Circle(2));
        btn1.setStyle("-fx-background-color:#FFFF00");
        btn1.setMinSize(50,50);

        Button btn2 = new Button();
        btn2.setShape(new Circle(2));
        btn2.setStyle("-fx-background-color:#006400");
        btn2.setMinSize(50,50);

        HBox cenBottom = new HBox(leftB,rightB,btn1,btn2);
        cenBottom.setPadding(new Insets(10));

        borderPane.setBottom(cenBottom);
        borderPane.setCenter(pane);

        scene2 = new Scene(borderPane, 450, 300);
        WINDOW.setScene(scene1);
        WINDOW.setTitle("Tic Tac Toe");
        WINDOW.show();
    }

    public boolean isBoardFull(){
        for(int i = 0; i<3;i++){
            for(int j= 0; j<3; j++){
                if(cell[i][j].getPlayer() == " "){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean hasWon(String player){
        for(int i=0;i<3;i++){
            if(cell[i][0].getPlayer() == player && cell[i][1].getPlayer() == player && cell[i][2].getPlayer() == player){
                return true;
            }
        }
        for(int i=0;i<3;i++){
            if(cell[0][i].getPlayer() == player && cell[1][i].getPlayer()  == player && cell[2][i].getPlayer() == player){
                return true;
            }
        }
        if(cell[0][0].getPlayer() == player && cell[1][1].getPlayer() == player && cell[2][2].getPlayer() == player){
            return true;
        }
        if(cell[0][2].getPlayer() == player && cell[1][1].getPlayer() == player && cell[2][0].getPlayer() == player){
            return true;
        }
        return false;
    }
    public class Cell extends Pane {
        private String player = " ";

        public Cell(){
            setStyle("-fx-border-color:black");
            this.setPrefSize(300,300);
            this.setOnMouseClicked(c -> handleClick());
        }
        private void handleClick() {
            //if cell empty and game over

                if (player == " " && currentPlayer != " ") {
                    setPlayer(currentPlayer);

                    if (hasWon(currentPlayer)) {
                        statusMsg.setText(currentPlayer + " won");
                        currentPlayer = " ";
                    } else if (isBoardFull()) {
                        statusMsg.setText(currentPlayer + " Draw");
                        currentPlayer = " ";
                    } else {
                        currentPlayer = (currentPlayer == "Player 1") ? "Player 2" : "Player 1";
                        statusMsg.setText(currentPlayer + " must play");
                    }
                }
        }
        public String getPlayer(){
            return player;
        }
        public void setPlayer(String c){
            player = c;
            if(player == character1){
                Ellipse ell = new Ellipse(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2 -10, this.getHeight()/2 -10);
                ell.centerXProperty().bind(this.widthProperty().divide(2));
                ell.centerYProperty().bind(this.heightProperty().divide(2));
                ell.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ell.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ell.setFill(Color.MAROON);

                getChildren().add(ell);

            } else if(player == character2){
                Ellipse ellipse = new Ellipse(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2 -10, this.getHeight()/2 -10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setFill(Color.RED);
                getChildren().add(ellipse);
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
