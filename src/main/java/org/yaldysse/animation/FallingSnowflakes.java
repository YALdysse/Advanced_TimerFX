package org.yaldysse.animation;

import javafx.animation.AnimationTimer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Проблеми: потребує багато ресурсів
 * Об'єкти можуть падати вертикально
 */
public class FallingSnowflakes
{
    private final Pane pane;
    private ArrayList<Image> images;
    private ArrayList<ImageView> imageViews;
    private int maxObjectsInPane;
    private int minObjectSize;
    private int maxObjectSize;
    public final int MIN_OBJECT_ANIMATION_TIME;
    public final int MAX_OBJECT_ANIMATION_TIME;
    private double minOpacity;
    private double maxOpacity;
    private ThreadLocalRandom threadLocalRandom;
    private AnimationTimer animationTimer;
    private double[] speedX;
    private double[] speedY;
    boolean start = true;
    private long previousNano;
    private int frame = 0;
    private boolean running;

    public FallingSnowflakes(final Pane aPane, final int aMaxObjects,
                             final int aMinObjectSize, final int aMaxObjectSize)
    {
        images = new ArrayList<>();
        imageViews = new ArrayList<>();
        threadLocalRandom = ThreadLocalRandom.current();
        pane = aPane;
        MIN_OBJECT_ANIMATION_TIME = 6000;
        MAX_OBJECT_ANIMATION_TIME = 10000;
        minOpacity = 0.5D;
        maxOpacity = 0.95D;
        minObjectSize = 12;
        maxObjectSize = 24;
        maxObjectsInPane = aMaxObjects;
        running = false;

        //встановлюємо обмеження на root
        //pane.setMaxWidth(pane.getBoundsInParent().getWidth()+20.0D);
        //pane.setMaxHeight(pane.getBoundsInParent().getHeight()+20.0D);
        pane.setMaxWidth(600);
        pane.setMaxHeight(600);

        loadingImages();
        creatingImageViews();
        resetAllObjectsTranslateLocation();
        creatingArrayWithSpeedParameters();
        initializingAnimationTimer();
    }

    private void loadingImages()
    {
        images.add(new Image(this.getClass().getResourceAsStream("/Images/ForAnimations/FallingSnowflakes/Snowflake_00.png")));
    }

    private void creatingImageViews()
    {
        for (int k = 0; k < maxObjectsInPane; k++)
        {
            imageViews.add(createImageView(images.get(0), minObjectSize,
                    maxObjectSize));
        }
    }

    /**
     * Ініціалізує масив, в якому містятя значення на скількі пікселів буде
     */
    private void creatingArrayWithSpeedParameters()
    {
        speedY = new double[imageViews.size()];

        for (int k = 0; k < speedY.length; k++)
        {
            speedY[k] = threadLocalRandom.nextDouble(0.60D, 1.1D);

            //speed[k] = threadLocalRandom.nextDouble(0.02D, 0.2D);
        }


        speedX = new double[imageViews.size()];

        for (int k = 0; k < speedY.length; k++)
        {
            speedX[k] = threadLocalRandom.nextDouble(0.40D, 0.8D);

            if (threadLocalRandom.nextBoolean())
            {
                speedX[k] = speedX[k] * -1.0D;
            }
            //speed[k] = threadLocalRandom.nextDouble(0.02D, 0.2D);
        }
    }


    private void initializingAnimationTimer()
    {
        animationTimer = new AnimationTimer()
        {
            public void handle(long now)
            {
                animationHandle(now);
            }
        };

    }

    /**
     * О.
     */
    private void animationHandle(final long now)
    {
        if (frameLimiter(now))
        {
            animationFrame(now);
        }
    }

