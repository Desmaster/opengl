package nl.tdegroot.games.opengl.entity;

import nl.tdegroot.games.opengl.DisplayManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {
    }

    public void move() {
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_W)) {
            position.z -= 0.02f;
        }
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_D)) {
            position.x += 0.02f;
        }
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_A)) {
            position.x -= 0.02f;
        }
        System.out.println(position.x + ", " + position.y + ", " + position.z);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
