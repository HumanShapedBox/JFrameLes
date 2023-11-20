import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    private static final Random RANDOM = new Random();
    private static final int DOT_PADDING = 5;

    private int gameOverType;
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;

    private static final String MSG_WIN_HUMAN = "Вы победили!";
    private static final String MSG_WIN_AI = "Вы проиграли";
    private static final String MSG_DRAW = "Ничья!";


    private final int HUMAN_DOT = 1;
    private final int AI_DOT = 2;
    private final int EMPTY_DOT = 0;
    private int fieldSyzeY = 3;
    private int fieldSizeX = 3;
    private char[][] field;

    private int panelWidth;
    private int panelHeight;
    private int cellWidth;
    private int cellHeight;

    private boolean isGameOver;
    private boolean isInitialized;

    Map(){
        setBackground(Color.DARK_GRAY);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e){
                update(e);
            }
        });
        isInitialized = false;
    }

    void startNewGame(int mode, int fSzX, int fSzY, int wLen){
        System.out.printf("Mode: %d;\nSize: x=%d, y=%d;\nWinLength: %d\n", mode, fSzX, fSzY, wLen);
        initMap();
        isGameOver = false;
        isInitialized = true;
        repaint();
    }

    private void update(MouseEvent e){
        if(isGameOver || !isInitialized) return;
        int cellX = e.getX()/cellWidth;
        int cellY = e.getY()/cellHeight;
        if(!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) return;
        field[cellY][cellX] = HUMAN_DOT;
        repaint();
        if(checkEndGame(HUMAN_DOT, STATE_WIN_HUMAN)) return;
        aiTurn();
        repaint();
        if(checkEndGame(AI_DOT, STATE_WIN_AI)) return;
    }

    private void render(Graphics g){
        if(!isInitialized) return;;
        panelWidth = getWidth();
        panelHeight = getHeight();
        cellHeight = panelHeight / 3;
        cellWidth = panelWidth / 3;

        g.setColor(Color.LIGHT_GRAY);
        for(int h = 0; h < 3; h++){
            int y = h * cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }
        for(int w = 0; w < 3; w++){
            int x = w * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }

        for(int y = 0; y < fieldSyzeY; y++){
            for(int x = 0; x < fieldSizeX; x++){
                if(field[y][x] == EMPTY_DOT) continue;

                if(field[y][x] == HUMAN_DOT){
                    g.setColor(Color.GREEN);
                    g.fillRect(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else if(field[y][x] == AI_DOT){
                    g.setColor(Color.YELLOW);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                }else {
                    throw new RuntimeException("Недопустимое значение " + field[y][x]);
                }
            }
        }
        if(isGameOver) showMassageGameOver(g);
    }

    private void initMap(){
        fieldSyzeY = 3;
        fieldSizeX = 3;
        field = new char[fieldSyzeY][fieldSizeX];
        for(int i = 0; i < fieldSyzeY; i++){
            for(int j = 0; j < fieldSizeX; j++){
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    private boolean isValidCell(int x, int y){
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSyzeY;
    }

    private boolean isEmptyCell(int x, int y){
        return field[y][x] == EMPTY_DOT;
    }

    private boolean isMapFull(){
        for(int i = 0; i < fieldSyzeY; i++){
            for(int j = 0; j < fieldSizeX; j++){
                if(field[i][j] == EMPTY_DOT) return false;
            }
        }
        return true;
    }

    private void aiTurn(){
        int x, y;
        do{
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSyzeY);
        }while (!isEmptyCell(x, y));
        field[y][x] = AI_DOT;
    }

    //Так не делай
    private boolean checkWin(int c){
        if(field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
        if(field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
        if(field[2][0] == c && field[2][1] == c && field[2][2] == c) return true;

        if(field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
        if(field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
        if(field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;

        if(field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
        if(field[0][2] == c && field[1][1] == c && field[2][0] == c) return true;

        return false;
    }

    private boolean checkEndGame(int dot, int gameOverType){
        if(checkWin(dot)){
            this.gameOverType = gameOverType;
            isGameOver = true;
            repaint();
            return true;
        }

        if(isMapFull()){
            this.gameOverType = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }

        return false;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        render(g);
    }

    private void showMassageGameOver(Graphics g){
        g.setColor(Color.lightGray);
        g.fillRect(0, 200, getWidth(), 70);
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Areal", Font.BOLD, 48));
        switch (gameOverType){
            case STATE_DRAW -> g.drawString(MSG_DRAW, 180, getHeight() / 2);
            case STATE_WIN_AI -> g.drawString(MSG_WIN_AI, 100, getHeight() / 2);
            case STATE_WIN_HUMAN -> g.drawString(MSG_WIN_HUMAN, 100, getHeight() / 2);
            default -> throw new RuntimeException("Oops" + gameOverType);
        }
    }
}
