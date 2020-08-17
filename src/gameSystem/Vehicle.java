package gameSystem;


import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

import java.io.File;

public class Vehicle extends Entity {


    double speed;

    public Vehicle(String carImage, int  size, double xPos, double yPos, double speed){

        setImage(new Image(new File(carImage).toURI().toString(), size, size, true, true));
        setX(xPos);
        setY(yPos);
        this.speed= speed;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                move(speed , 0);
                if (getX() > 600 && speed>0)
                    setX(-180);
                if (getX() < -50 && speed<0)
                    setX(700);

            }


        };
        timer.start();
    }
    }

