package y2022.day14;

import utilities.Vector2Int;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class View {

    private JFrame frame;
    private final BufferedImage cave;
    private BufferStrategy bufferStrategy;
    private final int scale;

    public View(BufferedImage cave, int scale, int left) {
        this.cave = cave;
        this.scale = scale;
        createAndShowGUI(left);
    }

    private void createAndShowGUI(int left) {
        // Sim View
        frame = new JFrame("Simulation");
        frame.setLocation(left, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = (JPanel) frame.getContentPane();

        int WIDTH = cave.getWidth() * scale;
        int HEIGHT = cave.getHeight() * scale;

        panel.setSize(WIDTH, HEIGHT);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        Canvas canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
    }

    public void render(Vector2Int grain, double time) {
        sleep(time);
        if (bufferStrategy == null) return; // View not ready yet
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.drawImage(cave, 0, 0, cave.getWidth() * scale, cave.getHeight() * scale, frame);
        if (grain != null) {
            g.setColor(Color.YELLOW);
            g.fillRect(grain.getX() * scale, grain.getY() * scale, scale, scale);
        }
        g.dispose();
        bufferStrategy.show();
    }

    private void sleep(double seconds) {
        try {
            Thread.sleep(Math.round(seconds * 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
