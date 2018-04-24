package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by user on 25.04.2018.
 */
public class BaseLogTextFloat extends HBox {
    private Text text;
    private Label label;
    private SimpleFloatProperty floatProperty;

    public BaseLogTextFloat(String text) {
        this.text = new Text(text);
        this.label = new Label();
        this.floatProperty = new SimpleFloatProperty(0f);
        this.label.textProperty().bind(this.floatProperty.asString());
        this.getChildren().addAll(
                this.text,this.label
        );
    }

    public void setProperty(float newValue){
        //this.integerProperty.set(newValue);

        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                floatProperty.set(newValue);
            }
        });

    }


}
