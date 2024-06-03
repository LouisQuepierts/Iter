package quepierts.iter.util;

import org.apache.logging.log4j.Logger;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {
    private static final Logger logger = IterLogManager.getLogger();
    public static float startedTime = System.nanoTime();

    private static double currentTime;
    private static double lastFrameTime;
    private static double deltaTime;

    public static void updateTime() {
        lastFrameTime = currentTime;
        currentTime = glfwGetTime();
        deltaTime = currentTime - lastFrameTime;
    }

    public static double getCurrentTime() {
        return currentTime;
    }

    public static double getDeltaTime() {
        return deltaTime;
    }

    public static double getLastFrameTime() {
        return lastFrameTime;
    }
}
