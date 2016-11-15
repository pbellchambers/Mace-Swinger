package uk.co.pbellchambers.maceswinger.client.render;

import org.magnos.entity.Entity;
import org.magnos.entity.Renderer;
import uk.co.pbellchambers.maceswinger.*;

public class SpriteRenderer implements Renderer {

    public static Vector2 mainCamera;
    public String spriteSheet;
    public String sprite;

    public SpriteRenderer(String spriteSheet, String sprite) {
        this.spriteSheet = spriteSheet;
        this.sprite = sprite;
    }

    @Override
    public Renderer create(Entity e) {
        return new SpriteRenderer(spriteSheet, sprite);
    }

    @Override
    public void begin(Entity e, Object drawState) {
        Sprite t = Assets.get(spriteSheet, sprite);

        Color col = Color.white();
        if (e.has(Components.color)) {
            col = e.get(Components.color);
        }
        if (e.has(Components.position)) {
            Vector2 pos = new Vector2(e.get(Components.position));

            t.draw(pos, col);

        }
    }

    @Override
    public void end(Entity e, Object drawState) {
    }

    @Override
    public void destroy(Entity e) {
    }

    @Override
    public void notify(Entity e, int message) {
    }
}