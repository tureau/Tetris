package fr.cours.projettetris.tetris;

import android.graphics.Point;

import java.util.Arrays;

public class Tetraminos {
    private Point[] tetraminos;
    private char type;

    public Tetraminos(char type, int largeur) {
        this.type = type;
        this.setTetraminos(largeur);
    }

    public Tetraminos(Tetraminos t) {
        this.tetraminos = Arrays.copyOf(t.tetraminos, t.tetraminos.length);
        this.type = t.type;
    }

    private void setTetraminos(int x) {
        x /= 2;
        final String TYPE = "IOTLJZSE";
        Point[][] list = new Point[][]{
                    {new Point(x-2, -1), new Point(x-1, -1), new Point( x , -1), new Point(x+1, -1)},  // I
                    {new Point(x-1, -2), new Point( x , -2), new Point(x-1, -1), new Point( x , -1)},  // O
                    {new Point(x-2, -2), new Point(x-1, -2), new Point( x , -2), new Point(x-1, -1)},  // T
                    {new Point(x-2, -1), new Point(x-1, -1), new Point( x , -1), new Point( x , -2)},  // L
                    {new Point(x-2, -2), new Point(x-1, -1), new Point(x-2, -1), new Point( x , -1)},  // J
                    {new Point(x-2, -2), new Point(x-1, -2), new Point(x-1, -1), new Point( x , -1)},  // Z
                    {new Point(x-2, -1), new Point(x-1, -1), new Point(x-1, -2), new Point( x , -2)},  // S
                    {} // E
        };

        this.tetraminos = list[TYPE.indexOf(this.type)];
    }

    public char getType() {
        return this.type;
    }

    public Point[] getTetraminos() {
        return this.tetraminos;
    }

    public void move(char dir) {
        switch (dir) {
            case 'D' :
                for (Point p : this.tetraminos) {
                    p.y++;
                }
                break;
            case 'L' :
                for (Point p : this.tetraminos) {
                    p.x--;
                }
                break;
            case 'R' :
                for (Point p : this.tetraminos) {
                    p.x++;
                }
                break;
        }
    }

    public boolean canMove(char dir, char[][] grille) {
        switch (dir) {
            case 'D' :
                for (Point p : this.tetraminos) {
                    if (p.y > grille.length-2 || p.y+1 >= 0 && grille[p.y+1][p.x] != 0) return false;
                }
                break;
            case 'L' :
                for (Point p : this.tetraminos) {
                    if (p.x-1 < 0 || p.y >= 0 && grille[p.y][p.x-1] != 0) return false;
                }
                break;
            case 'R' :
                for (Point p : this.tetraminos) {
                    if (p.x > grille[0].length-2 || p.y >= 0 && grille[p.y][p.x+1] != 0) return false;
                }
                break;
        }

        return true;
    }

    public void rotation(char[][] grille, char rotation) {
        Point center = new Point(tetraminos[1]);
        Point[] copy = new Point[this.tetraminos.length];

        for (int i = 0; i < copy.length; i++) {
            copy[i] = new Point(this.tetraminos[i]);
        }

        for (Point p : tetraminos) {
            p.x -= center.x;
            p.y -= center.y;
            switch (rotation) {
                case 'R' : p = rightRotation(p); break;
                case 'L' : p = leftRotation(p); break;
                case 'T' : p = turnRotation(p); break;
            }
            p.x += center.x;
            p.y += center.y;
        }

        if (!this.canRotate(grille)) {
            this.tetraminos = copy;
        }
    }

    
    public Point rightRotation(Point p) {
         int temp = p.x;
         p.x = -p.y;
         p.y = temp;
         return p;
    }

    public Point leftRotation(Point p) {
        int temp = p.x;
        p.x = p.y;
        p.y = -temp;
        return p;
    }

    public Point turnRotation(Point p) {
        p.x = -p.x;
        p.y = -p.y;
        return p;
    }

    private boolean canRotate(char[][] grille) {
        if (this.type == 'O') return false;
        for (Point p : tetraminos) {
            if (p.x < 0 || p.x > grille[0].length-1)
                return false;
            if ( p.y > -1 && grille[p.y][p.x] != 0)
                return false;
        }
        return  true;
    }

    @Override
    public String toString() {
        return "Tetraminos{" +
                "tetraminos=" + Arrays.toString(tetraminos) +
                ", type=" + type +
                '}';
    }
    
}
