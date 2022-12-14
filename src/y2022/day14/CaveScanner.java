package y2022.day14;

import utilities.Vector2Int;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class CaveScanner {

    List<List<Vector2Int>> rockLines;
    private BufferedImage cave;
    private int xOffset = Integer.MAX_VALUE;

    public BufferedImage getCave() {
        return cave;
    }

    public void parseLines(List<String> lines) {
        rockLines = lines.stream()
                .map(line -> Arrays.stream(line.split(" -> "))
                        .map(s -> s.split(","))
                        .map(n -> new Vector2Int(Integer.parseInt(n[0]), Integer.parseInt(n[1])))
                        .toList())
                .toList();
    }

    public boolean pourSandGrain(Vector2Int grain, View view) {
        grain.move(-xOffset, 0);
        if (getColor(grain) != Color.BLACK.getRGB()) return false;
        while (true) {
            view.render(grain, 0);
            var pos = grain.add(Vector2Int.down());
            if (isOutsideCave(pos)) {
                view.render(null, 1 / 60d);
                return false;
            }
            if (getColor(pos) == Color.BLACK.getRGB()) {
                grain = pos;
                continue;
            }
            pos = grain.add(new Vector2Int(-1, 1));
            if (isOutsideCave(pos)) {
                view.render(null, 1 / 60d);
                return false;
            }
            if (getColor(pos) == Color.BLACK.getRGB()) {
                grain = pos;
                continue;
            }
            pos = grain.add(new Vector2Int(1, 1));
            if (isOutsideCave(pos)) {
                view.render(null, 1 / 60d);
                return false;
            }
            if (getColor(pos) == Color.BLACK.getRGB()) {
                grain = pos;
                continue;
            }
            setColor(grain, Color.YELLOW);
            return true;
        }
    }

    private boolean isOutsideCave(Vector2Int pos) {
        return (pos.getX() < 0 || pos.getX() >= cave.getWidth() || pos.getY() >= cave.getHeight());
    }

    private int getColor(Vector2Int pos) {
        return cave.getRGB(pos.getX(), pos.getY());
    }

    private void setColor(Vector2Int pos, Color color) {
        cave.setRGB(pos.getX(), pos.getY(), color.getRGB());
    }

    public void drawCave1() {
        // find bounds
        int maxX = 0, maxY = 0;
        for (var rockLine : rockLines) {
            for (var rock : rockLine) {
                maxX = Math.max(rock.getX(), maxX);
                maxY = Math.max(rock.getY(), maxY);
                xOffset = Math.min(rock.getX(), xOffset);
            }
        }
        // draw actual image
        cave = new BufferedImage((maxX - xOffset) + 1, maxY + 1, BufferedImage.TYPE_INT_RGB);
        for (var rockLine : rockLines) {
            for (int i = 1; i < rockLine.size(); i++) {
                var prev = rockLine.get(i - 1);
                var rock = rockLine.get(i);
                for (int y = Math.min(prev.getY(), rock.getY()); y <= Math.max(prev.getY(), rock.getY()); y++) {
                    for (int x = Math.min(prev.getX(), rock.getX()); x <= Math.max(prev.getX(), rock.getX()); x++) {
                        cave.setRGB(x - xOffset, y, Color.GRAY.getRGB());
                    }
                }
            }
        }
    }

    public void drawCave2() {
        // find bounds
        int maxX = 0, maxY = 0;
        for (var rockLine : rockLines) {
            for (var rock : rockLine) {
                maxX = Math.max(rock.getX(), maxX);
                maxY = Math.max(rock.getY(), maxY);
                xOffset = Math.min(rock.getX(), xOffset);
            }
        }

        // draw actual image
        cave = new BufferedImage(maxX + (2 * maxY) - xOffset, maxY + 3, BufferedImage.TYPE_INT_RGB);
        xOffset -= maxY;
        for (var rockLine : rockLines) {
            for (int i = 1; i < rockLine.size(); i++) {
                var prev = rockLine.get(i - 1);
                var rock = rockLine.get(i);
                for (int y = Math.min(prev.getY(), rock.getY()); y <= Math.max(prev.getY(), rock.getY()); y++) {
                    for (int x = Math.min(prev.getX(), rock.getX()); x <= Math.max(prev.getX(), rock.getX()); x++) {
                        cave.setRGB(x - xOffset, y, Color.GRAY.getRGB());
                    }
                }
            }
        }
        for (int x = 0; x < cave.getWidth(); x++) {
            cave.setRGB(x, maxY + 2, Color.GRAY.getRGB());
        }
    }

}
