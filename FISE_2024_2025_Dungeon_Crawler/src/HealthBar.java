import java.awt.*;

public class HealthBar implements Displayable {
    private int x;
    private int y;
    private int width;
    private int height;
    private int currentHealth;
    private int maxHealth;

    public HealthBar(int x, int y, int width, int height, int maxHealth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    // Setter methods for x and y position
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(Graphics g) {
        // Draw the health bar (you can adjust the drawing logic as needed)
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);

        // Draw the current health (a filled portion of the health bar)
        g.setColor(Color.GREEN);
        g.fillRect(x, y, (int) (width * ((double) currentHealth / maxHealth)), height);
    }

    // Getter methods for health status
    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
