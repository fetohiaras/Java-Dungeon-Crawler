import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {
    private Direction direction = Direction.EAST;
    private double speed = 5;
    private double timeBetweenFrame = 250;
    private boolean isWalking = true;
    private final int spriteSheetNumberOfColumn = 10;
    private int maxHealth = 100;
    private int currentHealth = 100;

    private long lastUpdateTime;
    private ArrayList<Item> inventory;  // Lista de inventário para armazenar itens coletados

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
        lastUpdateTime = System.currentTimeMillis(); // Inicializa o temporizador
        inventory = new ArrayList<>(); // Inicializa o inventário
    }

    // Retorna o inventário
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    // Adiciona um item ao inventário
    public void collectItem(Item item) {
        inventory.add(item);
    }

    // Atualiza o estado do personagem
    public void update(ArrayList<Item> items) {
        // Lógica de cura a cada 1 segundo
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > 1000) {
            heal(1); // Cura 1 ponto de vida
            lastUpdateTime = currentTime;
        }
    
        // Verifica colisões com itens
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (this.getHitBox().intersects(item.getHitBox())) {
                collectItem(item); // Adiciona ao inventário
                items.remove(i);   // Remove da lista de itens no ambiente
                i--;               // Ajusta o índice
    
                // Verifica se o item coletado é uma poção
                if ("Potion".equals(item.getName())) {
                    int lostHP = maxHealth / 2; // Calcula 50% da vida atual
                    takeDamage(lostHP);             // Aplica o dano
                    System.out.println("Item collected: " + item.getName() + ". 50% HP Loss!");
                } else {
                    System.out.println("Item collected: " + item.getName());
                }

                if (isDead()) {
                    System.out.println("Hero died!"); // Depuração
                }
            }
        }
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) currentHealth = 0; // Não permite vida negativa
    }

    public void heal(int healAmount) {
        currentHealth += healAmount;
        if (currentHealth > maxHealth) currentHealth = maxHealth; // Não excede a vida máxima
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }    

    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch (direction) {
            case EAST:
                moved.setRect(super.getHitBox().getX() + speed, super.getHitBox().getY(),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:
                moved.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY(),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:
                moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() - speed,
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:
                moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed,
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment) {
            if ((s instanceof SolidSprite) && (s != this)) {
                if (((SolidSprite) s).intersect(moved)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void move() {
        switch (direction) {
            case NORTH -> this.y -= speed;
            case SOUTH -> this.y += speed;
            case EAST -> this.x += speed;
            case WEST -> this.x -= speed;
        }
    }

    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            move();
        }
    }

    @Override
    public void draw(Graphics g) {
        int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberOfColumn);

        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((direction.getFrameLineNumber() + 1) * this.height), null);
    }

    public void drawHealthBar(Graphics g, int offsetX, int offsetY) {
        int barWidth = 50; // Largura da barra de vida
        int barHeight = 8; // Altura da barra de vida
        int healthBarWidth = (int) ((double) currentHealth / maxHealth * barWidth);

        // Desenha o fundo (borda preta)
        g.setColor(Color.BLACK);
        g.fillRect((int) x + offsetX, (int) y - offsetY, barWidth, barHeight);

        // Desenha o nível de saúde - muda de cor de acordo com o estado de vida
        if (currentHealth > maxHealth / 2) {
            g.setColor(Color.GREEN); // Vida cheia -> verde
        } else if (currentHealth > maxHealth / 4) {
            g.setColor(Color.YELLOW); // Vida média -> amarelo
        } else {
            g.setColor(Color.RED); // Vida crítica -> vermelho
        }
        g.fillRect((int) x + offsetX, (int) y - offsetY, healthBarWidth, barHeight);
    }
}
