package uk.co.pbellchambers.maceswinger.leveldesigner.util;

import uk.co.pbellchambers.maceswinger.leveldesigner.Main;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class Keyboard {

    public static boolean isKeyDown(int key) {
        return glfwGetKey(Main.customDisplay.getWindowHandle(), key) == GLFW_PRESS;
    }
}


