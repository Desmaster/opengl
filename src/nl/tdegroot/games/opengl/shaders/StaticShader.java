package nl.tdegroot.games.opengl.shaders;

import nl.tdegroot.games.opengl.entity.Camera;
import nl.tdegroot.games.opengl.entity.Light;
import nl.tdegroot.games.opengl.toolbox.MathUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/nl/tdegroot/games/opengl/shaders/shader.vert";
    private static final String FRAGMENT_FILE = "src/nl/tdegroot/games/opengl/shaders/shader.frag";

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int lightPositionLocation;
    private int lightColourLocation;
    private int shineDamperLocation;
    private int reflectivityLocation;
    private int fakeLightingLocation;
    private int skyColourLocation;

    public StaticShader() {
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
        fakeLightingLocation = super.getUniformLocation("useFakeLighting");
        skyColourLocation = super.getUniformLocation("skyColour");
    }

    public void loadSkyColour(float r, float g, float b) {
        super.loadVector(skyColourLocation, new Vector3f(r, g, b));
    }

    public void loadFakeLightingVariable(boolean useFakeLighting) {
        super.loadBoolean(fakeLightingLocation, useFakeLighting);
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
