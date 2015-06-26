package nl.tdegroot.games.opengl;

import nl.tdegroot.games.opengl.entity.Camera;
import nl.tdegroot.games.opengl.entity.Entity;
import nl.tdegroot.games.opengl.entity.Light;
import nl.tdegroot.games.opengl.graphics.Loader;
import nl.tdegroot.games.opengl.graphics.OBJLoader;
import nl.tdegroot.games.opengl.graphics.Renderer;
import nl.tdegroot.games.opengl.models.RawModel;
import nl.tdegroot.games.opengl.models.TexturedModel;
import nl.tdegroot.games.opengl.shaders.StaticShader;
import nl.tdegroot.games.opengl.textures.ModelTexture;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

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
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        Camera camera = new Camera();
        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/white.png"));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));

        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE) {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.prepare();
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
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
