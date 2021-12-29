package com.ui.component.subcomponent;

import com.ui.scheme.ColorScheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GamePanelMap extends JPanel {
    MapGrid[][] mapGrids = {
            {MapGrid.BLANK, MapGrid.BLANK, MapGrid.CROSSROAD, MapGrid.BLANK, MapGrid.FORWARD, MapGrid.NULL, MapGrid.BACKWARD, MapGrid.BACKWARD, MapGrid.RENDEZVOUS, MapGrid.BLANK, MapGrid.ALL_GET_ITEM, MapGrid.NULL, MapGrid.GOAL},
            {MapGrid.BLANK, MapGrid.NULL, MapGrid.GET_ITEM, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.FORWARD, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.FORWARD, MapGrid.NULL, MapGrid.BLANK},
            {MapGrid.GET_ITEM, MapGrid.NULL, MapGrid.GET_ITEM, MapGrid.NULL, MapGrid.GET_ITEM, MapGrid.NULL, MapGrid.FORWARD, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.BLANK},
            {MapGrid.BLANK, MapGrid.NULL, MapGrid.GET_ITEM, MapGrid.NULL, MapGrid.FORWARD, MapGrid.NULL, MapGrid.BACKWARD, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.BACKWARD, MapGrid.NULL, MapGrid.BACKWARD},
            {MapGrid.BLANK, MapGrid.NULL, MapGrid.GET_ITEM, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.FORWARD, MapGrid.NULL, MapGrid.ALL_LOSE_ITEM, MapGrid.NULL, MapGrid.GET_ITEM, MapGrid.NULL, MapGrid.BLANK},
            {MapGrid.BLANK, MapGrid.NULL, MapGrid.RESTART, MapGrid.NULL, MapGrid.BACKWARD, MapGrid.NULL, MapGrid.BACKWARD, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.FORWARD, MapGrid.NULL, MapGrid.FORWARD},
            {MapGrid.BLANK, MapGrid.NULL, MapGrid.RESTART, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.BACKWARD, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.BLANK},
            {MapGrid.GET_ITEM, MapGrid.NULL, MapGrid.RESTART, MapGrid.NULL, MapGrid.FORWARD, MapGrid.NULL, MapGrid.FORWARD, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.BLANK},
            {MapGrid.BLANK, MapGrid.NULL, MapGrid.GET_ITEM, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.FORWARD, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.FORWARD, MapGrid.NULL, MapGrid.FORWARD},
            {MapGrid.BLANK, MapGrid.NULL, MapGrid.GET_ITEM, MapGrid.NULL, MapGrid.BACKWARD, MapGrid.NULL, MapGrid.BACKWARD, MapGrid.NULL, MapGrid.ALL_LOSE_ITEM, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.ALL_LOSE_ITEM},
            {MapGrid.BLANK, MapGrid.NULL, MapGrid.ALL_LOSE_ITEM, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.BACKWARD, MapGrid.NULL, MapGrid.BLANK, MapGrid.NULL, MapGrid.BACKWARD, MapGrid.NULL, MapGrid.BLANK},
            {MapGrid.START, MapGrid.NULL, MapGrid.GET_ITEM, MapGrid.GET_ITEM, MapGrid.RENDEZVOUS, MapGrid.ALL_GET_ITEM, MapGrid.CROSSROAD, MapGrid.BLANK, MapGrid.BLANK, MapGrid.NULL, MapGrid.GET_ITEM, MapGrid.BLANK, MapGrid.GET_ITEM}
    };
    private final int gridWidth = 60;
    private final int gridHeight = 60;
    private final GamePanelPiece[] piece;

    public GamePanelMap() {
        setPreferredSize(new Dimension(781, 721));
        setBackground(ColorScheme.LIGHT_ORANGE.getColor());
        piece = new GamePanelPiece[4];
        for (int i = 0; i < 4; i++) {
            piece[i] = new GamePanelPiece(i, gridWidth, gridHeight);
        }
    }

    public void moveUp(int i) {
        piece[i].Y--;
    }

    public void moveDown(int i) {
        piece[i].Y++;
    }

    public void moveLeft(int i) {
        piece[i].X--;
    }

    public void moveRight(int i) {
        piece[i].X++;
    }

    public void moveForward(int i) {
        switch (piece[i].X) {
            case 0, 6, 8, 12 -> {
                if (piece[i].Y == 0) {
                    moveRight(i);
                } else if (piece[i].keepDirection) {
                    moveRight(i);
                    piece[i].keepDirection = false;
                } else {
                    moveUp(i);
                }
            }
            case 2, 4, 10 -> {
                if (piece[i].Y == 11) {
                    moveRight(i);
                } else if (piece[i].keepDirection) {
                    moveRight(i);
                    piece[i].keepDirection = false;
                } else {
                    moveDown(i);
                }
            }
            case 1, 3, 5, 7, 9, 11 -> moveRight(i);
        }
        repaint();
    }

    public void moveBackward(int i) {
        switch (piece[i].X) {
            case 4, 10 -> moveUp(i);
            case 6, 12 -> moveDown(i);
            case 7 -> moveLeft(i);
        }
        repaint();
    }

    public void restart(int i) {
        piece[i].X = 0;
        piece[i].Y = 11;
        repaint();
    }

    public void setKeepDirection(int i) {
        piece[i].keepDirection = true;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        final int column = 13; //X axis grid number
        final int row = 12; //Y axis grid number
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (mapGrids[i][j] != MapGrid.NULL) {
                    g2.setPaint(Color.BLACK);
                    g2.drawRect(j * gridWidth, i * gridHeight, gridWidth, gridHeight);
                    switch (mapGrids[i][j]) {
                        case START -> g2.setPaint(ColorScheme.DARK_BLUE.getColor());
                        case GOAL -> g2.setPaint(ColorScheme.DARK_RED.getColor());
                        case BLANK -> g2.setPaint(ColorScheme.WHITE.getColor());
                        case GET_ITEM -> g2.setPaint(ColorScheme.LIGHT_GOLD.getColor());
                        case FORWARD -> g2.setPaint(ColorScheme.LIGHT_RED.getColor());
                        case BACKWARD -> g2.setPaint(ColorScheme.LIGHT_BLUE.getColor());
                        case CROSSROAD -> g2.setPaint(ColorScheme.DARK_GREEN.getColor());
                        case RENDEZVOUS -> g2.setPaint(ColorScheme.LIGHT_GREEN.getColor());
                        case ALL_GET_ITEM -> g2.setPaint(ColorScheme.GOLD.getColor());
                        case ALL_LOSE_ITEM -> g2.setPaint(ColorScheme.INDIGO.getColor());
                        case RESTART -> g2.setPaint(ColorScheme.DARK_GRAY.getColor());
                    }
                    g2.fill(new Rectangle2D.Double(j * gridWidth + 1, i * gridHeight + 1, gridWidth - 1, gridHeight - 1));
                }
            }
        }
        g2.setPaint(piece[0].getColor());
        g2.fill(piece[0].getShape());
        g2.setPaint(piece[1].getColor());
        g2.fill(piece[1].getShape());
        g2.setPaint(piece[2].getColor());
        g2.fill(piece[2].getShape());
        g2.setPaint(piece[3].getColor());
        g2.fill(piece[3].getShape());
    }
}