package org.yaldysse.atfx.action;

import java.io.IOException;

/**Інтерфейс визначає супер-тип для всіх дій, що можуть
 * бути виконані після закінчення роботи таймеру.*/
public interface Action
{
    public void perform() throws IOException;
}
