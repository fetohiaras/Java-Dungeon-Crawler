import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public Main() throws Exception {
        // Criação da janela do jogo
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(400, 600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Criação do herói
        DynamicSprite hero = new DynamicSprite(70, 80,
                ImageIO.read(new File("img/heroTileSheetLowRes.png")), 48, 50);

        // Inicializa o playground (ambiente e itens)
        Playground level = new Playground("data/level1.txt");
        ArrayList<Item> items = level.getItemList(); // Recupera os itens carregados do playground

        // Inicializa os motores do jogo
        gameEngine = new GameEngine(hero, items);
        renderEngine = new RenderEngine(displayZoneFrame, gameEngine);
        physicEngine = new PhysicEngine();

        // Configuração de timers para atualizar os motores
        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        // Adiciona componentes ao JFrame
        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        // Adiciona sprites ao renderizador
        renderEngine.addToRenderList(level.getSpriteList());
        
        // Converte itens para Displayable e os adiciona ao renderizador
        ArrayList<Displayable> displayableItems = new ArrayList<>(items);
        renderEngine.addToRenderList(displayableItems);

        renderEngine.addToRenderList(hero);

        // Configura o ambiente para o motor físico
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

        // Adiciona o listener de teclado
        displayZoneFrame.addKeyListener(gameEngine);
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
    }
}