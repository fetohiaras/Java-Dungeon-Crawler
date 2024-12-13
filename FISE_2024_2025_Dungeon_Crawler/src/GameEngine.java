import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameEngine implements Engine, KeyListener {
    private DynamicSprite hero;
    private ArrayList<Item> items; // Lista de itens no ambiente
    private boolean showInventory = false; // Controle de exibição do inventário
    private boolean gameOver = false;

    public GameEngine(DynamicSprite hero, ArrayList<Item> items) {
        this.hero = hero;
        this.items = items;
    }

    @Override
    public void update() {
        hero.update(items); // Atualiza o estado do herói e verifica colisões com itens
        if (!gameOver) {
            hero.update(items);
            if (hero.isDead()) {
                gameOver = true; // Sinaliza o fim do jogo
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }    

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_I:
                showInventory = !showInventory; // Alterna a exibição do inventário
                System.out.println("Inventário " + (showInventory ? "aberto" : "fechado")); // Debug
                break;
        }
    }

    public boolean isShowInventory() {
        return showInventory;
    }

    public DynamicSprite getHero() {
        return hero;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
