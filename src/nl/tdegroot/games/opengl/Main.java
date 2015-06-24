package nl.tdegroot.games.opengl;

import nl.tdegroot.games.opengl.graphics.Loader;
import nl.tdegroot.games.opengl.graphics.RawModel;
import nl.tdegroot.games.opengl.graphics.Renderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Main {

    private long window;

    public Main() {
        window = DisplayManager.create();
    }

    private void start() {
        loop();
    }

    private void loop() {

        DisplayManager.update();

        float[] vertices = {
                -0.5f, 0.5f, 0f, // V0
                -0.5f, -0.5f, 0f, // V1
                0.5f, -0.5f, 0f, // V2
                0.5f, 0.5f, 0f // V3
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        RawModel model = loader.loadToVAO(vertices, indices);

        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE) {
            renderer.prepare();
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            renderer.render(model);

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        loader.cleanUp();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

}
