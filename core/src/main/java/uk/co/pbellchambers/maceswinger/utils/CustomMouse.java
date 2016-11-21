package uk.co.pbellchambers.maceswinger.utils;

import uk.co.pbellchambers.maceswinger.client.GameClient;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

public class CustomMouse {

    public static float getX() {
        return (float) GameClient.customDisplay.getCursorPos().x * CustomDisplay.getxScale();

    }

    public static float getY() {
        return (float) GameClient.customDisplay.getCursorPos().y * CustomDisplay.getyScale();

    }

    public static boolean isButtonDown(int button) {
        return glfwGetMouseButton(GameClient.customDisplay.getWindowHandle(), button) == GLFW_PRESS;
    }
}
