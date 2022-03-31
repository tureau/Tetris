package fr.cours.projettetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

import fr.cours.projettetris.tetris.Grille;
import fr.cours.projettetris.tetris.Interface;
import fr.cours.projettetris.tetris.Play;
import fr.cours.projettetris.tetris.Tetraminos;

public class Tetris extends AppCompatActivity implements SensorEventListener {
    private boolean block;
    private boolean canHold;

    private long time;

    private GestureDetector gd;
    private Tetraminos[] tetraminos; // 0: hold, 1: current, 2+: suivant
    private Grille grille;
    private Interface anInterface;
    private String option ;  // S for Scroll   F for Fling
    private SensorManager sensorManager;
    private Sensor accelerometre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        gd = new GestureDetector(this, new GestureListener());
        canHold = true;
        grille = new Grille(10, 20);
        anInterface = findViewById(R.id.GUI);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,accelerometre, SensorManager.SENSOR_DELAY_NORMAL);
        block = false;
        tetraminos = new Tetraminos[3];
        tetraminos[0] = new Tetraminos('E', 0);
        this.setTetraminos();
        anInterface.setTetris(this);
        this.option = Memoire.faconChoisi;
        new Play(this).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(!Memoire.faconChoisi.equals("Accéléromètre"))return;
        if(!(Calendar.getInstance().getTimeInMillis() - time >= 100))return;
        if(sensorEvent.values[0] <= -1.25f)
        {
            rightSwipe();
            anInterface.invalidate();
        }
        else if(sensorEvent.values[0] >= 1.25f)
        {
            leftSwipe();
            anInterface.invalidate();
        }
        time = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int THRESHOLD = 100;
        private static final int VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            boolean result = false;
            if (block) return false;

            if (Math.abs(distanceX) > anInterface.getTailleCase()/2.5) {
                if (option.equals("Scroll")) {
                    if (distanceX > 0) {
                        leftSwipe();
                    } else {
                        rightSwipe();
                    }
                    result = true;
                }
            }

            if (Math.abs(distanceY) > anInterface.getTailleCase()/2.5) {
                if (distanceY < 0) {
                    bottomSwipe();
                }
                result = true;
            }

            anInterface.invalidate();
            return result;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            if (block) return false;

            try {
                float deltaX = e2.getX() - e1.getX();
                float deltaY = e2.getY() - e1.getY();
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (option.equals("Swipe")) {
                        if (Math.abs(deltaX) > THRESHOLD && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                            if (deltaX > 0) rightSwipe();
                            else leftSwipe();
                            result = true;
                        }
                    }
                } else {
                    if (Math.abs(deltaY) > THRESHOLD && Math.abs(velocityY) > VELOCITY_THRESHOLD) {
                        if (deltaY > 0)
                            while (tetraminos[1].canMove('D', grille.getGrille()))
                                bottomSwipe();
                        else            topSwipe();
                        result = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            anInterface.invalidate();
            return  result;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (block) return super.onSingleTapUp(e);

            if (e.getX() < (anInterface.getLargeur() - grille.getGrille()[0].length*anInterface.getTailleCase()) /2f &&
                    e.getY() > (anInterface.getHauteur() - grille.getGrille().length*anInterface.getTailleCase()) /2f + 6*anInterface.getTailleCase() &&
                    e.getY() < (anInterface.getHauteur() - grille.getGrille().length*anInterface.getTailleCase()) /2f + 13*anInterface.getTailleCase() &&
                    canHold ) {

                Tetraminos temp = tetraminos[0];
                tetraminos[0] = new Tetraminos(tetraminos[1].getType(), grille.getGrille()[0].length);
                if (temp.getType() == 'E') {
                    setTetraminos();
                } else {
                    tetraminos[1] = temp;
                }
                canHold = false;
            } else if (e.getX() < anInterface.getLargeur()/2f) {
                tetraminos[1].rotation(grille.getGrille(), 'L');
            } else {
                tetraminos[1].rotation(grille.getGrille(), 'R');
            }
            anInterface.invalidate();
            return super.onSingleTapUp(e);
        }
    }

    public void rightSwipe() {
        moveTetraminos('R');
    }

    public void leftSwipe() {
        moveTetraminos('L');
    }

    public void bottomSwipe() {
        this.moveTetraminos('D');
    }

    public void topSwipe() {
        tetraminos[1].rotation(grille.getGrille(), 'T');;
    }

    public void moveTetraminos(char c) {
        if (this.tetraminos[1].canMove(c, this.grille.getGrille())) this.tetraminos[1].move(c);
    }

    public void setTetraminos() {
        canHold = true;
        String type = "IOTLJZS";
        System.arraycopy(tetraminos, 2, tetraminos, 1, tetraminos.length-2);
        tetraminos[tetraminos.length-1] = new Tetraminos(type.charAt(new Random().nextInt(type.length())), grille.getGrille()[0].length);
        if (Arrays.asList(tetraminos).contains(null)) setTetraminos();
    }

    public Grille getGrille() {
        return grille;
    }

    public Tetraminos[] getTetraminos() {
        return tetraminos;
    }

    public Interface getAnInterface() {
        return anInterface;
    }

    public void blocked() {
        this.block = true;
    }

}