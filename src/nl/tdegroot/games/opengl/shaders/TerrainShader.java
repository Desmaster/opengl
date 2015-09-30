package nl.tdegroot.games.opengl.shaders;

import nl.tdegroot.games.opengl.entity.Camera;
import nl.tdegroot.games.opengl.entity.Light;
import nl.tdegroot.games.opengl.toolbox.MathUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class TerrainShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/nl/tdegroot/games/opengl/shaders/terrain.vert";
    private static final String FRAGMENT_FILE = "src/nl/tdegroot/games/opengl/shaders/terrain.frag";

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int lightPositionLocation;
    private int lightColourLocation;
    private int shineDamperLocation;
    private int reflectivityLocation;
    private int skyColourLocation;

    private int backgroundTextureLocation;
    private int rTextureLocation;
    private int gTextureLocation;
    private int bTextureLocation;
    private int blendMapLocation;


    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normals");
    }

    protected void getAllUniformLocations() {
        transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
        lightPositionLocation = super.getUniformLocation("lightPosition");
        lightColourLocation = super.getUniformLocation("lightColour");
        shineDamperLocation = super.getUniformLocation("shineDamper");
        reflectivityLocation = super.getUniformLocation("reflectivity");
        skyColourLocation = super.getUniformLocation("skyColour");
        backgroundTextureLocation = super.getUniformLocation("backgroundTexture");
        rTextureLocation = super.getUniformLocation("rTexture");
        gTextureLocation = super.getUniformLocation("gTexture");
        bTextureLocation = super.getUniformLocation("bTexture");
        blendMapLocation = super.getUniformLocation("blendMap");
    }

    public void connectTextureUnits() {
        super.loadInt(backgroundTextureLocation, 0);
        super.loadInt(rTextureLocation, 1);
        super.loadInt(gTextureLocation, 2);
        super.loadInt(bTextureLocation, 3);
        super.loadInt(blendMapLocation, 4);
    }

    public void loadSkyColour(float r, float g, float b) {
        super.loadVector(skyColourLocation, new Vector3f(r, g, b));
    }

    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(shineDamperLocation, damper);
        super.loadFloat(reflectivityLocation, reflectivity);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(transformationMatrixLocation, matrix);
    }

    public void loadLight(Light light) {
        super.loadVector(lightPositionLocation, light.getPosition());
        super.loadVector(lightColourLocation, light.getColour());
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f matrix = MathUtils.createViewMatrix(camera);
        super.loadMatrix(viewMatrixLocation, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(projectionMatrixLocation, matrix);
    }
}
