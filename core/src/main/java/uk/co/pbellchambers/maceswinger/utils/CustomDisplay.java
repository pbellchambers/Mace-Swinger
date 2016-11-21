package uk.co.pbellchambers.maceswinger.utils;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import uk.co.pbellchambers.maceswinger.client.GameClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class CustomDisplay implements WindowListener {

    private static float yScale;
    private static float xScale;
    private GLFWErrorCallback errorCallback;
    private long windowHandle;
    private static final CharSequence TITLE = "Mace Swinger";
    private long variableYieldTime;
    private long lastTime;
    private GLFWFramebufferSizeCallback framebufferSizeCallback;

    public void create(boolean isFullscreen) {
        if (!isFullscreen) {
            GameClient.frame.addWindowListener(this);
            GameClient.canvas.setBounds(0, 0, (int) GameClient.WIDTH,
                    (int) GameClient.HEIGHT);
            GameClient.canvas.setIgnoreRepaint(true);
            GameClient.canvas.setFocusable(true);

            GameClient.frame.add(GameClient.canvas);
            GameClient.frame.pack();
            GameClient.frame.setLocationRelativeTo(null);
            GameClient.frame.setFocusable(true);
            GameClient.frame.setVisible(true);
            GameClient.canvas.requestFocus();
            GameClient.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GameClient.frame.setTitle(TITLE.toString());
            BufferedImage img;
            try {
                img = ImageIO.read(this.getClass().getResourceAsStream("/shield.png"));
                GameClient.frame.setIconImage(img);
            } catch (IOException e) {
                e.printStackTrace();
            }

            initialiseWindow(false);
            
        } else {
            initialiseWindow(true);
        }
        setyScale(GameClient.HEIGHT / getWindowHeight());
        setxScale(GameClient.WIDTH / getWindowWidth());
    }

    private void initialiseWindow(Boolean fullscreen) {
        //todo fullscreen something
        glfwInit();
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        System.out.println(GameClient.canvas.isDisplayable());

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        windowHandle = glfwCreateWindow(Math.round(GameClient.WIDTH), Math.round(GameClient.HEIGHT), TITLE, 0, 0);
        if (windowHandle == 0) {
            throw new RuntimeException("Failed to create windowHandle");
        }
        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();
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
        IntBuffer width = IntBuffer.allocate(1);
        IntBuffer height = IntBuffer.allocate(1);
        glfwGetFramebufferSize(windowHandle, width, height);
        return width.get();
    }

    public float getWindowHeight() {
        IntBuffer width = IntBuffer.allocate(1);
        IntBuffer height = IntBuffer.allocate(1);
        glfwGetFramebufferSize(windowHandle, width, height);
        return height.get();
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
