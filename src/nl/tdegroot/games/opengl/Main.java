package nl.tdegroot.games.opengl;

import nl.tdegroot.games.opengl.graphics.Loader;
import nl.tdegroot.games.opengl.graphics.RawModel;
import nl.tdegroot.games.opengl.graphics.Renderer;
import nl.tdegroot.games.opengl.models.TexturedModel;
import nl.tdegroot.games.opengl.shaders.StaticShader;
import nl.tdegroot.games.opengl.textures.ModelTexture;
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

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

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

        float[] textureCoords = {
                0, 0, // V0
                0, 1, // V1
                1, 1, // V2
                1, 0, // V3
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/texture.png"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE) {
            renderer.prepare();
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            shader.start();
            renderer.render(texturedModel);
            shader.stop();

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        shader.cleanUp();
        loader.cleanUp();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

}
