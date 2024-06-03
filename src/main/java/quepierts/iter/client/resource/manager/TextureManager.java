package quepierts.iter.client.resource.manager;

import quepierts.iter.client.resource.ResourceID;
import quepierts.iter.client.resource.texture.Texture;
import quepierts.iter.util.IterLogManager;
import quepierts.iter.util.ResourceLoader;
import quepierts.iter.util.ResourceManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class TextureManager extends ResourceManager<Texture> {
    @Override
    public void load() {
        InputStream resource;
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, -1);

        def = Texture.loadTexture(image);

        try {
            Map<String, InputStream> png = ResourceLoader.getAllResources("assets/texture", "png");
            for (String fileName : png.keySet()) {
                resource = png.get(fileName);
                image = ImageIO.read(resource);

                resources.put(fileName, Texture.loadTexture(image));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        IterLogManager.getLogger().info("[Iter Initialization] Texture Loaded");
    }

    @Override
    public void cleanUp() {
        for (Texture texture : this.resources.values()) {
            texture.cleanUp();
        }

        this.resources.clear();

        IterLogManager.getLogger().info("[Iter Clean Up] Texture Unloaded");
    }

    public void bindTexture(String id) {
        Texture texture = this.resources.get(id);
        if (texture != null) {
            texture.bindTexture();
        }
    }

    public void bindTexture(ResourceID resourceID) {
        this.bindTexture(resourceID.name);
    }
}
