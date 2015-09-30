package nl.tdegroot.games.opengl.entity;

import nl.tdegroot.games.opengl.DisplayManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch = 20;
    private float yaw;
    private float roll;

    private Player player;

    public Camera(Player player) {
        this.player = player;
    }

    public void move() {
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance, verticalDistance);
        System.out.println(position);
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = player.getRy() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance;
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = (float) (DisplayManager.getyScroll());
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch() {
        if (DisplayManager.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            float pitchChange = (float) (DisplayManager.getyScroll() * 0.1f);
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundPlayer() {
        if (DisplayManager.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            float angleChange = (float) DisplayManager.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
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
