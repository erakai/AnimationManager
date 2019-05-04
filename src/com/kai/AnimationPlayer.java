package com.kai;

//TODO: Revisit and rewrite with proper JavaDoc.

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created: 05/03/2019
 * Author: Kai Tinkess
 */

public class AnimationPlayer {

    //Represents the number of frames displayed per second.
    //If playing an anim with 5 frames, and while being called 10 times per second, each frame is returned twice.
    private int framesPerSecond = 5;

    //A map of all anims currently in the animation player.
    private HashMap<String, Animation> animations = new HashMap<>();

    //The time of the last time the current frame changed
    private long lastFrameChange = -1;

    //The current frame inside the currently playing anim.
    private int currentFrame = 0;

    //The anim that is currently playing.
    private Animation currentAnim;

    //Represents whether or not the player is currently playing.
    private boolean playing = false;

    //The current queue of anims to be played.
    private Queue<Animation> animQueue = new LinkedList<>();

    //An optional idle anim that is returned when no animations are being played.
    private Animation idleAnim;

    //Returns the next frame in the anim current being played.
    public BufferedImage nextFrame() {

        long currentTime = System.currentTimeMillis();
        if ((1000.0 / getFramesPerSecond()) <= (currentTime - lastFrameChange)) {
            currentFrame++;
            lastFrameChange = currentTime;
        }

        if (currentAnim == null || currentFrame >= (currentAnim.getFrameCount()) || !playing) {
            if (animQueue.isEmpty()) {
                playing = false;
            } else {
                resetFieldsForNextAnim();
                currentAnim = animQueue.poll();
            }
        }
        if (!playing) {
            if (!(idleAnim == null)) {
                playAnim(idleAnim);
                resetFieldsForNextAnim();
            }
            return null;
        }

        return currentAnim.getFrame(currentFrame);
    }


    //Optionally clears the current queue and overwrites the current playing animation to play the requested anim.

    public void forceAnim(Animation anim, boolean clear) {
        if (clear) clearAnimQueue();
        resetFieldsForNextAnim();

        currentAnim = new Animation(anim);
    }

    public void forceAnim(String title, boolean clear) {
        forceAnim(animations.get(title), clear);
    }

    public void forceAnim(String title) {
        forceAnim(animations.get(title), true);
    }

    public void forceAnim(Animation anim) {
        forceAnim(anim, true);
    }


    //Queues up an anim to play after currently queued anims are played.
    public void playAnim(Animation anim) {
        animQueue.add(anim);
    }

    public void playAnim(String title) {
        playAnim(animations.get(title));
    }


    //Clears the current queue of anims
    public void clearAnimQueue() {
        animQueue.clear();
    }

    //Helper methods that sets the fields specific per anim.
    public void resetFieldsForNextAnim() {
        currentFrame = 0;
        lastFrameChange = System.currentTimeMillis();
        playing = true;
    }

    //Adds an anim to the map of anims held by this player.
    //Returns true if the add request was successful.
    public boolean addAnim(String title, BufferedImage[] frames) {
        return addAnim(new Animation(title, frames));
    }

    public boolean addAnim(String title, List<BufferedImage> frames) {
        return addAnim(new Animation(title, frames));
    }

    public boolean addAnim(Animation anim) {
        //Duplicate anims or anims of 0 length are not permitted.
        if (containsAnim(anim.getTitle())) return false;
        if (anim.getFrameCount() == 0) return false;
        if (anim == null) return false;

        animations.put(anim.getTitle(), anim);
        return true;
    }


    //Returns true if the animation player has an anim with the inputted title.
    public boolean containsAnim(String title) {
        return animations.keySet().contains(title);
    }

    //Retrieves the frames per second.
    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    //Sets the frames per second.
    public void setFramesPerSecond(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }


    //Sets the idle animation.
    public void setIdleAnim(Animation anim) {
        idleAnim = new Animation(anim);
    }

    public void setIdleAnim(String title, BufferedImage[] frames) {
        setIdleAnim(new Animation(title, frames));
    }

    public void setIdleAnim(String title, List<BufferedImage> frames) {
        setIdleAnim(new Animation(title, frames));
    }

    public void setIdleAnim(String title) {
        setIdleAnim(animations.get(title));
    }

    public void removeIdleAnim() {
        idleAnim = null;
    }

}
