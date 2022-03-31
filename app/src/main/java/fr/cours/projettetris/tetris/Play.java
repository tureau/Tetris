package fr.cours.projettetris.tetris;

import fr.cours.projettetris.Memoire;
import fr.cours.projettetris.Tetris;

public class Play extends Thread {

    private final Tetris tetris;

    public Play(Tetris tetris) {
        this.tetris = tetris;
    }

    @Override
    public void run() {
        boolean finish = false;

        int[] niveau = new int[]{0, 30, 80, 200, 250, 300, 430, 460, 500};
        while (!finish) {
            try {
                Thread.sleep(1000 - niveau[(int) tetris.getGrille().niveau]);
                if (tetris.getTetraminos()[1].canMove('D', tetris.getGrille().getGrille()))
                    tetris.getTetraminos()[1].move('D');
                else {
                    tetris.getGrille().addTetraminos(tetris.getTetraminos()[1]);
                    tetris.setTetraminos();
                }
                tetris.getAnInterface().invalidate();
            } catch (Exception e) {
                finish = true;
            }
        }
        tetris.blocked();
        tetris.getAnInterface().end = true;
        tetris.getAnInterface().invalidate();
        try { Thread.sleep(2000); } catch (Exception e) {}
        if (tetris.getGrille().getScore() > 0) Memoire.ajouterScore(tetris.getGrille().getScore());
        Memoire.save();
        tetris.finish();
    }
}