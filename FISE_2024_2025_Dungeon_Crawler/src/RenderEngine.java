import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;
    private GameEngine gameEngine; // Referência à GameEngine para verificar o estado do inventário

    public RenderEngine(JFrame jFrame, GameEngine gameEngine) {
        this.renderList = new ArrayList<>();
        this.gameEngine = gameEngine;
    }

    public void addToRenderList(Displayable displayable) {
        if (!renderList.contains(displayable)) {
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayables) {
        renderList.addAll(displayables);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (gameEngine.isGameOver()) {
            renderGameOverScreen(g); // Renderiza a tela de "GAME OVER"
            return;
        }

        // Renderiza o jogo normalmente
        for (Displayable renderObject : renderList) {
            renderObject.draw(g);

            if (renderObject instanceof DynamicSprite dynamicSprite) {
                dynamicSprite.drawHealthBar(g, 0, 10);
            }
        }

        if (gameEngine.isShowInventory()) {
            renderInventory(g);
        }
    }

    private void renderGameOverScreen(Graphics g) {
        // Tela branca
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Texto "GAME OVER"
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String message = "GAME OVER";
        FontMetrics metrics = g.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(message)) / 2;
        int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        g.drawString(message, x, y);
    }


    private void renderInventory(Graphics g) {
        // Fundo do inventário
        g.setColor(Color.BLACK);
        g.fillRect(50, 50, 300, 200);
    
        // Texto do título
        g.setColor(Color.WHITE);
        g.drawString("Inventory:", 60, 70);
    
        // Recupera o inventário do herói
        DynamicSprite hero = gameEngine.getHero();
        ArrayList<Item> inventory = hero.getInventory();
    
        // Renderiza cada item no inventário
        int x = 60, y = 90;
        int itemsPerRow = 5; // Número de itens por linha
        int itemSpacing = 80; // Espaçamento horizontal entre itens
    
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
    
            // Desenha o sprite do item
            g.drawImage(item.image, x, y, null);
    
            // Desenha o nome do item logo abaixo do sprite
            g.setColor(Color.WHITE);
            g.drawString(item.getName(), x, y + (int) item.height + 50);
    
            // Ajusta a posição horizontalmente
            x += itemSpacing;
    
            // Se atingir o limite da linha, passa para a próxima linha
            if ((i + 1) % itemsPerRow == 0) {
                x = 60; // Reinicia a posição horizontal
                y += (int) item.height + 40; // Passa para a próxima linha
            }
        }
    }
    
    
    


    @Override
    public void update() {
        repaint();
    }
}
