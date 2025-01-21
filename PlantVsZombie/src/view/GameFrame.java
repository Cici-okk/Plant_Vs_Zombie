package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.event.WindowEvent;

/**
 * GameFrame is a custom JFrame that sets up the main window of the game.
 * It handles the layout and events for the game window, including exiting
 * the game when the window is closed.
 */
public class GameFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private BorderLayout borderLayout1 = new BorderLayout();

    /**
     * Initializes the GameFrame by setting up the content pane layout.
     * This method configures the layout manager and prepares the frame for display.
     * @throws Exception if there is an error during initialization.
     */
    private void initialize() throws Exception {
        contentPane = (JPanel) this.getContentPane(); // Cast as JPanel
        contentPane.setLayout(borderLayout1);
    }

    /**
     * Processes window events. Specifically, it overrides the default behavior
     * to ensure that the application exits when the window is closed.
     * @param e the WindowEvent object containing information about the window event.
     */
    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0); // Exit the application when the window is closed.
        }
    }

    /**
     * Constructs a new GameFrame and sets up the necessary event handling.
     * This constructor ensures that the key event mask is enabled and calls the
     * initialize method to set up the content pane.
     */
    public GameFrame() {
        enableEvents(AWTEvent.KEY_EVENT_MASK); // Enable key event handling
        try {
            initialize(); // Initialize the frame's content pane
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
