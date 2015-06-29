package nl.tdegroot.games.opengl.entity;

import nl.tdegroot.games.opengl.DisplayManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0, 15, 0);
    private float pitch;
    private float yaw;
    private float roll;
    private float speed = 0.1f;

    public Camera() {
    }

    public void move() {
//        yaw += 0.2f;
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_S)) {
            position.z -= speed;
        }
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_W)) {
            position.z += speed;
        }
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_D)) {
            position.x += speed;
        }
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_A)) {
            position.x -= speed;
        }
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            position.y -= speed;
        }
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
            position.y += speed;
        }
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_Q)) {
            yaw -= speed;
        }
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_E)) {
            yaw += speed;
        }

//        System.out.println(position.x + ", " + position.y + ", " + position.z);
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
