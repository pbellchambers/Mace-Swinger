package uk.co.pbellchambers.maceswinger.utils;

import uk.co.pbellchambers.maceswinger.client.GameClient;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class Keyboard {

    public static boolean isKeyDown(int key) {
        return glfwGetKey(GameClient.customDisplay.getWindowHandle(), key) == GLFW_PRESS;

    }
}


