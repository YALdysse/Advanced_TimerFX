package org.yaldysse.animation.flowers;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class FlowersGrowing
{
    private ArrayList<Image> images;
    private ArrayList<ImageView> imageViews;
    //    private ArrayList<SequentialTransition> animations;
    private ArrayList<ParallelTransition> animations;
    private int minAnimationDuration;
    private int maxAnimationDuration;
    private int maxObjects;
    private final ThreadLocalRandom threadLocalRandom;
    private Pane pane;
    private double xObjectsOffset;
    private double maxObjectSize;

    public FlowersGrowing(final Pane aPane)
    {
        images = new ArrayList<>();
        imageViews = new ArrayList<>();
        animations = new ArrayList<>();
        threadLocalRandom = ThreadLocalRandom.current();

        minAnimationDuration = 10_000;
        maxAnimationDuration = 20_000;
        maxObjects = 7;
        xObjectsOffset = 75.0D;
        maxObjectSize = 64.0D;

        pane = aPane;

        loadingImages();
        creatingImageViews();
        creatingAnimations();
        relocateObjects(imageViews);
    }

    private void loadingImages()
    {
        images.add(new Image(getClass().getResourceAsStream("/Images/ForAnimations/Spring/flower-pot.png")));
        images.add(new Image(getClass().getResourceAsStream("/Images/ForAnimations/Spring/tulips.png")));
        images.add(new Image(getClass().getResourceAsStream("/Images/ForAnimations/Spring/flower.png")));
    }

    private void creatingImageViews()
    {
        for (int k = 0; k < maxObjects; k++)
        {
            imageViews.add(createImageView(images.get(
                            threadLocalRandom.nextInt(0, images.size())),
                    maxObjectSize));
        }
    }

    private ImageView createImageView(final Image aImage, final double aSize)
    {
        ImageView iv = new ImageView(aImage);
        iv.setPreserveRatio(true);
        iv.setPreserveRatio(true);
        iv.setFitWidth(aSize);
        return iv;
    }

    private void creatingAnimations()
    {
        //SequentialTransition st;
        ParallelTransition pt;

        Duration duration;
        ScaleTransition sc;
        TranslateTransition tt;

        for (int k = 0; k < maxObjects; k++)
        {
            duration = Duration.millis(threadLocalRandom.nextInt(minAnimationDuration, maxAnimationDuration));
            sc = createScaleTransition(duration);
            tt = createTranslateTransition(duration);

            //st = new SequentialTransition(imageViews.get(k), sc);
            pt = new ParallelTransition(imageViews.get(k),
                    sc);
            animations.add(pt);
        }
    }

    private ScaleTransition createScaleTransition(final Duration aDuration)
    {
        ScaleTransition st = new ScaleTransition(aDuration);
        st.setFromX(0.01D);
        st.setFromY(0.01D);
        st.setToX(1.0D);
        st.setToY(1.0D);

        st.setInterpolator(Interpolator.EASE_OUT);
        return st;
    }

    private TranslateTransition createTranslateTransition(final Duration aDuration)
    {
        TranslateTransition tt = new TranslateTransition(aDuration);
        tt.setFromY(pane.getBoundsInParent().getHeight());
        tt.setToY(pane.getBoundsInParent().getHeight() -
                (imageViews.get(0).getBoundsInParent().getHeight() * 0.95D));
        //System.out.println("im: " + imageViews.get(0).getBoundsInParent().getHeight());

        tt.setInterpolator(Interpolator.EASE_OUT);
        return tt;
    }

    private void relocateObjects(final ArrayList<ImageView> ivs)
    {
        double x;

        for (int k = 0; k < ivs.size(); k++)
        {
            System.out.println(pane.getBoundsInParent().getWidth());
            System.out.println("pref: " + pane.getBoundsInParent().getWidth());

            x = xObjectsOffset * (k);
            if (x > pane.getBoundsInParent().getWidth())
            {
                double koef = x / pane.getBoundsInParent().getWidth();
                x = (koef - (int) koef) * pane.getBoundsInParent().getWidth();
                System.out.println("newX: " + x);
            }
            ivs.get(k).setTranslateX(x);
            //ivs.get(k).setTranslateY(50.0D);
            ivs.get(k).setTranslateY(pane.getBoundsInParent().getHeight() - ivs.get(k)
                    .getBoundsInParent().getHeight());
        }
    }

    private void addingObjectsOnPaneIfNotAdded()
    {
        for (ImageView iv : imageViews)
        {
            if (!pane.getChildren().contains(iv))
            {
                pane.getChildren().add(iv);
            }
        }
    }

    private void playAllAnimations()
    {
        for (ParallelTransition st : animations)
        {
            st.play();
        }
    }

    public void play()
    {
        addingObjectsOnPaneIfNotAdded();
        playAllAnimations();
    }


    public void setObjectsXOffset(final double aOffsetX)
    {
        xObjectsOffset = aOffsetX;
        relocateObjects(imageViews);
    }

    public void setMaxFlowerSize(final double aSize)
    {
        if (aSize < 0.1D || aSize > 128.0D)
        {
            throw new IllegalArgumentException("");
        }
        maxObjectSize = aSize;

        for(ImageView iv : imageViews)
        {
            iv.setFitWidth(maxObjectSize);
        }
        relocateObjects(imageViews);
    }
}
