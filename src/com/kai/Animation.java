package com.kai;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created: 05/03/2019
 * Author: Kai Tinkess
 */

public class Animation {

    //The name/title of the animation.
    private String title;
    //A collection of every frame in the animation
    private BufferedImage[] frames;

    public Animation(String title, BufferedImage[] frames) {
        this.title = title;
        this.frames = frames;
    }

    public Animation(BufferedImage[] frames) {
        this("Untitled", frames);
    }

    public Animation(String title, List<BufferedImage> frames) {
        this(title, (BufferedImage[]) frames.toArray());
    }

    public Animation(List<BufferedImage> frames) {
        this("Untitled", (BufferedImage[]) frames.toArray());
    }

    public Animation(Animation anim) {
        this.title = anim.getTitle();
        this.frames = anim.getFrames();
    }

    //Returns a specific frame.
    public BufferedImage getFrame(int index) {
        return frames[index];
    }

    //Returns the title of the animation.
    public String getTitle() {
        return title;
    }

    //Returns the animation's frames in an array.
    public BufferedImage[] getFrames() {
        return frames;
    }

    //Returns how many frames the animation has.
    public int getFrameCount() {
        return frames.length;
    }

}
