package GeneralPackage;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.yaldysse.tools.notification.AnimationType;
import org.yaldysse.tools.notification.Notification;
import org.yaldysse.tools.notification.NotificationType;

public class ttt extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        Notification notify = new Notification("title", "aMessage");
        notify.setNotificationTitle("aTitle");
        notify.setMessage("aMessage");
        notify.setWidthPercent(0.25);
        notify.setHeightPercent(0.1D);
        notify.setIcon(NotificationType.INFORMATION);
        notify.setIconSizePercent(0.5D);
        notify.setDisplayDuration(Duration.INDEFINITE);
        notify.setDisappearanceAnimation(AnimationType.FADE, Duration.seconds(1.0D));
        notify.showAndWaitNotification();
    }
}
