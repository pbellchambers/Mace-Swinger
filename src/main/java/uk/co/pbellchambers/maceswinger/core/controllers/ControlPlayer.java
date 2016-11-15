package uk.co.pbellchambers.maceswinger.core.controllers;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.magnos.entity.Control;
import org.magnos.entity.Entity;
import org.magnos.entity.vals.IntVal;
import uk.co.pbellchambers.maceswinger.Components;
import uk.co.pbellchambers.maceswinger.Vector2;
import uk.co.pbellchambers.maceswinger.client.render.SpriteRenderer;
import uk.co.pbellchambers.maceswinger.core.Core;

public class ControlPlayer implements Control {

    @Override
    public void update(Entity e, Object updateState) {
        Vector2 velocity = e.get(Core.Components.velocity);
        Vector2 pos = e.get(Components.position);
        IntVal direction = e.get(Components.direction);
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            Jump.jump(e, updateState, velocity);
        }
        if (Mouse.isButtonDown(0)) {
            Attack.attack(e, updateState, direction);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            velocity.x -= 0.5f;
            direction.v = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            velocity.x += 0.5f;
            direction.v = 1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            // open inventory
        }

        SpriteRenderer.mainCamera.x = pos.x - 400 + 16;
        SpriteRenderer.mainCamera.y = pos.y - 300 + 16;
    }
}
