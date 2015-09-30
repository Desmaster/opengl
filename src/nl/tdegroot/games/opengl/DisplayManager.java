package nl.tdegroot.games.opengl;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayManager {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private static long lastFrameTime;
    private static float delta;

    private static boolean[] keys = new boolean[65536];
    private static boolean[] buttons = new boolean[32];

    private static double mouseX;
    private static double mouseY;
    private static double mouseDX;
    private static double mouseDY;

    private static double xScroll;
    private static double yScroll;

    private static GLFWErrorCallback errorCallback;
    private static GLFWKeyCallback keyCallback;
    private static GLFWMouseButtonCallback mouseCallBack;
    private static GLFWCursorPosCallback cursorPosCallback;
    private static GLFWScrollCallback scrollCallback;

    private static long window;

    public static long create() {
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
        if (glfwInit() != GL_TRUE) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);

        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello, OpenGL!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = action != GLFW_RELEASE;
            }
        });

        glfwSetMouseButtonCallback(window, mouseCallBack = new GLFWMouseButtonCallback() {
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = action != GLFW_RELEASE;
            }
        });

        glfwSetCursorPosCallback(window, cursorPosCallback = new GLFWCursorPosCallback() {
            public void invoke(long window, double xpos, double ypos) {
                mouseDX = xpos - mouseX;
                mouseDY = ypos - mouseY;

                mouseX = xpos;
                mouseY = ypos;
            }
        });

        glfwSetScrollCallback(window, scrollCallback = new GLFWScrollCallback() {
            public void invoke(long window, double xoffset, double yoffset) {
                xScroll = xoffset;
                yScroll = yoffset;
            }
        });

        ByteBuffer videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window,
                (GLFWvidmode.width(videoMode) - WIDTH) / 2,
                (GLFWvidmode.height(videoMode) - HEIGHT) / 2);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        lastFrameTime = getCurrentTime();
        GLContext.createFromCurrent();

        return window;
    }

    public static void update() {
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime);
        lastFrameTime = currentFrameTime;
    }

    public static float getFrameTimeSeconds() {
        return delta / 1000;
    }

    public static void destroy() {
    }

    private static long getCurrentTime() {
        return (long) (GLFW.glfwGetTime() * 1000);
    }

    public static boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }

    public static boolean isMouseButtonDown(int button) {
        return buttons[button];
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static double getDX() {
        return mouseDX;
    }

    public static double getDY() {
        return mouseDY;
    }

    public static double getxScroll() {
        double result = xScroll;
        xScroll = 0;
        return result;
    }

    public static double getyScroll() {
        double result = yScroll;
        yScroll = 0;
        return result;
    }
}
