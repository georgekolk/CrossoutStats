package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    //SimpleFloatProperty

    //private int counter = 1;
    //private SimpleIntegerProperty counterProperty;
    File file = new File("D:/md/dc/My Games/Crossout/logs/2018.04.25 01.37.09/combat.log");
    //File file = new File("D:/md/dc/My Games/Crossout/logs/2018.04.24 18.48.10/combat.log");
    //2018.04.22 23.20.55\"
    long fileLength = file.length();


    private static String playerNick = "Palaleipa";

    private static float pveCurrentBattlePlayerDamage = 0;
    private static float pvpCurrentBattlePlayerDamage = 0;
    private static float pveCurrentBattleTeamDamage = 0;
    private static float pvpCurrentBattleTeamDamage = 0;
    private static float pvpOverallSessionDamage = 0;
    private static float pveOverallSessionDamage = 0;
    private static float pvpPercentageFromOverallTeamDamage = 0;
    private static float pvePercentageFromOverallTeamDamage = 0;

    private static int pvpWins = 0;
    private static int pvpLoses = 0;
    private static int pveWins = 0;
    private static int pveLoses = 0;
    private static int pvpKilled = 0;
    private static int pveKilled = 0;
    private static int pvpDeaths = 0;
    private static int pveDeaths = 0;

    private static int pvpWinPercentage = 0;
    private static SimpleIntegerProperty pvpWinPercentageProperty;
    private static int pveWinPercentage = 0;
    private static SimpleIntegerProperty pveWinPercentageProperty;

    //windows
    private double xOffset = 0;
    private double yOffset = 0;

    private static int playerTeam = 0;

    private static String gameMode = ""; //pve or pvp

    private static BaseLogText pvpWinsBox   ;
    private static BaseLogText pvpLosesBox  ;
    private static BaseLogText pvpKilledBox ;
    private static BaseLogText pvpDeathsBox ;
    private static BaseLogText pveWinsBox   ;
    private static BaseLogText pveLosesBox  ;
    private static BaseLogText pveKilledBox ;
    private static BaseLogText pveDeathsBox ;

    private static BaseLogTextFloat pveCurrentBattlePlayerDamageBox;
    private static BaseLogTextFloat pvpCurrentBattlePlayerDamageBox;
    private static BaseLogTextFloat pveCurrentBattleTeamDamageBox;
    private static BaseLogTextFloat pvpCurrentBattleTeamDamageBox;
    private static BaseLogTextFloat pvpOverallSessionDamageBox;
    private static BaseLogTextFloat pveOverallSessionDamageBox;
    private static BaseLogTextFloat pvpPercentageFromOverallTeamDamageBox;
    private static BaseLogTextFloat pvePercentageFromOverallTeamDamageBox;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Platform.setImplicitExit(false);
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        FlowPane root = new FlowPane();
        //root.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        root.setOrientation(Orientation.VERTICAL);
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        //move around here
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        pvpWinsBox = new BaseLogText("pvpWins: ");
        pvpLosesBox = new BaseLogText("pvpLoses: ");
        pvpKilledBox = new BaseLogText("pvpKilled: ");
        pvpDeathsBox = new BaseLogText("pvpDeaths: ");
        pveWinsBox = new BaseLogText("pveWins: ");
        pveLosesBox = new BaseLogText("pveLoses: ");
        pveKilledBox = new BaseLogText("pveKilled: ");
        pveDeathsBox = new BaseLogText("pveDeaths: ");

        pveCurrentBattlePlayerDamageBox = new BaseLogTextFloat("pveCurrentBattlePlayerDamage: ");
        pvpCurrentBattlePlayerDamageBox = new BaseLogTextFloat("pvpCurrentBattlePlayerDamage: ");
        pveCurrentBattleTeamDamageBox = new BaseLogTextFloat("pveCurrentBattleTeamDamage: ");
        pvpCurrentBattleTeamDamageBox = new BaseLogTextFloat("pvpCurrentBattleTeamDamage: ");
        pvpOverallSessionDamageBox = new BaseLogTextFloat("pvpOverallSessionDamage: ");
        pveOverallSessionDamageBox = new BaseLogTextFloat("pveOverallSessionDamage: ");
        pvpPercentageFromOverallTeamDamageBox =new BaseLogTextFloat("pvpPercentageFromOverallTeamDamage: ");
        pvePercentageFromOverallTeamDamageBox = new BaseLogTextFloat("pvePercentageFromOverallTeamDamage: ");

        CrossoutBattleLogFileReadService сrossoutBattleLogFileReadService = new CrossoutBattleLogFileReadService();
        сrossoutBattleLogFileReadService.start();

        root.getChildren().addAll(

                pvpWinsBox, pvpLosesBox, pvpKilledBox, pvpDeathsBox,
                pveWinsBox, pveLosesBox, pveKilledBox, pveDeathsBox,

                pveCurrentBattlePlayerDamageBox,
        pvpCurrentBattlePlayerDamageBox,
        pveCurrentBattleTeamDamageBox,
        pvpCurrentBattleTeamDamageBox,
        pvpOverallSessionDamageBox,
        pveOverallSessionDamageBox,
        pvpPercentageFromOverallTeamDamageBox,
        pvePercentageFromOverallTeamDamageBox
        );


        primaryStage.setTitle("Crossout Stats");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        //primaryStage.setScene(new Scene(root, 300, 275, Color.TRANSPARENT));
        primaryStage.setScene(new Scene(root, 400, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private static void readFile(File file,Long fileLength) throws IOException {
        String line = null;

        BufferedReader in = new BufferedReader(new java.io.FileReader(file));
        in.skip(fileLength);

        while((line = in.readLine()) != null){
                //11:11:32.853  CMBT   | ===== Gameplay 'Pve' started, map 'arizona_castle' ======


            System.out.println(line);

            if (line.contains("Gameplay '")){
                System.out.println(line);
            }

            if (line.contains("Gameplay 'Assault'") || line.contains("Gameplay 'Conquer'")){
                gameMode = "pvp";
            }else if (line.contains("Gameplay 'Pve")){
                gameMode = "pve";
            }

            if (line.contains("nickname: " + playerNick)){
                if (line.contains("team: 2")){
                    playerTeam = 2;
                    //System.out.println("pvpGameTeam: " + playerTeam);
                }else {
                    playerTeam = 1;
                    //System.out.println("pvpGameTeam: " + playerTeam);
                }
            }

            if (line.contains("killer: " + playerNick)){
                switch (gameMode){
                    case "pve":
                        pveKilled++;
                        pveKilledBox.setProperty(pveKilled);
                        break;
                    case "pvp":
                        pvpKilled++;
                        pvpKilledBox.setProperty(pvpKilled);
                    break;
                }
            }

            //System.out.println("line.indexOf damage: " + line.indexOf("damage:"));

            if (line.contains("assist by " + playerNick)){
                String lel = line.substring(line.indexOf("damage:") + 8);
                //System.out.println(lel);
                //System.out.println("line.indexOf_DMG_: " + line.indexOf("DMG_"));
                lel = lel.substring(0, lel.indexOf("DMG_"));
                //System.out.println(lel);

                switch (gameMode){
                    case "pve":
                        pveCurrentBattlePlayerDamage = pveCurrentBattlePlayerDamage + Float.valueOf(lel);
                        pveOverallSessionDamage = pveOverallSessionDamage + Float.valueOf(lel);

                        pveCurrentBattlePlayerDamageBox.setProperty(pveCurrentBattlePlayerDamage);
                        pveOverallSessionDamageBox.setProperty(pveOverallSessionDamage);
                        break;
                    case "pvp":
                        pvpCurrentBattlePlayerDamage = pvpCurrentBattlePlayerDamage + Float.valueOf(lel);
                        pvpOverallSessionDamage = pvpOverallSessionDamage + Float.valueOf(lel);

                        pvpCurrentBattlePlayerDamageBox.setProperty(pvpCurrentBattlePlayerDamage);
                        pvpOverallSessionDamageBox.setProperty(pvpOverallSessionDamage);
                        break;
                }

            }

            if (line.contains("assist by")){
                String lel = line.substring(line.indexOf("damage:") + 8);
                //System.out.println(lel);
                //System.out.println("line.indexOf_DMG_: " + line.indexOf("DMG_"));
                lel = lel.substring(0, lel.indexOf("DMG_"));
                //System.out.println(lel);

                switch (gameMode){
                    case "pve":
                        pveCurrentBattleTeamDamage = pveCurrentBattleTeamDamage + Float.valueOf(lel);
                        pveCurrentBattleTeamDamageBox.setProperty(pveCurrentBattleTeamDamage);
                        break;
                    case "pvp":
                        pvpCurrentBattleTeamDamage = pvpCurrentBattleTeamDamage + Float.valueOf(lel);
                        pvpCurrentBattleTeamDamageBox.setProperty(pvpCurrentBattleTeamDamage);
                        break;
                }
            }

            pvpPercentageFromOverallTeamDamageBox.setProperty(pvpCurrentBattlePlayerDamage/pvpCurrentBattleTeamDamage*100.0f);
            pvePercentageFromOverallTeamDamageBox.setProperty(pveCurrentBattlePlayerDamage/pveCurrentBattleTeamDamage*100.0f);


            if (line.contains("Victim: " + playerNick)){
                switch (gameMode){
                    case "pve":
                        pveDeaths++;
                        pveDeathsBox.setProperty(pveDeaths);
                        break;
                    case "pvp":
                        pvpDeaths++;
                        pvpDeathsBox.setProperty(pvpDeaths);
                        break;
                }
            }

            if (line.contains("Gameplay finish,")) {

                switch (gameMode){
                    case "pve":
                        if (line.contains("winner team 2")) {
                            pveLoses++;
                            pveLosesBox.setProperty(pveLoses);
                        } else {
                            pveWins++;
                            pveWinsBox.setProperty(pveWins);
                        }

                        //System.out.println("pveWinPercentage: " + (pveWins/(pveLoses+pveWins))*100);
                        //pveWinPercentage = (pveWins/(pveLoses+pveWins))*100;
                        break;
                    case "pvp":
                        if (line.contains("winner team 2")) {
                            switch (playerTeam) {
                                case 1:
                                    pvpLoses++;
                                    pvpLosesBox.setProperty(pvpLoses);
                                    break;
                                case 2:
                                    pvpWins++;
                                    pvpWinsBox.setProperty(pvpWins);

                                    break;
                            }
                        } else {
                            switch (playerTeam) {
                                case 1:
                                    pvpWins++;
                                    pvpWinsBox.setProperty(pvpWins);
                                    break;
                                case 2:
                                    pvpLoses++;
                                    pvpLosesBox.setProperty(pvpLoses);
                                    break;
                            }
                        }
                        //System.out.println("pvpWinPercentage: " + (pvpWins/(pvpLoses+pvpWins))*100);
                        //pvpWinPercentage = (pvpWins/(pvpLoses+pvpWins))*100;
                        break;
                }
                gameMode = "";
            }


            if (line.contains("Gameplay finish,")){

                System.out.println("pvpKilled: " + pvpKilled);
                System.out.println("pveKilled: " + pveKilled);

                System.out.println("pveDeaths: " + pveDeaths);
                System.out.println("pvpDeaths: " + pvpDeaths);

                System.out.println("pvpWins: " + pvpWins);
                System.out.println("pvpLooses: " + pvpLoses);
                System.out.println("pveWins: " + pveWins);
                System.out.println("pveLooses: " + pveLoses);

                System.out.println("pveCurrentBattlePlayerDamage:" + pveCurrentBattlePlayerDamage);
                System.out.println("pvpCurrentBattlePlayerDamage: " + pvpCurrentBattlePlayerDamage);
                System.out.println("pveOverallSessionDamage: " + pveOverallSessionDamage);
                System.out.println("pvpOverallSessionDamage: " + pvpOverallSessionDamage);

                System.out.println("pveCurrentBattleTeamDamage: " + pveCurrentBattleTeamDamage);
                System.out.println("pvpCurrentBattleTeamDamage: " + pvpCurrentBattleTeamDamage);

                System.out.println("pvp percentage from overall team damage: " + pvpCurrentBattlePlayerDamage/pvpCurrentBattleTeamDamage*100.0f);
                System.out.println("pve percentage from overall team damage: " + pveCurrentBattlePlayerDamage/pveCurrentBattleTeamDamage*100.0f);

                /*float correct = 25;
                float questionNum = 100;
                float percent = (pvpCurrentBattlePlayerDamage * 100.0f) / pvpCurrentBattleTeamDamage;
                System.out.println("pvp percentage from overall team damage: " + percent);*/

                pvpCurrentBattlePlayerDamage = 0;
                pveCurrentBattlePlayerDamage = 0;

                pveCurrentBattleTeamDamage = 0;
                pvpCurrentBattleTeamDamage = 0;
            }

                //if (line.contains("killer: " + playerNick)){System.out.println(line);}
            }
                in.close();


    }

    private class CrossoutBattleLogFileReadService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    if (file.exists() && file.canRead()) {
                        fileLength = file.length();
                        try {
                            readFile(file, 0L);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        while (true) {

                            if (fileLength < file.length()) {

                                try {
                                    readFile(file, fileLength);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                fileLength = file.length();
                            }

                            Thread.sleep(100);

                        }

                    } else {
                        System.out.println("no file to read");
                    }

                    return null;
                }
            };
        }
    }
}

