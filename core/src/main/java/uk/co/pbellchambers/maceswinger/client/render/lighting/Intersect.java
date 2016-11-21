package uk.co.pbellchambers.maceswinger.client.render.lighting;

import org.joml.Vector2f;

public class Intersect {

    public float distance;
    public float angle;
    public Vector2f pos;

    public Intersect(Vector2f pos, float d, float a) {
        this.pos = pos;
        this.angle = a;
        this.distance = d;
    }
}
