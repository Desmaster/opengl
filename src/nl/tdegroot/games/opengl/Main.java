package nl.tdegroot.games.opengl;

import nl.tdegroot.games.opengl.entity.Camera;
import nl.tdegroot.games.opengl.entity.Entity;
import nl.tdegroot.games.opengl.entity.Light;
import nl.tdegroot.games.opengl.graphics.Loader;
import nl.tdegroot.games.opengl.graphics.MasterRenderer;
import nl.tdegroot.games.opengl.graphics.OBJLoader;
import nl.tdegroot.games.opengl.models.RawModel;
import nl.tdegroot.games.opengl.models.TexturedModel;
import nl.tdegroot.games.opengl.terrains.Terrain;
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

        Camera camera = new Camera();
        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/white.png"));
        texture.setShineDamper(10);
        texture.setReflectivity(0.15f);
        TexturedModel texturedModel = new TexturedModel(model, texture);
        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));

        Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("res/terrain.png")));
        Terrain terrain2 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("res/terrain.png")));
        Terrain terrain3 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("res/terrain.png")));
        Terrain terrain4 = new Terrain(1, -1, loader, new ModelTexture(loader.loadTexture("res/terrain.png")));
        Terrain terrain5 = new Terrain(-1, 0, loader, new ModelTexture(loader.loadTexture("res/terrain.png")));
        Terrain terrain6 = new Terrain(-1, 1, loader, new ModelTexture(loader.loadTexture("res/terrain.png")));

        MasterRenderer renderer = new MasterRenderer();

        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE) {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processTerrain(terrain3);
            renderer.processTerrain(terrain4);
            renderer.processTerrain(terrain5);
            renderer.processTerrain(terrain6);

            renderer.processEntity(entity);
            renderer.render(light, camera);

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
        renderer.cleanUp();
        loader.cleanUp();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

}
