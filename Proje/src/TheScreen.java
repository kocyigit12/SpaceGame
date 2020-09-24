import javax.swing.*;
import java.awt.*;

public class TheScreen extends JFrame{
    public TheScreen(String title) throws HeadlessException {
        super(title);
    }

    public static void main(String[] args) {


        TheScreen screen = new TheScreen("Space Game");
        screen.setResizable(false);// we want to focus on JPanel not JFrame so that's why these are false
        screen.setFocusable(false);

        screen.setSize(800,600);

        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();

        game.requestFocus(); // that means , it will focus on the users commend(such as, keyboard actions)

        game.addKeyListener(game);  // it will read the keyboard

        game.setFocusable(true); // now we send  "the focus" on JPanel
        game.setFocusTraversalKeysEnabled(false);

        screen.add(game);

        screen.setVisible(true);
    }
}
