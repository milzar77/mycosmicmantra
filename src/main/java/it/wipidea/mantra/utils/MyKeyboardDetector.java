package it.wipidea.mantra.utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class MyKeyboardDetector {

    /*
    USAGE:
    MyKeyboardDetector INCHIODA IL THREAD, DA FAR SGANCIARE
    do {
    if (Keyboard.isKeyPressed(KeyEvent.VK_W)) System.out.println("W is pressed!");
} while (!Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE));
     */

    private static final Map<Integer, Boolean> pressedKeys = new HashMap<>();

    static {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
            synchronized (MyKeyboardDetector.class) {
                if (event.getID() == KeyEvent.KEY_PRESSED) pressedKeys.put(event.getKeyCode(), true);
                else if (event.getID() == KeyEvent.KEY_RELEASED) pressedKeys.put(event.getKeyCode(), false);
                return false;
            }
        });
    }

    public static boolean isKeyPressed(int keyCode) { // Any key code from the KeyEvent class
        return pressedKeys.getOrDefault(keyCode, false);
    }
}