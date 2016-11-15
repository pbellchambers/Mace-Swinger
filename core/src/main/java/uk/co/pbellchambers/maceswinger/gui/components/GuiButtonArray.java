package uk.co.pbellchambers.maceswinger.gui.components;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;
import uk.co.pbellchambers.maceswinger.gui.Gui;
import uk.co.pbellchambers.maceswinger.utils.CustomMouse;
import uk.co.pbellchambers.maceswinger.utils.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class GuiButtonArray extends GuiComponent {

    private Random rand = new Random();
    private List<Button> options = new ArrayList<Button>();

    public GuiButtonArray(int id, Gui gui) {
        super(id, gui);
    }

    public void addButton(Button button) {
        options.add(button);

    }

    public int getId() {
        return id;
    }

    public class Button {

        public int id;
        public String text;
        public boolean isAvailable = true;
        private float x, y;
        private float width, height;
        private boolean isActive = true;

        public Button(int id, String text, float xx, float yy, float width,
                      float height) {
            this.id = id;
            this.text = text;
            this.x = xx;
            this.y = yy;
            this.width = width;
            this.height = height;
        }

        public boolean isMouseinBounds() {

            if (CustomMouse.getX() < this.x + width
                    && CustomMouse.getX() > this.x
                    && CustomMouse.getY() < this.y + height
                    && CustomMouse.getY() > this.y) {
                return true;
            }
            return false;

        }

        public void tick(int ticks) {
            if (isActive && CustomMouse.isButtonDown(0) && isMouseinBounds()) {
                parent.guiActionPerformed(id, 0);
            } else if (!isMouseinBounds()) {
                isActive = true;
            }

        }

    }

    public void render() {
        for (Button o : options) {

            glPushMatrix();
            if (o.isMouseinBounds()) {
                GL11.glColor4f(0, 1, 0, 1);
            }
            if (!o.isMouseinBounds()) {
                GL11.glColor4f(1, 0, 0, 1);
            }
            glBegin(GL_QUADS);
            // glTexCoord2f(textureX,textureY);
            glVertex2f(o.x, o.y);
            // glTexCoord2f(textureX+textureWidth,textureY);
            glVertex2f(o.x + o.width, o.y);
            // glTexCoord2f(textureX+textureWidth,textureY+textureWidth);
            glVertex2f(o.x + o.width, o.y + o.height);
            // glTexCoord2f(textureX,textureY+textureWidth);
            glVertex2f(o.x, o.y + o.height);
            GL11.glColor4f(1, 1, 1, 1);
            glEnd();
            Font.drawString(o.text, o.x + 10, o.y + 10, 0.2f, new Vector4f(0,
                    0, 1, 1));
            glPopMatrix();
        }

    }

    @Override
    public void tick(int ticks) {
        for (Button b : options) {
            b.tick(ticks);
        }

    }

    public void deactivate(int id) {
        for (Button o : options) {
            if (o.id == id) {
                o.isActive = false;
                return;
            }
        }
    }

}
