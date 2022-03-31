package fr.cours.projettetris.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import fr.cours.projettetris.Tetris;

public class Interface extends View {
    private boolean initialise;
    public boolean end;

    private int largeur;
    private int hauteur;
    private int tailleCase;

    private Tetris tetris;

    private Paint pBackground;
    private Paint pTetraminosInside;
    private Paint pTetraminosRightLeft;
    private Paint pTetraminosTop;
    private Paint pTetraminosBottom;
    private Paint pScore;

    public Interface(Context context) {
        super(context);
        this.init(context, null);
    }

    public Interface(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    private void init(Context c, AttributeSet attrs) {
        this.initialise = false;
        this.end = false;
    }

    private void initialisation(Canvas c) {
        this.largeur = c.getWidth();
        this.hauteur = c.getHeight();

        this.tailleCase = Math.min(
                50 * this.largeur / 100 / this.tetris.getGrille().getGrille()[0].length,
                80 * this.hauteur / 100 / this.tetris.getGrille().getGrille().length
        );

        this.initialisePaint();

        this.initialise = true;
        this.invalidate();
    }

    private void initialisePaint() {
        this.pBackground = new Paint();
        this.pBackground.setStyle(Paint.Style.STROKE);
        this.pBackground.setAntiAlias(false);

        this.pTetraminosInside = new Paint();
        this.pTetraminosInside.setStyle(Paint.Style.FILL);
        this.pTetraminosInside.setAntiAlias(false);

        this.pTetraminosRightLeft = new Paint(this.pTetraminosInside);
        this.pTetraminosTop       = new Paint(this.pTetraminosInside);
        this.pTetraminosBottom    = new Paint(this.pTetraminosInside);

        this.pScore = new Paint();
        this.pScore.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (initialise) {
            this.drawBackGround(canvas);
            this.drawTetraminos(canvas);
            this.drawGrille(canvas);
            if (end) this.drawEnd(canvas);
        } else this.initialisation(canvas);
    }

    private void drawEnd(Canvas c) {
        pScore.setTextSize(getLargeur()/5f);
        pScore.setColor(Color.RED);
        c.drawText(
                "Perdu",
                getLargeur()/2f - 5*pScore.getTextSize()/4f,
                getHauteur()/2f,
                pScore
        );
    }

    private void drawBackGround(Canvas c) {
        pBackground.setColor(Color.argb(100, 255, 255, 255));
        pBackground.setStrokeWidth(1);

        for (int i = 1; i < tetris.getGrille().getGrille()[0].length; i++) {
            c.drawLine(
                    (largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f + i*tailleCase,
                    (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f,
                    (largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f + i*tailleCase,
                    (hauteur + tetris.getGrille().getGrille().length*tailleCase) /2f,
                    pBackground
            );
        }
        for (int i = 1; i < tetris.getGrille().getGrille().length; i++) {
            c.drawLine(
                    (largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f,
                    (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f + i*tailleCase,
                    (largeur + tetris.getGrille().getGrille()[0].length*tailleCase) /2f,
                    (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f + i*tailleCase,
                    pBackground
            );
        }

        pBackground.setColor(Color.WHITE);
        pBackground.setStrokeWidth(this.largeur/100f);

        c.drawLine(
                (largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f-pBackground.getStrokeWidth()/2,
                (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f,
                (largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f-pBackground.getStrokeWidth()/2,
                (hauteur + tetris.getGrille().getGrille().length*tailleCase) /2f+pBackground.getStrokeWidth(),
                pBackground
        );
        c.drawLine(
                (largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f,
                (hauteur + tetris.getGrille().getGrille().length*tailleCase) /2f+pBackground.getStrokeWidth()/2,
                (largeur + tetris.getGrille().getGrille()[0].length*tailleCase) /2f,
                (hauteur + tetris.getGrille().getGrille().length*tailleCase) /2f+pBackground.getStrokeWidth()/2,
                pBackground
        );
        c.drawLine(
                (largeur + tetris.getGrille().getGrille()[0].length*tailleCase) /2f+pBackground.getStrokeWidth()/2,
                (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f,
                (largeur + tetris.getGrille().getGrille()[0].length*tailleCase) /2f+pBackground.getStrokeWidth()/2,
                (hauteur + tetris.getGrille().getGrille().length*tailleCase) /2f+pBackground.getStrokeWidth(),
                pBackground
        );
        c.drawLine(
                0,
                (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f + pBackground.getStrokeWidth()/2 + 2*tailleCase,
                (largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f-pBackground.getStrokeWidth()/2,
                (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f + pBackground.getStrokeWidth()/2 + 2*tailleCase,
                pBackground
        );

        c.drawLine(
                0,
                (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f + pBackground.getStrokeWidth()/2 + 9*tailleCase,
                (largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f-pBackground.getStrokeWidth()/2,
                (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f + pBackground.getStrokeWidth()/2 + 9*tailleCase,
                pBackground
        );

        pScore.setTextSize(70* Math.min(
                (float) (largeur - tetris.getGrille().getGrille()[0].length*tailleCase)/(tetris.getGrille().getScore()+"").length(),
                2f*tailleCase
        ) /100);
        c.drawText(
                tetris.getGrille().getScore()+"",
                ((largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f-pBackground.getStrokeWidth()/2)/2 - (pScore.getTextSize()/4)*(tetris.getGrille().getScore()+" ").length(),
                (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f + pBackground.getStrokeWidth()/2 + tailleCase + pScore.getTextSize()/4,
                pScore
        );
        pScore.setTextSize(70* Math.min(
                (float) (largeur - tetris.getGrille().getGrille()[0].length*tailleCase)/(((int)tetris.getGrille().niveau+"").length()+8),
                2f*tailleCase
        ) /100);
        c.drawText(
                "niveau : " + (int)tetris.getGrille().niveau,
                ((largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f-pBackground.getStrokeWidth()/2)/8,
                (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f + pBackground.getStrokeWidth()/2 + 10*tailleCase + pScore.getTextSize()/4,
                pScore
        );
    }

    private void drawTetraminos(Canvas c) {
        this.colorPaintTetraminos(tetris.getTetraminos()[1].getType());
        for (Point p : tetris.getTetraminos()[1].getTetraminos()) {
            this.drawSquare(c, p.y, p.x);
        }

        float x = 5f;
        float y = 4f;
        switch (tetris.getTetraminos()[0].getType()) {
            case 'I': x = 6f; y = 3f; break;
            case 'O': x = 4f; break;
        }

        float tailleCase = Math.min(
                ((largeur - tetris.getGrille().getGrille()[0].length*this.tailleCase) /2f-pBackground.getStrokeWidth()/2)/x,
                (7*this.tailleCase)/y
        );

        float left, top, right, bottom;
        this.colorPaintTetraminos((tetris.getTetraminos()[0].getType()));

        for (Point p : tetris.getTetraminos()[0].getTetraminos()) {
            left = (p.x-2)*tailleCase;
            top = (p.y+5)*tailleCase + (hauteur - tetris.getGrille().getGrille().length*this.tailleCase) /2f + pBackground.getStrokeWidth()/2 + 2*tailleCase;
            right = (p.x-1)*tailleCase+1;
            bottom = (p.y+6)*tailleCase+1 + (hauteur - tetris.getGrille().getGrille().length*this.tailleCase) /2f + pBackground.getStrokeWidth()/2 + 2*tailleCase;
            this.drawSquare(c, left, top, right, bottom);
        }
    }

    private void drawGrille(Canvas c) {
        for (int i = 0; i < tetris.getGrille().getGrille().length; i++) {
            for (int j = 0; j < tetris.getGrille().getGrille()[0].length; j++) {
                colorPaintTetraminos(tetris.getGrille().getGrille()[i][j]);
                this.drawSquare(c, i, j);
            }
        }
    }

    private void drawSquare(Canvas c, int i, int j) {
        float left, top, right, bottom;
        left = (largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f + j*tailleCase;
        top = (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f + i*tailleCase;
        right = (largeur - tetris.getGrille().getGrille()[0].length*tailleCase) /2f + (j+1)*tailleCase+1;
        bottom = (hauteur - tetris.getGrille().getGrille().length*tailleCase) /2f + (i+1)*tailleCase+1;
        this.drawSquare(c, left, top, right, bottom);
    }

    private void drawSquare(Canvas c, float left, float top, float right, float bottom) {
        Path path;

        c.drawRect(
                left,
                top,
                right,
                bottom,
                pTetraminosRightLeft
        );

        path  = new Path();
        path.moveTo(left, top);
        path.lineTo((left+right)/2f, (top+bottom)/2f);
        path.lineTo(right, top);

        c.drawPath(path, pTetraminosTop);

        path = new Path();
        path.moveTo(left, bottom);
        path.lineTo((left+right)/2f, (top+bottom)/2f);
        path.lineTo(right, bottom);

        c.drawPath(path, pTetraminosBottom);

        c.drawRect(
                left + 0.15f*tailleCase,
                top + 0.15f*tailleCase,
                right - 0.15f*tailleCase,
                bottom - 0.15f*tailleCase,
                pTetraminosInside
        );
    }

    private void colorPaintTetraminos(char c) {
        switch (c) {
            case 'I' :
                this.pTetraminosInside   .setColor(Color.rgb(110, 236, 238));
                this.pTetraminosRightLeft.setColor(Color.rgb( 98, 213, 214));
                this.pTetraminosTop      .setColor(Color.rgb(194, 249, 250));
                this.pTetraminosBottom   .setColor(Color.rgb( 51, 118, 119));
                break;
            case 'O' :
                this.pTetraminosInside   .setColor(Color.rgb(240, 240,  79));
                this.pTetraminosRightLeft.setColor(Color.rgb(216, 216,  70));
                this.pTetraminosTop      .setColor(Color.rgb(251, 251, 187));
                this.pTetraminosBottom   .setColor(Color.rgb(120, 120,  35));
                break;
            case 'T' :
                this.pTetraminosInside   .setColor(Color.rgb(146,  28, 231));
                this.pTetraminosRightLeft.setColor(Color.rgb(131,  24, 208));
                this.pTetraminosTop      .setColor(Color.rgb(220, 181, 246));
                this.pTetraminosBottom   .setColor(Color.rgb( 73,   9, 115));
                break;
            case 'L' :
                this.pTetraminosInside   .setColor(Color.rgb(228, 163,  56));
                this.pTetraminosRightLeft.setColor(Color.rgb(205, 147,  50));
                this.pTetraminosTop      .setColor(Color.rgb(247, 228, 184));
                this.pTetraminosBottom   .setColor(Color.rgb(114,  82,  24));
                break;
            case 'J' :
                this.pTetraminosInside   .setColor(Color.rgb(  0,   0, 230));
                this.pTetraminosRightLeft.setColor(Color.rgb(  0,   0, 207));
                this.pTetraminosTop      .setColor(Color.rgb(179, 179, 245));
                this.pTetraminosBottom   .setColor(Color.rgb(  0,   0, 115));
                break;
            case 'Z' :
                this.pTetraminosInside   .setColor(Color.rgb(220,  48,  32));
                this.pTetraminosRightLeft.setColor(Color.rgb(198,  42,  28));
                this.pTetraminosTop      .setColor(Color.rgb(241, 182, 180));
                this.pTetraminosBottom   .setColor(Color.rgb(100,  19,  10));
                break;
            case 'S' :
                this.pTetraminosInside   .setColor(Color.rgb(110, 236,  71));
                this.pTetraminosRightLeft.setColor(Color.rgb( 98, 213,  63));
                this.pTetraminosTop      .setColor(Color.rgb(194, 249, 185));
                this.pTetraminosBottom   .setColor(Color.rgb( 51, 118, 31));
                break;
            default  :
                this.pTetraminosInside   .setColor(Color.TRANSPARENT);
                this.pTetraminosRightLeft.setColor(Color.TRANSPARENT);
                this.pTetraminosTop      .setColor(Color.TRANSPARENT);
                this.pTetraminosBottom   .setColor(Color.TRANSPARENT);
        }
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getTailleCase() {
        return tailleCase;
    }

    public void setTetris(Tetris tetris) {
        this.tetris = tetris;
    }
}
