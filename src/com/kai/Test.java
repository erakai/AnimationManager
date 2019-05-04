package com.kai;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        JFrame tester = new JFrame("Test");
        TestPlayer panel = new TestPlayer();
        panel.setPreferredSize(new Dimension(500, 500));
        tester.add(panel);

        tester.pack();
        tester.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tester.setVisible(true);

        new Timer(10, (e) -> panel.repaint()).start();
    }


    private static class TestPlayer extends JPanel {
        private AnimationPlayer anim = new AnimationPlayer();
        BufferedImage image1;

        public TestPlayer() {
            anim.setFramesPerSecond(1);


            try {
                image1 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/wizzart_f_run_anim_f1.png"));
                BufferedImage image2 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/wizzart_f_run_anim_f2.png"));
                BufferedImage image3 = ImageIO.read(Test.class.getResourceAsStream("/com/kai/wizzart_f_run_anim_f3.png"));
                BufferedImage[] frames = {image1, image2, image3};
                String title = "idle";

                anim.setIdleAnim(title, frames);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage( anim.nextFrame(), 30, 30, null);
            g.drawImage(image1, 30, 300, null);
        }

    }
}
