package uk.co.pbellchambers.maceswinger.core.spawns;

import org.magnos.entity.Entity;
import org.magnos.entity.EntityList;
import org.magnos.entity.vals.IntVal;
import uk.co.pbellchambers.maceswinger.Animation;
import uk.co.pbellchambers.maceswinger.Components;
import uk.co.pbellchambers.maceswinger.Rectangle;
import uk.co.pbellchambers.maceswinger.Vector2;
import uk.co.pbellchambers.maceswinger.client.render.AnimationRenderer;
import uk.co.pbellchambers.maceswinger.client.render.SpriteRenderer;
import uk.co.pbellchambers.maceswinger.core.Core;
import uk.co.pbellchambers.maceswinger.map.MapObject;
import uk.co.pbellchambers.maceswinger.server.GameServer;

/**
 * Player handling class
 *
 * @since Feb 2, 2014
 */
public class PlayerSpawn implements MapObject {

    @Override
    public Object spawn(EntityList list, int x, int y, String... params) {
        GameServer.playerSpawn.x = x;
        GameServer.playerSpawn.y = y;
        return null;
    }

    /**
     * Creates a new player entity at given coordinates.
     *
     * @param x X value of coordinate
     * @param y Y value of coordinate
     * @return A fully ready player entity
     */
    public static Entity create(int x, int y) {
        Entity player = new Entity();

        player.add(Core.Controllers.player);
        player.add(Core.Controllers.velocity);
        player.add(Core.Controllers.gravity);

        player.add(Components.animation);
        player.add(Components.position);
        player.add(Components.direction);

        player.add(Components.heldItem);

        player.add(Core.Components.collider);
        player.add(Core.Components.velocity);
        player.add(Core.Components.inventory);
        player.add(Core.Components.jumpCooldown);
        player.add(Core.Components.attackCooldown);

        player.set(Components.animation, new Animation(new Animation.Frame("player_walk_1", 5), new Animation.Frame("player_walk_2", 5), new Animation.Frame("player_walk_3", 5), new Animation.Frame("player_walk_4", 5)));
        player.set(Components.position, new Vector2(x * 32, y * 32));
        player.set(Components.direction, new IntVal(0));
        //player.set(Components.heldItem, new ItemVal());
        player.set(Core.Components.jumpCooldown, new IntVal(0));
        player.set(Core.Components.attackCooldown, new IntVal(0));
        player.set(Core.Components.collider, new Rectangle(x * 32 + 3, y * 32 + 1, 17, 48));

        player.setRenderer(new AnimationRenderer("mob"));

        SpriteRenderer.mainCamera = new Vector2(x, y);

        return player;
    }

    public String toString() {
        return "Player Spawn";
    }
}