    /**
     * Цей метод виклакається безпосередньо для кожного кадру саме анімації.
     */
    private void animationFrame(final long now)
    {
        frame++;
        System.out.println("Frames: " + frame);

        ImageView iv;
        int continueNumber = 0;

        for (int k = 0; k < imageViews.size(); k++)
        {
            iv = imageViews.get(k);
            //System.out.println(k + "\t " + isObjectInVisibleRegion(iv));
            if (!isObjectInVisibleRegion(iv))
            {
                System.out.println("Об'єект " + k + " за межами");
                continueNumber++;

                ///--------new
                resetObjectTranslateLocation(iv);

//                if (continueNumber >= imageViews.size())
//                {
//                    System.out.println("Прериваємо анімацію");
//                    animationTimer.stop();
//                    resetAllObjectsTranslateLocation();
//                    animationTimer.start();
//                    break;
//                }
                continue;
            }

            iv.setTranslateX(iv.getTranslateX() + speedX[k]);
            iv.setTranslateY(iv.getTranslateY() + speedY[k]);
        }
    }

    /**
     * Для 60 кадрів.
     */
    private final long FRAMES_60 = 16_666_666L;
    private final long FRAMES_50 = 20_000_000L;

    /**
     * Повертає значення true, якщо з моменту previousNano пройшло 16 мілісекунд.
     * Таким чином, за допомогою цього методу анімація складається близько з 60 кадрів на
     * секунду
     */
    private boolean frameLimiter(final long now)
    {
        if (start)
        {
            previousNano = now;
            start = false;
            return false;
        }


        //більше 16 мілісекунд
        if (now - previousNano > FRAMES_50)
        {
            previousNano = now;
            return true;
        }
        return false;
    }

    private boolean isObjectInVisibleRegion(final ImageView iv)
    {
        if (iv.getTranslateX() > pane.getLayoutBounds().getWidth()
                || iv.getTranslateY() > pane.getLayoutBounds().getHeight())
        {
            return false;
        }
        return true;
    }

    private ImageView createImageView(final Image aImage, final int aMinSize, final int aMaxSize)
    {
        ImageView imageView = new ImageView(aImage);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(threadLocalRandom.nextInt(aMinSize, aMaxSize));
        imageView.setOpacity(threadLocalRandom.nextDouble(0.55D, 0.95D));
        //imageView.setVisible(false);
        return imageView;
    }

    private void resetAllObjectsTranslateLocation()
    {
        for (ImageView iv : imageViews)
        {
            resetObjectTranslateLocation(iv);
        }
    }

    private void resetObjectTranslateLocation(final ImageView iv)
    {

        iv.setTranslateX(threadLocalRandom.nextDouble(0.0D,
                pane.getBoundsInLocal().getWidth() - (pane.getLayoutBounds().getWidth() * 0.05)));
        //iv.setTranslateX(0.0D);
        //необхідно врахувати розмір сніжинки
        iv.setTranslateY(0.0D - iv.getBoundsInParent().getHeight());
        //iv.setTranslateY(0.0D);

    }

    private void addObjectsOnPageIfNotAdded()
    {
        for (ImageView iv : imageViews)
        {
            pane.getChildren().add(iv);
        }
    }

//    private Point2D generateXLocation()
//    {
//        double startX = threadLocalRandom.nextDouble(0.0D, pane.getBoundsInParent().getWidth());
//
//        double endX;
//        double subtract;
//
//        while (true)
//        {
//            //pane.getBoundsInParent().getWidth() - aNode.getBoundsInParent().getWidth()
//            endX = threadLocalRandom.nextDouble(0.0D, pane.getBoundsInParent().getWidth());
//            subtract = positive(endX - startX);
//
//            //якщо більше ніж 2%
//            if (subtract > pane.getBoundsInParent().getWidth() * 0.02D)
//            {
//                System.out.println("subtract: " + subtract);
//                break;
//            }
//        }
//
//        System.out.println("x: " + startX + "\tx1: " + endX);
//
//
//        return new Point2D(startX, endX);
//    }

    public void play()
    {
        addObjectsOnPageIfNotAdded();
        animationTimer.start();
        running = true;
    }

    public void stop()
    {
        animationTimer.stop();
        removeAllObjectsFromPane();
        running = false;
    }

    private void removeAllObjectsFromPane()
    {
        for (ImageView iv : imageViews)
        {
            pane.getChildren().remove(iv);
        }
    }

    public boolean isRunning()
    {
        return running;
    }
}
