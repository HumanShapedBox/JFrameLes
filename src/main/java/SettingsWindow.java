import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;

    JButton btnStart = new JButton("Начинаем!");
    JLabel gameMode = new JLabel("Режим игры");
    JLabel gameField = new JLabel("Установленный размер поля:");
    JLabel winCount = new JLabel("Установленная длина:");
    JSlider fieldSlider = new JSlider(3,10);
    JSlider winLengthSlider = new JSlider(3,10);
    JRadioButton humanVsAi = new JRadioButton("Человек против компьютера");
    JRadioButton humanVsHuman = new JRadioButton("Человек против человека");
    ButtonGroup gameModeButtonsGroup = new ButtonGroup();
    JPanel panBottom;

    SettingsWindow(GameWindow gameWindow){
        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        panBottom = new JPanel(new GridLayout(9, 1));
        panBottom.add(gameMode);
        panBottom.add(humanVsAi);
        panBottom.add(humanVsHuman);
        panBottom.add(gameField);
        panBottom.add(fieldSlider);
        panBottom.add(winCount);
        panBottom.add(winLengthSlider);
        panBottom.add(btnStart);
        humanVsAi.setSelected(true);

        fieldSlider.addChangeListener(e -> gameField.setText("Установленный размер поля: " + fieldSlider.getValue()));

        winLengthSlider.addChangeListener(e -> winCount.setText("Установленная длина: " + winLengthSlider.getValue()));
        // это то же самое)
//        winLengthSlider.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                winCount.setText("Установленная длина: " + winLengthSlider.getValue());
//            }
//        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.startNewGame(0, 3 , 3, 3);
                setVisible(false);
            }
        });
        add(panBottom);
        add(btnStart);
    }
}
