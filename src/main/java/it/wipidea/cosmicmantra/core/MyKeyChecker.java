package it.wipidea.cosmicmantra.core;

import it.wipidea.cosmicmantra.MantraCoreRunner;
import it.wipidea.cosmicmantra.gui.MyJFrame;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyChecker extends KeyAdapter {

    char previous = 0;
    @Override
    public void keyPressed(KeyEvent event) {

        int idk = event.getKeyCode();

        if (idk == KeyEvent.VK_P) {
            System.out.println("==> KEY BREAK'EM ALL, NEEDS RESTART <==");
            MantraCoreRunner.executorService.shutdownNow();
        }

        if (event.isControlDown()) {
            if (idk == KeyEvent.VK_K || idk == KeyEvent.VK_W) {
                System.out.println("==> KEY WALL <==");
                System.exit(1);
            }
            if (idk == KeyEvent.VK_P) {
                System.out.println("==> FREE KEY <==");
            }
            if (idk == KeyEvent.VK_S) {
                System.out.println("==> KEY STAT <==");
                System.out.printf("==> %s <==\n", event.getSource());
                if (event.getSource() instanceof MyJFrame) {
                    ((MyJFrame)event.getSource()).printStats(false);
                } else {
                    MantraCoreRunner.windowHook.printStats(false);
                    if (MantraCoreRunner.windowHook.getState()==JFrame.ICONIFIED) {
                        MantraCoreRunner.windowHook.setState(JFrame.NORMAL);
                    }

                }
            }
            if (idk == KeyEvent.VK_H) {
                System.out.println("==> KEY HIDE STAT <==");
                System.out.printf("==> %s <==\n", event.getSource());
                MantraCoreRunner.windowHook.setState(JFrame.ICONIFIED);
            }
        }

/*        System.out.println("=== KEY EVENT ===");
        System.out.println("KEY EVENT FIRED: "+event.getKeyChar());
        System.out.println("--- KEY EVENT ---");*/

    }

}