/*
 * Copyright (C) Yaroslav Lytvynov (aka YALdysse) 2021 <Yaroslav_A_Litvinov@yahoo.com>
 *
 * Advanced TimerFX is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Advanced TimerFX is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @license GPL-3.0+ <https://spdx.org/licenses/GPL-3.0+.html>
 */

package org.yaldysse.atfx;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalTime;
/**
 * Собрание частоиспользуэмых алгоритмов
 */
public class YALtools
{
    public static void printDebugMessage(final String aMessage)
    {
        System.out.println("[" + LocalTime.now() + "] " + aMessage);
    }

    /**
     * @param aIS Обьект типа InputStream, данные которого нужно прочитать
     * @return StringBuilder со считанными данными
     * @throws java.io.IOException
     * @author YALdysse
     */
    public static StringBuilder readInputStream(final InputStream aIS) throws java.io.IOException
    {
        StringBuilder strBuilder_obj = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(aIS);
        BufferedReader br = new BufferedReader(isr);

        String tmpStr = br.readLine();

        while (tmpStr != null)
        {
            strBuilder_obj.append(tmpStr);
            strBuilder_obj.append("\n");
            tmpStr = br.readLine();
        }
        return strBuilder_obj;
    }

    /**Создает обьект шрифта с файла для использования с JavaFX
     * Рекомендуется использовать с Maven, т.к. он точно задает путь к ресурсам: src/main/resources
     * @param aPathToFont Путь к файлу шрифта
     * @param aSize Размер шрифта, который хотите получить
     * @throws java.io.IOException
     * @author YALdysse
     * */
    public static javafx.scene.text.Font createFontFXFromResources(final String aPathToFont, final double aSize) throws java.io.IOException
    {
        URL pathToFont_URL = YALtools.class.getClassLoader().getResource(aPathToFont);

        return javafx.scene.text.Font.loadFont(pathToFont_URL.openStream(), aSize);
    }

    /**Возвращает путь к исполняемому jar(class) файлу
     * @throws URISyntaxException
     * */
    public static File getJarLocation() throws URISyntaxException
    {

        //return new File("");
        return new File (YALtools.class.getProtectionDomain().getCodeSource().getLocation().toURI());
    }

}