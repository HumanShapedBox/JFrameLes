import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame{

    private static final int WINDOW_HEIGHT = 555;
    private static final int WINDOW_WIDTH = 507;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;

    JButton btnStart = new JButton("Играть!");
    JButton btnExit = new JButton("Уйти");

    Map map;
    SettingsWindow settings;

    GameWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Палочки-кружочки");
        setResizable(false);

        map = new Map();
        settings = new SettingsWindow(this);

        btnExit.addActionListener(e -> System.exit(0));
        btnStart.addActionListener(e -> settings.setVisible(true));

//        btnStart.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {          Это развёрнутая лямбда
//                settings.setVisible(true);
//            }
//        });

        settings.setVisible(true);

        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnExit);
        add(panBottom, BorderLayout.SOUTH);
        add(map);

        setVisible(true);
    }

    void startNewGame(int mode, int fSzX, int fSzY, int wLen){
        map.startNewGame(mode, fSzX, fSzY, wLen);
    }

}
