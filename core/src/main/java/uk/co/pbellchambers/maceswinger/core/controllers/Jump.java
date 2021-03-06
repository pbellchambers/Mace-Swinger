package uk.co.pbellchambers.maceswinger.core.controllers;

import org.magnos.entity.Entity;
import org.magnos.entity.EntityIterator;
import org.magnos.entity.filters.ComponentFilter;
import org.magnos.entity.vals.IntVal;
import uk.co.pbellchambers.maceswinger.Vector2;
import uk.co.pbellchambers.maceswinger.client.GameClient;
import uk.co.pbellchambers.maceswinger.core.Core;

public class Jump {

    public static void jump(Entity e, Object updateState, Vector2 velocity) {
        if (!e.has(Core.Components.jumpCooldown)) {
            return;
        }
        IntVal jumpCooldown = e.get(Core.Components.jumpCooldown);
        if (jumpCooldown.v == 0) {
            jumpCooldown.v = 35;
        } else {
            GameClient program = (GameClient) updateState;
            for (Entity ent : new EntityIterator(program.entities, new ComponentFilter(Core.Components.collider))) {
                if (ent != e) {
                    if (e.get(Core.Components.collider).overlaps(new Vector2(0, -1), ent.get(Core.Components.collider))) {
                        velocity.y = 16;
                    }
                }
            }
            if (velocity.y > -1) {
                velocity.y += 0.3f;
            }
            jumpCooldown.v -= 1;
        }
    }
}
