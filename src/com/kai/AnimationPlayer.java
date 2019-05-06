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

    //An optional repeating anim that is returned when no animations are being played.
    private Animation repeatingAnim;

    //Returns the next frame in the anim current being played.
    public BufferedImage nextFrame() {

        long currentTime = System.currentTimeMillis();
        if ((1000.0 / getFramesPerSecond()) <= (currentTime - lastFrameChange) && playing) {
            currentFrame++;
            lastFrameChange = currentTime;
            //System.out.println("switching frames in the " + currentAnim.getTitle() + " anim");
        }
        //System.out.println("last frame change was " + (currentTime - lastFrameChange) + " ms ago");

        if (currentAnim == null || currentFrame >= (currentAnim.getFrameCount()) || !playing) {
            if (isAnimQueueEmpty()) {
                playing = false;
            } else {
                resetFieldsForNextAnim();
                currentAnim = animQueue.poll();
            }
        }
        if (!playing) {
            if (!(repeatingAnim == null)) {
                queueAnim(repeatingAnim);
                currentAnim = animQueue.poll();
                resetFieldsForNextAnim();
            } else {
                return null;
            }
        }

        return currentAnim.getFrame(currentFrame);
    }


    //Optionally clears the current queue and overwrites the current playing animation to play the requested anim.
    public void playAnim(Animation anim, boolean clear) {
        if (clear) clearAnimQueue();
        resetFieldsForNextAnim();

        currentAnim = new Animation(anim);
    }

    public void playAnim(String title, boolean clear) {
        playAnim(animations.get(title), clear);
    }

    public void playAnim(String title) {
        playAnim(animations.get(title), true);
    }

    public void playAnim(Animation anim) {
        playAnim(anim, true);
    }


    //Queues up an anim to play after currently queued anims are played.
    public void queueAnim(Animation anim) {
        animQueue.add(anim);
    }

    public void queueAnim(String title) {
        queueAnim(animations.get(title));
    }

    //Returns if the queue is empty or not.
    public boolean isAnimQueueEmpty() {
        return animQueue.isEmpty();
    }

    //Clears the current queue of anims.
    //Optionally stops playing the current anim.
    public void clearAnimQueue() {
        clearAnimQueue(false);
    }

    public void clearAnimQueue(boolean stopPlaying) {
        animQueue.clear();
        if (stopPlaying) {
            playing = false;
            currentAnim = null;
        }
    }

    //Stops the anim from playing and clears the queue.
    //Removes the repeating anim.
    public void stop() {
        clearAnimQueue();
        playing = false;
        currentAnim = null;
        removeRepeatingAnim();
    }

    //Resets the existing anim list and stops the player.
    public void reset() {
        stop();
        animations.clear();
    }

    //Helper methods that sets the fields specific per anim.
    public void resetFieldsForNextAnim() {
        currentFrame = 0;
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


    //Sets the repeating animation.
    //Overrides the current repeating anim. but not a queued or played anim.
    public void setRepeatingAnim(Animation anim) {
        Animation newRepeatingAnim = new Animation(anim);
        if (currentAnim == repeatingAnim) {
            currentAnim = newRepeatingAnim;
        }

        repeatingAnim = newRepeatingAnim;
    }

    public void setRepeatingAnim(String title) {
        setRepeatingAnim(animations.get(title));
    }

    public void removeRepeatingAnim() {
        repeatingAnim = null;
    }

    //Returns the specified anim
    public Animation getAnim(String title) {
        return animations.get(title);
    }

}
