package uk.co.pbellchambers.maceswinger.leveldesigner.util;

import org.joml.Vector3d;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import uk.co.pbellchambers.maceswinger.client.GameClient;
import uk.co.pbellchambers.maceswinger.leveldesigner.Main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class CustomDisplay implements WindowListener {

    private static float yScale;
    private static float xScale;
    private long windowHandle;
    private long variableYieldTime;
    private long lastTime;
    private GLFWCursorPosCallback cursorPosCallback;
    private Vector3d cursorPos = new Vector3d();
    private GLFWErrorCallback errorCallback;
    private static final CharSequence TITLE = "Mace Swinger Level Designer";

    public void create(boolean isFullscreen) {
        if (!isFullscreen) {
            Main.frame.addWindowListener(this);
            Main.canvas.setBounds(0, 0, (int) Main.WIDTH,
                    (int) Main.HEIGHT);
            Main.canvas.setIgnoreRepaint(true);
            Main.canvas.setFocusable(true);

            Main.frame.add(Main.canvas);
            Main.frame.pack();
            Main.frame.setLocationRelativeTo(null);
            Main.frame.setFocusable(true);
            Main.frame.setVisible(true);
            Main.canvas.requestFocus();
            Main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Main.frame.setTitle(TITLE.toString());

            System.out.println(Main.canvas.isDisplayable());

            initialiseWindow(false);

        } else {
            initialiseWindow(true);
        }
        setyScale(Main.HEIGHT / getWindowHeight());
        setxScale(Main.WIDTH / getWindowWidth());

    }

    private void initialiseWindow(Boolean fullscreen) {
        //todo fullscreen something
        glfwInit();
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        System.out.println(Main.canvas.isDisplayable());

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 1);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        //glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); - only in 3.2 and above
        windowHandle = glfwCreateWindow(Math.round(GameClient.WIDTH), Math.round(GameClient.HEIGHT), TITLE, 0, 0);
        if (windowHandle == 0) {
            throw new RuntimeException("Failed to create windowHandle");
        }
        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();
        glfwSetCursorPosCallback(windowHandle, (cursorPosCallback = new GLFWCursorPosCallback() {

            @Override
            public void invoke(long window, double xpos, double ypos) {
                cursorPos.x = xpos;
                cursorPos.y = getWindowHeight() - ypos;
            }

        }));
        glfwShowWindow(windowHandle);
        glfwSetFramebufferSizeCallback(windowHandle, resizeWindow);
        //todo set parent if not fullscreen
    }

    public void destroyWindow() {
        glfwDestroyWindow(windowHandle);
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public void terminateGLFW() {
        glfwTerminate();
    }

    public static float getxScale() {
        return xScale;
    }

    public static void setxScale(float xScale) {
        CustomDisplay.xScale = xScale;
    }

    public static float getyScale() {
        return yScale;
    }

    public static void setyScale(float yScale) {
        CustomDisplay.yScale = yScale;
    }

    @Override
    public void windowActivated(WindowEvent arg0) {

    }

    @Override
    public void windowClosed(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosing(WindowEvent arg0) {
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowOpened(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    public float getWindowWidth() {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(windowHandle, width, height);
        return width.get(0);
    }

    public float getWindowHeight() {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(windowHandle, width, height);
        return height.get(0);
    }

    public Vector3d getCursorPos() {
        return cursorPos;
    }

    /**
     * An accurate sync method that adapts automatically
     * to the system it runs on to provide reliable results.
     *
     * @param fps The desired frame rate, in frames per second
     * @author kappa (On the LWJGL Forums)
     */
    public void sync(int fps) {
        if (fps <= 0) {
            return;
        }

        long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
        // yieldTime + remainder micro & nano seconds if smaller than sleepTime
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000 * 1000));
        long overSleep = 0; // time the sync goes over by

        try {
            while (true) {
                long t = System.nanoTime() - lastTime;

                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1);
                } else if (t < sleepTime) {
                    // burn the last few CPU cycles to ensure accuracy
                    Thread.yield();
                } else {
                    overSleep = t - sleepTime;
                    break; // exit while loop
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);

            // auto tune the time sync should yield
            if (overSleep > variableYieldTime) {
                // increase by 200 microseconds (1/5 a ms)
                variableYieldTime = Math.min(variableYieldTime + 200 * 1000, sleepTime);
            } else if (overSleep < variableYieldTime - 200 * 1000) {
                // decrease by 2 microseconds
                variableYieldTime = Math.max(variableYieldTime - 2 * 1000, 0);
            }
        }
    }

    /* for resizing windowHandle */
    private static GLFWFramebufferSizeCallback resizeWindow = new GLFWFramebufferSizeCallback() {
        @Override
        public void invoke(long window, int width, int height) {
            GL11.glViewport(0, 0, width, height);
            //update any other windowHandle vars you might have (aspect ratio, MVP matrices, etc)
        }
    };
}
