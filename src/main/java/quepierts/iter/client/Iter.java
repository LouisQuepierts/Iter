package quepierts.iter.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import quepierts.iter.Main;
import quepierts.iter.client.event.KeyEvent;
import quepierts.iter.client.renderer.RenderEngine;
import quepierts.iter.client.resource.ResourceID;
import quepierts.iter.client.resource.manager.ModelManager;
import quepierts.iter.client.resource.manager.ShaderFileManager;
import quepierts.iter.client.resource.manager.ShaderManager;
import quepierts.iter.client.resource.manager.TextureManager;
import quepierts.iter.client.resource.model.Model;
import quepierts.iter.client.resource.shader.Program;
import quepierts.iter.client.resource.shader.Shader;
import quepierts.iter.client.resource.texture.Texture;
import quepierts.iter.client.scene.Scene;
import quepierts.iter.client.scene.SceneManager;
import quepierts.iter.client.util.CameraHelper;
import quepierts.iter.client.util.GuiHelper;
import quepierts.iter.client.util.input.KeyHelper;
import quepierts.iter.client.util.input.MouseHelper;
import quepierts.iter.observer.Event;
import quepierts.iter.reflex.ClassFinder;
import quepierts.iter.util.ResourceManager;
import quepierts.iter.util.Time;
import quepierts.iter.util.tree.Quad;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Iter {
    public static final Iter instance = new Iter();

    private static final Logger LOGGER = LogManager.getLogger("Iter");

    private static Vector2i size;
    private static Quad screenQuad;

    private final CameraHelper cameraHelper;
    public final GuiHelper guiHelper;

    public final ResourceManager<Program> shaderManager;
    public final ResourceManager<Texture> textureManager;
    public final ResourceManager<Model> modelManager;
    public final ResourceManager<Shader> shaderResourceManager;

    public final MouseHelper mouseHelper;
    public final KeyHelper inputHelper;

    public final SceneManager sceneManager;
    private long window;

    private RenderEngine renderEngine;

    public static Iter getInstance() {
        return instance;
    }

    private Iter() {
        mouseHelper = new MouseHelper(this, window);
        inputHelper = new KeyHelper(window);

        ResourceID.init();

        guiHelper = new GuiHelper(mouseHelper, inputHelper, this);
        cameraHelper = new CameraHelper(mouseHelper, inputHelper, guiHelper);

        textureManager = new TextureManager();
        modelManager = new ModelManager();
        shaderResourceManager = new ShaderFileManager();
        shaderManager = new ShaderManager((ShaderFileManager) shaderResourceManager);

        sceneManager = new SceneManager();
    }

    public void run() {
        LOGGER.info("Iter Start");

        init();

        loop();

        cleanUp();
        glfwDestroyWindow(window);

        LOGGER.info("[Iter Clean Up] Iter Window Destroyed");

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    @Event.Subscribe
    private static void onKeyboardInput(KeyEvent event) {
        GuiHelper guiHelper = instance.guiHelper;
        if (event.press && GLFW_KEY_TAB == event.key) {
            if (guiHelper.isGuiOpened()) {
                guiHelper.closeGui();
            } else {
                guiHelper.openGui();
            }
        }
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        createWindow();
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);

        ResourceManager.loadAll();

        sceneManager.load();

        renderEngine = new RenderEngine(this);

        guiHelper.init();

        ClassFinder.searchFromRoot(Main.class);
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            Time.updateTime();
            glfwWindowHint(GLFW_REFRESH_RATE, 60);

            cameraHelper.updateCamera();

            guiHelper.updateGui();

            renderEngine.render(cameraHelper);

            mouseHelper.updateMouse();
            glfwSwapBuffers(window);
        }
    }

    private void createWindow() {
        glfwDefaultWindowHints();

        //set window visible and can not resize
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_REFRESH_RATE, 60);

        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode mode = glfwGetVideoMode(monitor);

        window = glfwCreateWindow(mode.width(), mode.height(), "Iter Indev-0.0.1", monitor, NULL);

        size = new Vector2i(mode.width(), mode.height());
        screenQuad = new Quad(0, 0, mode.width(), mode.height());

        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMaximizeWindow(window);

        glfwMakeContextCurrent(window);

        //init inputs
        glfwSetCursorPosCallback(window, mouseHelper::mousePositionCallBack);
        glfwSetMouseButtonCallback(window, mouseHelper::mouseButtonCallBack);
        glfwSetScrollCallback(window, mouseHelper::updateMouseScroll);

        glfwSetKeyCallback(window, inputHelper::updateKeyInput);
        glfwSetCharCallback(window, inputHelper::charInput);

        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private void cleanUp() {
        ResourceManager.clearAll();

        renderEngine.cleanUp();
    }

    public static int getWidth() {
        return size.x;
    }

    public static int getHeight() {
        return size.y;
    }

    public long getWindow() {
        return window;
    }

    public static void resetViewPort() {
        GL11.glViewport(0, 0, size.x, size.y);
    }

    public static Vector2f getSizeVecf() {
        return new Vector2f(size);
    }

    public static Vector2i getSizeVeci() {
        return new Vector2i(size);
    }

    public Scene getScene() {
        return sceneManager.getCurrentScene();
    }

    public static Quad getScreenQuad() {
        return screenQuad;
    }
}
