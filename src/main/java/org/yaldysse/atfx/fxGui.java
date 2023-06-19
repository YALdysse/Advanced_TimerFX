package org.yaldysse.atfx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class fxGui
{
    public static final double rem = new Text().getBoundsInParent().getHeight();

    public static double calculateTextWidth(final Font aFont, final String aText)
    {
       Text text = new Text(aText);
       text.setFont(aFont);
       return text.getBoundsInParent().getWidth();
    }

    public static ImageView createImageView(final Image image, final double size)
    {
        ImageView iv = new ImageView(image);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setFitWidth(size);
        return iv;
    }
}
