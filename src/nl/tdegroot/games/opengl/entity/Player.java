package nl.tdegroot.games.opengl.entity;

import nl.tdegroot.games.opengl.DisplayManager;
import nl.tdegroot.games.opengl.models.TexturedModel;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity {

    private static final float RUN_SPEED = 30f;
    private static final float TURN_SPEED = 160f;
    private static final float GRAVITY = -150f;
    private static final float JUMP_POWER = 60;

    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean inAir = false;

    public Player(TexturedModel model, Vector3f position, float rx, float ry, float rz, float scale) {
        super(model, position, rx, ry, rz, scale);
    }

    public void move() {
        checkInput();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(getRy())));
        float dz = (float) (distance * Math.cos(Math.toRadians(getRy())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        if (getPosition().y < TERRAIN_HEIGHT) {
            upwardsSpeed = 0;
            inAir = false;
            super.getPosition().y = TERRAIN_HEIGHT;
        }
    }

    private void jump() {
        if (!inAir) {
            this.upwardsSpeed = JUMP_POWER;
            inAir = true;
        }
    }

    private void checkInput() {
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_W)) {
            currentSpeed = RUN_SPEED;
        } else if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_S)) {
            currentSpeed = -RUN_SPEED;
        } else {
            currentSpeed = 0;
        }

        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_D)) {
            currentTurnSpeed = -TURN_SPEED;
        } else if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_A)) {
            currentTurnSpeed = TURN_SPEED;
        } else {
            currentTurnSpeed = 0;
        }

//        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
//            position.y -= ySpeed;
//        }
        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
            jump();
        }
//        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_Q)) {
//            yaw -= yawSpeed;
//        }
//        if (DisplayManager.isKeyDown(GLFW.GLFW_KEY_E)) {
//            yaw += yawSpeed;
//        }
    }

}
