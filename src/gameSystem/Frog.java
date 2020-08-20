package gameSystem;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static gameSystem.gameScene.IMAGES_PATH;


public class Frog extends Entity {
    final static String IMAGES_PATH = "Resources\\Images\\";
    private List<Entity> entities;
    Image imgW1;
    Image imgA1;
    Image imgS1;
    Image imgD1;
    Image imgW2;
    Image imgA2;
    Image imgS2;
    Image imgD2;

    double movementV=31;
    double movementH=15;
    boolean isDeath=true;
    boolean goUp,goLeft,goDown,goRight;
    private boolean singleClick=true;
    private static boolean death=false;
    private static boolean isColliding=false;
    Turtle tur;

    int size=30;//serve a fare lo scaling della rana



    public Frog(String link, Scene scene, List<Entity> interceptable){
        setImage(new Image(new File(link).toURI().toString(),size,size,true,true));
        setX(135);
        setY(475);
        this.tur=tur;
        this.entities=interceptable;
        imgW1 = new Image(new File(IMAGES_PATH + "froggerUp.png").toURI().toString(),size,size,true,true);
        imgA1 = new Image(new File(IMAGES_PATH + "froggerLeft.png").toURI().toString(),size,size,true,true);
        imgS1 = new Image(new File(IMAGES_PATH + "froggerDown.png").toURI().toString(),size,size,true,true);
        imgD1 = new Image(new File(IMAGES_PATH + "froggerRight.png").toURI().toString(),size,size,true,true);
        imgW2 = new Image(new File(IMAGES_PATH + "froggerUpJump.png").toURI().toString(),size,size,true,true);
        imgA2 = new Image(new File(IMAGES_PATH + "froggerLeftJump.png").toURI().toString(),size,size,true,true);
        imgS2 = new Image(new File(IMAGES_PATH + "froggerDownJump.png").toURI().toString(),size,size,true,true);
        imgD2 = new Image(new File(IMAGES_PATH + "froggerRightJump.png").toURI().toString(),size,size,true,true);

        //movimento
        if(death){

        }else {
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent event) {


                    if (event.getCode() == KeyCode.W && singleClick && getY() > 120) {
                        singleClick = false;
                        if(isDeath){
                        move(0, -movementV);
                        setImage(imgW2);
                        }

                    } else if (event.getCode() == KeyCode.A && singleClick && getX() > 10) {
                        singleClick = false;
                        if(isDeath) {
                            move(-movementH, 0);
                            setImage(imgA2);
                        }

                    } else if (event.getCode() == KeyCode.S && singleClick && getY() < 475) {
                        singleClick = false;
                        if(isDeath) {
                            move(0, movementV);
                            setImage(imgS2);
                        }

                    } else if (event.getCode() == KeyCode.D && singleClick && getX() < 330) {
                        singleClick = false;
                        if(isDeath) {
                            move(movementH, 0);
                            setImage(imgD2);
                        }

                    }


                }


            });
        }
        if (death) {

        } else {
            scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent event) {

                    if (event.getCode() == KeyCode.W) {
                        if(isDeath) {
                            setImage(imgW1);
                            singleClick = true;
                        }
                    } else if (event.getCode() == KeyCode.A) {
                        if(isDeath) {
                            setImage(imgA1);
                            singleClick = true;
                        }
                    } else if (event.getCode() == KeyCode.S) {
                        setImage(imgS1);
                        singleClick = true;
                    } else if (event.getCode() == KeyCode.D) {
                        setImage(imgD1);
                        singleClick = true;
                    }

                }

            });
        }



    }

    @Override
    public void movement(Long now) {


        if (collision.specificCollision(entities, this, Vehicle.class) || collision.specificCollision(entities, this, Snake.class) && death == true) {
            death = true;
            isDeath = false;
            isDeath = Death.carDeath(now, this);

        }
      /*  if (getY() < 260 && getY() > 107) {

            if (collision.specificCollision(entities, this, Log.class) && !death) {
                isColliding = true;
                Log log = collision.getOne(entities, this, Log.class);
                this.move(log.getSpeed(), 0);


            } else {
                death = true;
                isDeath = false;
                isDeath = Death.waterDeath(now, this);
            }
        }*/

        if (getY() < 107) {
            if (collision.specificCollision(entities, this, Burrow.class)) {
                Burrow b = collision.getOne(entities, this, Burrow.class);
                if (!b.isFull()) {
                    if (collision.specificCollision(entities, this, Bonus.class))
                        System.out.println("bonus");

                    b.setFrogEnd();
                    RandomBonus.removePos(b.getX());
                } else {
                    death = true;
                    isDeath = false;
                    isDeath = Death.waterDeath(now, this);

                }


            }
        }
    }

}









   /* perchè non funge?
else  if(collision.specificCollision(entities,this,Turtle.class)){
                    isColliding=true;
                    Turtle turtle=collision.getOne(entities,this,Turtle.class);
                    this.move(turtle.getSpeed(),0);

*/
