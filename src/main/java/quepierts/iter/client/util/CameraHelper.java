package quepierts.iter.client.util;

import quepierts.iter.client.object.Camera;
import quepierts.iter.client.util.input.KeyHelper;
import quepierts.iter.client.util.input.MouseHelper;
import quepierts.iter.util.Time;
import quepierts.iter.util.math.Rotation;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;

public class CameraHelper {
    private final Camera camera;
    private final Vector3f cameraMovement;
    private final Vector3f cameraRotation;
    private final Vector3f cameraMovementSmooth;
    private final MouseHelper mouseHelper;
    private final KeyHelper inputHelper;
    private final GuiHelper guiHelper;

    private float cameraSpeed;

    public CameraHelper(MouseHelper mouseHelper, KeyHelper inputHelper, GuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        camera = new Camera();
        cameraMovement = new Vector3f();
        cameraRotation = new Vector3f();

        this.mouseHelper = mouseHelper;
        this.inputHelper = inputHelper;

        cameraMovementSmooth = new Vector3f();

        cameraSpeed = 2.5f;
    }

    public void updateCamera() {
        //Rotate the camera by mouse
        if (mouseHelper.isLeftPressed() && !guiHelper.isGuiOpened()) {
            cameraRotation.set(mouseHelper.getDY() * 0.05, mouseHelper.getDX() * 0.05, 0.0f);

            camera.rotate(cameraRotation.x, cameraRotation.y, 0);
        }

        //Translate the camera by keyboard
        cameraMovement.set(0);
        camera.update();

        if (!guiHelper.isGuiOpened()) {
            if (inputHelper.isKeyPressed(GLFW_KEY_W))
                Rotation.toVector(cameraMovement, camera.getYaw());
            if (inputHelper.isKeyPressed(GLFW_KEY_S))
                Rotation.toVector(cameraMovement, camera.getYaw() + 180);
            if (inputHelper.isKeyPressed(GLFW_KEY_A))
                Rotation.toVector(cameraMovement, camera.getYaw() - 90);
            if (inputHelper.isKeyPressed(GLFW_KEY_D))
                Rotation.toVector(cameraMovement, camera.getYaw() + 90);
            if (inputHelper.isKeyPressed(GLFW_KEY_SPACE))
                cameraMovement.y += 1;
            if (inputHelper.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
                cameraMovement.y -= 1;

            if (cameraMovement.equals(0, 0, 0)) smooth();
        }

        float speedFactor = (float) Time.getDeltaTime() * cameraSpeed;
        camera.translate(cameraMovement.mul(speedFactor));
        cameraMovementSmooth.set(cameraMovement).div(speedFactor);
    }

    public Camera getCamera() {
        return camera;
    }

    private void smooth() {
        if (cameraMovementSmooth.length() > 0.1) {
            cameraMovement.set(cameraMovementSmooth).mul(0.35f * cameraSpeed);
        }
    }
}
