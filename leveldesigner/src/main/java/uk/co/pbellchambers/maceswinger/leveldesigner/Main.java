package uk.co.pbellchambers.maceswinger.leveldesigner;

import org.lwjgl.opengl.GL11;
import uk.co.pbellchambers.maceswinger.leveldesigner.util.CustomDisplay;
import uk.co.pbellchambers.maceswinger.leveldesigner.util.Keyboard;
import uk.co.pbellchambers.maceswinger.leveldesigner.util.SpriteSheet;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glEnable;

public class Main {

    private int fps;
    public static final float WIDTH = 1920 / 2;
    public static final float HEIGHT = 1080 / 2;
    public static JFrame frame;
    public static Canvas canvas;

    private Level level;
    public static CustomDisplay customDisplay;

    private void run() {
        canvas = new Canvas();
        frame = new JFrame();
        customDisplay = new CustomDisplay();
        customDisplay.create(false);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        glEnable(GL11.GL_STENCIL_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        init();

        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int framesPS = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        while (!glfwWindowShouldClose(customDisplay.getWindowHandle())) {

            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            boolean shouldRender = true;

            while (delta >= 1) {
                if (Keyboard.isKeyDown(GLFW_KEY_LEFT_ALT)
                        && Keyboard.isKeyDown(GLFW_KEY_F4)) {
                    break;
				}
                tick();
                delta -= 1;
                shouldRender = true;

            }
            render();
            try {
                Thread.sleep(2);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldRender) {
                framesPS++;
                update();

            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                fps = framesPS;
                framesPS = 0;

            }

        }

        // exit(0);

    }

    private void tick() {
        // TODO Auto-generated method stub

    }

    private void render() {
        level.renderTiles();

    }

    private void update() {
        resize(canvas.getWidth(), canvas.getHeight());
        customDisplay.sync(60);
        glfwPollEvents();
        glfwSwapBuffers(customDisplay.getWindowHandle());
    }

    private void resize(int i, int j) {
        GL11.glViewport(0, 0, i, j);
        CustomDisplay.setyScale(Main.HEIGHT / customDisplay.getWindowHeight());
        CustomDisplay.setxScale(Main.WIDTH / customDisplay.getWindowWidth());
    }

    private void init() {
        this.level = new Level(this);
        level.loadLevel("");
        SpriteSheet.openSheet();

    }

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("libs").getAbsolutePath());

        new Main().run();
    }

}
