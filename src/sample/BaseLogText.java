package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.concurrent.Executor;

/**
 * Created by user on 24.04.2018.
 */
public class BaseLogText extends HBox {

    private Text text;
    private Label label;
    private SimpleFloatProperty floatProperty;
    private SimpleIntegerProperty integerProperty;
    //private HBox hBox;

    public BaseLogText(String text) {
        this.text = new Text(text);
        this.label = new Label();
        this.integerProperty = new SimpleIntegerProperty(0);
        this.label.textProperty().bind(this.integerProperty.asString());
        this.getChildren().addAll(
                this.text,this.label
        );

    }

    public void setProperty(int newValue){
        //this.integerProperty.set(newValue);

        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                integerProperty.set(newValue);
            }
        });

    }


}
