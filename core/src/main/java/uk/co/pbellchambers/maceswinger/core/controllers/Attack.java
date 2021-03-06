package uk.co.pbellchambers.maceswinger.core.controllers;

import org.magnos.entity.Entity;
import org.magnos.entity.EntityIterator;
import org.magnos.entity.filters.ComponentFilter;
import org.magnos.entity.vals.IntVal;
import uk.co.pbellchambers.maceswinger.Vector2;
import uk.co.pbellchambers.maceswinger.client.GameClient;
import uk.co.pbellchambers.maceswinger.core.Core;

public class Attack {

    public static void attack(Entity e, Object updateState, IntVal direction)//0 left, 1 right
    {
        if (!e.has(Core.Components.attackCooldown)) {
            return;
        }
        IntVal attackCooldown = e.get(Core.Components.attackCooldown);
        if (attackCooldown.v == 0) {
            attackCooldown.v = 35;
        } else {
            GameClient program = (GameClient) updateState;
            for (Entity ent : new EntityIterator(program.entities, new ComponentFilter(Core.Components.collider))) {
                if (!ent.has(Core.Components.velocity)) {
                    continue;
                }
                if (ent == e) {
                    continue;
                }
                if (direction.v == 0) {
                    if (e.get(Core.Components.collider).overlaps(new Vector2(-40, 10), ent.get(Core.Components.collider))) {
                        System.out.println(ent.id);
                        Vector2 v = ent.get(Core.Components.velocity);
                        v.x -= 1;
                        v.y += 10;
                    }
                }
                if (direction.v == 1) {
                    if (e.get(Core.Components.collider).overlaps(new Vector2(40, 10), ent.get(Core.Components.collider))) {
                        System.out.println(ent.id);
                        Vector2 v = ent.get(Core.Components.velocity);
                        v.x += 1;
                        v.y += 10;
                    }
                }
            }
            attackCooldown.v -= 1;
        }
    }
}
