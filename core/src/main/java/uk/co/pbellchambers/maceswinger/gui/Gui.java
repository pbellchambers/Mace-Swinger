package uk.co.pbellchambers.maceswinger.gui;

import uk.co.pbellchambers.maceswinger.client.GameClient;
import uk.co.pbellchambers.maceswinger.utils.Textures;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public abstract class Gui {

    public GameClient game;
    protected Random rand;

    public int width;
    public int height;
    protected boolean pauseGame;
    protected float alpha = 1.0f;
    protected int tick;

    public Gui(GameClient game, int width, int height) {
        this.game = game;
        this.rand = new Random();
        this.width = width;
        this.height = height;

    }

    protected void renderImage(int tex, float width, float height, float x, float y) {
        glPushMatrix();
        glBindTexture(GL_TEXTURE_2D, Textures.textureID[tex]);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2f(x, y);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y + height);
        glTexCoord2f(0, 0);
        glVertex2f(x, y + height);
        glEnd();
        glPopMatrix();

    }

    protected void fillScreen(float r, float g, float b, float a) {
        glColor4f(r, g, b, a);
        glBegin(GL_QUADS);
        glVertex2f(0, 0);
        glVertex2f(GameClient.width, 0);
        glVertex2f(GameClient.width, GameClient.height);
        glVertex2f(0, GameClient.width);
        glEnd();
        glPopMatrix();
        glColor4f(1, 1, 1, 1);
    }

    public void render() {

    }

    public void tick(int ticks) {
        if (this.tick < 20) {
            //this.alpha-=0.05;

        }
    }

    public abstract void guiActionPerformed(int elementId, int action);

    public boolean pausesGame() {
        return pauseGame;
    }

    public void closeGui() {

        //game.hideGui();
    }

}
