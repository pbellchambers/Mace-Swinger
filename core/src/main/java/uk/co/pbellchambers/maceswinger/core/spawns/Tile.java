package uk.co.pbellchambers.maceswinger.core.spawns;

import org.magnos.entity.Entity;
import org.magnos.entity.EntityList;
import uk.co.pbellchambers.maceswinger.Animation;
import uk.co.pbellchambers.maceswinger.Components;
import uk.co.pbellchambers.maceswinger.Rectangle;
import uk.co.pbellchambers.maceswinger.client.render.AnimationRenderer;
import uk.co.pbellchambers.maceswinger.client.render.lighting.Block;
import uk.co.pbellchambers.maceswinger.map.MapObject;

public class Tile implements MapObject {

    @Override
    public Object spawn(EntityList list, int x, int y, String... params) {
        Entity tile = new Entity();
        tile.add(Components.animation);
        tile.set(Components.animation, new Animation(new Animation.Frame(params[0], 50)));

        tile.add(Components.block);
        tile.set(Components.block, new Block(32, 32));

        tile.add(Components.position);
        tile.add(uk.co.pbellchambers.maceswinger.core.Core.Components.collider);

        tile.setRenderer(new AnimationRenderer("tileset"));

        tile.get(Components.position).x = x * 32;
        tile.get(Components.position).y = y * 32;
        tile.set(uk.co.pbellchambers.maceswinger.core.Core.Components.collider, new Rectangle(x * 32, y * 32, 32, 32));
        list.add(tile);
        return tile;
    }

    public String toString() {
        return "tile";
    }
}
