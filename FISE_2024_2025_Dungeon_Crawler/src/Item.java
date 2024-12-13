import java.awt.*;

public class Item extends SolidSprite {
    private String name;  // Name of the item

    public Item(double x, double y, Image image, double width, double height, String name) {
        super(x, y, image, width, height);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
