import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Playground {
    private ArrayList<Sprite> environment = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>(); // Lista para itens no ambiente

    public Playground(String pathName) {
        System.out.println("Loading map...");
        try {
            final Image imageTree = ImageIO.read(new File("img/tree.png"));
            final Image imageGrass = ImageIO.read(new File("img/grass.png"));
            final Image imageRock = ImageIO.read(new File("img/rock.png"));
            final Image imagePotion = ImageIO.read(new File("img/potion.png")); 
    
            final int imageWidth = imageGrass.getWidth(null);
            final int imageHeight = imageGrass.getHeight(null);
    
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int lineNumber = 0;
    
            while (line != null) {
                for (int columnNumber = 0; columnNumber < line.length(); columnNumber++) {
                    char element = line.charAt(columnNumber);
                    switch (element) {
                        case 'T':
                            environment.add(new SolidSprite(columnNumber * imageWidth, lineNumber * imageHeight, imageTree, imageWidth, imageHeight));
                            break;
                        case ' ':
                            environment.add(new Sprite(columnNumber * imageWidth, lineNumber * imageHeight, imageGrass, imageWidth, imageHeight));
                            break;
                        case 'R':
                            environment.add(new SolidSprite(columnNumber * imageWidth, lineNumber * imageHeight, imageRock, imageWidth, imageHeight));
                            break;
                        case 'P':
                            items.add(new Item(columnNumber * imageWidth, lineNumber * imageHeight, imagePotion, 32, 32, "Poção"));
                            environment.add(new Sprite(columnNumber * imageWidth, lineNumber * imageHeight, imageGrass, imageWidth, imageHeight));
                            break;
                    }
                }
                lineNumber++;
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }
        return solidSpriteArrayList;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }

    // Método para obter a lista de itens
    public ArrayList<Item> getItemList() {
        return items;
    }
}
