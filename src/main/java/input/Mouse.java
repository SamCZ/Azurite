package input;

import event.EventData;
import event.Events;
import graphics.Window;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    /**
     * Position of the mouse
     */
    public static Vector2f mouse = new Vector2f();
    /**
     * X Position of the mouse
     */
    public static long mouseX = 0;
    /**
     * Y Position of the mouse
     */
    public static long mouseY = 0;
    /**
     * Position of the mouse on the previous frame
     */
    public static Vector2f pmouse = new Vector2f();
    /**
     * X Position of the mouse on the previous frame
     */
    public static long pmouseX = 0;
    /**
     * Y Position of the mouse on the previous frame
     */
    public static long pmouseY = 0;
    /**
     * Mouse Scrolling Values
     */
    public static Vector2f mouseScroll;
    /**
     * Mouse Scrolling in X-axis.
     */
    public static float scrollX = 0;
    /**
     * Mouse Scrolling in Y-axis.
     */
    public static float scrollY = 0;
    /**
     * Array for storing states for mouse buttons
     */
    public static boolean mouseButton[] = new boolean[3];
    /**
     * Is the mouse being dragged
     */
    public static boolean mouseDragged;

    /**
     * Latest button whose state was changed
     */
    private static int _button;
    /**
     * Latest button whose state is changed
     */
    private static int _action;

    /**
     * Mouse was moved this frame or not
     */
    private static boolean moved = false;

    /**
     * Apply the latest changes to the mouseButton array
     */
    public static void pollMouseButtons() {
        if (_action == GLFW_PRESS) {
            if (_button < mouseButton.length)
                mouseButton[_button] = true;
        } else if (_action == GLFW_RELEASE) {
            if (_button < mouseButton.length) {
                mouseButton[_button] = false;
                mouseDragged = false;
            }
        }
    }

    /**
     * Subscribes to mouse scroll event and mouse button event
     */
    public static void setupCallbacks() {
        glfwSetScrollCallback(Window.glfwWindow(), (w, xOffset, yOffset) -> {
            scrollX = (float) xOffset;
            scrollY = (float) yOffset;
            mouseScroll = new Vector2f(scrollX, scrollY);

            Events.mouseScrollEvent.onEvent(new EventData.MouseScrollEventData(xOffset, yOffset));
        });

        glfwSetMouseButtonCallback(Window.glfwWindow(), (w, button, action, mods) -> {
            _button = button;
            _action = action;
            Events.mouseButtonEvent.onEvent(new EventData.MouseButtonEventData(button, action, mods));
        });

        glfwSetCursorPosCallback(Window.glfwWindow(), (w, xpos, ypos) -> {
            long pmouseX = mouseX;
            long pmouseY = mouseY;
            pmouse.set(pmouseX, pmouseY);

            mouseX = (long) xpos;
            mouseY = (long) ypos;
            mouse.set(mouseX, mouseY);
            moved = true;
        });
    }

    /**
     * Called each frame to update mouse button states and mouse position.
     */
    public static void update() {
        pollMouseButtons();

        if (moved) {
            mouseDragged = mouseButton[0] || mouseButton[1] || mouseButton[2];
        }

        moved = false;
    }

    /**
     * Get if the queried button is pressed
     *
     * @param button The button. Check input/Buttons.java
     * @return Returns whether the button is currently pressed.
     */
    public static boolean mouseButtonDown(int button) {
        if (button < mouseButton.length) {
            return mouseButton[button];
        }
        return false;
    }

    /**
     * Clear the mouse state
     */
    public static void clearMouseInput() {
        scrollX = 0;
        scrollY = 0;
        mouseScroll = new Vector2f(scrollX, scrollY);
        pmouseX = mouseX;
        pmouseY = mouseY;
        pmouse = new Vector2f(pmouseX, pmouseY);
    }
}
