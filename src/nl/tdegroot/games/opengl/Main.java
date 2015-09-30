package nl.tdegroot.games.opengl;

import nl.tdegroot.games.opengl.entity.Camera;
import nl.tdegroot.games.opengl.entity.Entity;
import nl.tdegroot.games.opengl.entity.Light;
import nl.tdegroot.games.opengl.entity.Player;
import nl.tdegroot.games.opengl.graphics.Loader;
import nl.tdegroot.games.opengl.graphics.MasterRenderer;
import nl.tdegroot.games.opengl.models.ModelData;
import nl.tdegroot.games.opengl.models.OBJFileLoader;
import nl.tdegroot.games.opengl.models.RawModel;
import nl.tdegroot.games.opengl.models.TexturedModel;
import nl.tdegroot.games.opengl.terrains.Terrain;
import nl.tdegroot.games.opengl.textures.ModelTexture;
import nl.tdegroot.games.opengl.textures.TerrainTexture;
import nl.tdegroot.games.opengl.textures.TerrainTexturePack;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        ModelData treeModelData = OBJFileLoader.loadOBJ("tree");
        RawModel treeModel = loader.loadToVAO(treeModelData.getVertices(), treeModelData.getTextureCoords(), treeModelData.getNormals(), treeModelData.getIndices());
        ModelTexture treeTexture = new ModelTexture(loader.loadTexture("res/tree.png"));
        treeTexture.setShineDamper(10);
        treeTexture.setReflectivity(0.15f);
        TexturedModel texturedModelTree = new TexturedModel(treeModel, treeTexture);

        ModelData grassModelData = OBJFileLoader.loadOBJ("grassModel");
        RawModel grassModel = loader.loadToVAO(grassModelData.getVertices(), grassModelData.getTextureCoords(), grassModelData.getNormals(), grassModelData.getIndices());
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("res/grassTexture.png"));
        grassTexture.setHasTransparency(true);
        grassTexture.setUseFakeLighting(true);
        grassTexture.setShineDamper(10);
        grassTexture.setReflectivity(0.15f);
        TexturedModel texturedModelGrass = new TexturedModel(grassModel, grassTexture);

        ModelData fernModelData = OBJFileLoader.loadOBJ("fern");
        RawModel fernModel = loader.loadToVAO(fernModelData.getVertices(), fernModelData.getTextureCoords(), fernModelData.getNormals(), fernModelData.getIndices());
        ModelTexture fernTexture = new ModelTexture(loader.loadTexture("res/fern.png"));
        fernTexture.setHasTransparency(true);
        fernTexture.setShineDamper(10);
        fernTexture.setReflectivity(0.15f);
        TexturedModel texturedModelFern = new TexturedModel(fernModel, fernTexture);

        Random r = new Random();

        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < 250; i++) {
            Entity tree = new Entity(texturedModelTree, new Vector3f(r.nextInt(1000) - 500, 0, r.nextInt(1000) - 500), 0, 0, 0, 6);
            Entity grass = new Entity(texturedModelGrass, new Vector3f(r.nextInt(500) - 250, 0, r.nextInt(500) - 250), 0, 0, 0, 1);
            Entity fern = new Entity(texturedModelFern, new Vector3f(r.nextInt(500) - 250, 0, r.nextInt(500) - 250), 0, 0, 0, 1);
            entities.add(tree);
            entities.add(grass);
            entities.add(fern);
        }
        Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("res/grassy2.png"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("res/dirt.png"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("res/grassFlowers.png"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("res/path.png"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("res/blendMap.png"));

        TerrainTexturePack terrainTexturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        Terrain terrain = new Terrain(0, 0, loader, terrainTexturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, terrainTexturePack, blendMap);
        Terrain terrain3 = new Terrain(0, -1, loader, terrainTexturePack, blendMap);
        Terrain terrain4 = new Terrain(1, -1, loader, terrainTexturePack, blendMap);
        Terrain terrain5 = new Terrain(-1, 0, loader, terrainTexturePack, blendMap);
        Terrain terrain6 = new Terrain(-1, 1, loader, terrainTexturePack, blendMap);

        ModelData playerModelData = OBJFileLoader.loadOBJ("person");
        RawModel playerModel = loader.loadToVAO(playerModelData.getVertices(), playerModelData.getTextureCoords(), playerModelData.getNormals(), playerModelData.getIndices());
        TexturedModel texturedModelPlayer = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("res/playerTexture.png")));

        Player player = new Player(texturedModelPlayer, new Vector3f(100, 0, -50), 0, 0, 0, 1);

        Camera camera = new Camera(player);

        MasterRenderer renderer = new MasterRenderer();

        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE) {
            DisplayManager.update();
            camera.move();
            player.move();

            renderer.processEntity(player);

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processTerrain(terrain3);
            renderer.processTerrain(terrain4);
            renderer.processTerrain(terrain5);
            renderer.processTerrain(terrain6);

            for (Entity e : entities) {
//                e.increaseRotation(0, 1, 0);
                renderer.processEntity(e);
            }
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
