package com.kai;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        JFrame tester = new JFrame("Test");
        TestPlayer panel = new TestPlayer();
        panel.setPreferredSize(new Dimension(700, 200));
        tester.add(panel);

        tester.pack();
        tester.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tester.setVisible(true);

        new Timer(10, (e) -> panel.repaint()).start();
    }


    private static class TestPlayer extends JPanel {
        private AnimationPlayer anim = new AnimationPlayer();
        private int px = 30, py = 30;
        private boolean right;

        public TestPlayer() {
            anim.setFramesPerSecond(5);

            try {
                BufferedImage run1 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/wizzart_f_run_anim_f1.png"));
                BufferedImage run2 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/wizzart_f_run_anim_f2.png"));
                BufferedImage run3 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/wizzart_f_run_anim_f3.png"));
                BufferedImage[] runFrames = {run1, run2, run3};
                String runTitle = "run";
                anim.addAnim(runTitle, runFrames);

                BufferedImage idle1 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/wizzard_f_idle_anim_f1.png"));
                BufferedImage idle2 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/wizzard_f_idle_anim_f2.png"));
                BufferedImage idle3 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/wizzard_f_idle_anim_f3.png"));
                BufferedImage[] idleFrames = {idle1, idle2, idle3};
                String idleTitle = "idle";
                anim.addAnim(idleTitle, idleFrames);
                anim.setRepeatingAnim("idle");

                BufferedImage force0 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/big_demon_idle_anim_f0.png"));
                BufferedImage force1 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/big_demon_idle_anim_f1.png"));
                BufferedImage force2 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/big_demon_idle_anim_f2.png"));
                BufferedImage force3 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/big_demon_idle_anim_f3.png"));
                BufferedImage[] forceFrames = {force0, force1, force2, force3};
                String forceTitle = "demon";
                anim.addAnim(forceTitle, forceFrames);
            } catch (IOException e) {
                e.printStackTrace();
            }


            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case 68:
                            anim.setRepeatingAnim("run");
                            right = true;
                            break;
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case 68:
                            anim.setRepeatingAnim("idle");
                            right = false;
                            break;
                        case 83:
                            anim.playAnim("demon");
                            break;
                    }
                }
            });
            setFocusable(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(anim.nextFrame(), px, py, null);

            if (right) {
                px++;
            }
        }

    }
}
