import controller.GameController;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        GameController gameController = null;
        try {
            gameController = new GameController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        EventQueue.invokeLater(gameController);
    }

}
