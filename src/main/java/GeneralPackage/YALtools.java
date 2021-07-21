package GeneralPackage;

/* Vice City Helper
 * Copyright (C) 2021 Yaroslav Lytvynov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Собрание частоиспользуэмых алгоритмов
 */
public class YALtools
{

    /**
     * Импортирует шрифт из каталога с ресурсами ./Fonts/название_файла_шрифта
     *
     * @throws java.io.IOException
     * @throws java.awt.FontFormatException
     * @author YALdysse
     */
    public static Font createFont(final String fileName) throws java.io.IOException, java.awt.FontFormatException
    {
        URL fontFile_url = YALtools.class.getClassLoader().getResource("Fonts/" + fileName);
        return Font.createFont(Font.PLAIN, fontFile_url.openStream());
    }

    /**
     * Добавляет явные разделители строк в текст
     *
     * @param aMessage      Исходный текст, в который нужно добавить разделители строк.
     * @param aMaxLineWidth Ширина в пикселях одной строки текста.
     * @param aMessageFont  Шрифт, который будет применен к тексту. Нужен для подсчета ширины строки.
     * @return обект типа ArrayList<String>, содержащий строки с явно указанным разрывом
     * @author Y@Ldysse
     */
    public static ArrayList<String> SplitToLines(final String aMessage, final double aMaxLineWidth, final Font aMessageFont, final Component aComponent)
    {
        Toolkit toolkit_obj = Toolkit.getDefaultToolkit();
        Dimension screenDimension = toolkit_obj.getScreenSize();

        if (aMaxLineWidth > screenDimension.getWidth() ||
                aMaxLineWidth < 1)
        {
            throw new java.lang.NumberFormatException("Значение ширины строки не может превышать ширину по разрешению или быть меньше единицы.\n"
                    + "Текущее значение ширины строки: " + aMaxLineWidth);
        }

        double currentLineWidth = 0;
        double currentWordWidth = 0;
        StringBuilder strBuilder_obj = new StringBuilder();
        ArrayList<String> lines = new ArrayList<>();


        //Разбиваем текст на слова
        String[] words = aMessage.split(" ");

        //Добавляем пробелы - смахивает на бредятину
        for (int k = 0; k < words.length; k++)
        {
            words[k] = words[k] + " ";
        }
        //

        for (int k = 0; k < words.length; k++)
        {
            currentWordWidth = getTextWidth_Swing(words[k], aMessageFont, aComponent);

            if (currentWordWidth < aMaxLineWidth)//Если слово потенциально можно запихнуть в строку - запихиваем
            {
                //добавляем, если слово влазит
                if (currentLineWidth + currentWordWidth < aMaxLineWidth)
                {
                    currentLineWidth += currentWordWidth;
                    strBuilder_obj.append(words[k]);
                    //strBuilder_obj.append(" ");

                    if (k + 1 == words.length)
                    {
                        lines.add(strBuilder_obj.toString());
                    }
                } else //Переносим на новую строку
                {
                    printDebugMessage("Ширина текущей строки: " + currentLineWidth);
                    currentLineWidth = 0;
                    strBuilder_obj.append("\n");
                    lines.add(strBuilder_obj.toString());
                    strBuilder_obj.delete(0, strBuilder_obj.capacity());

                    strBuilder_obj.append(words[k]);
                    //strBuilder_obj.append(" ");
                }
            } else
            {
                throw new java.lang.NumberFormatException("Невозможно разбить текст на строки, если ширина слова превышает максимальный порог ширины строки.\n"
                        + "Ширина слова: " + currentWordWidth + "\tМаксимальная ширина строки: " + aMaxLineWidth);
            }
        }
        return lines;
    }

    /**
     * Возвращает самую длинную строку из коллекции строк ArrayList<String>.
     * Внимание: метод расчитан на роботу с коллекцией ArrayList<String> с соотношением: элемент коллекции - строка.
     *
     * @param aText_ArrayList Коллекция, включающая текст разбитый на строки в каждый элемент этой же коллекции
     * @return String з наибольшим количеством элементов
     * @Y@Ldysse
     */
    public static String getMostLengthString(final ArrayList<String> aText_ArrayList)
    {
        String maxLength_str = "";

        for (String currentLine_str : aText_ArrayList)
        {
            if (maxLength_str.length() < currentLine_str.length())
            {
                maxLength_str = currentLine_str;
            }
        }
        return maxLength_str;
    }

    /**
     * Возвращает ширину строки текста с учетом заданного шрифта
     *
     * @param aMessage     Входная строка текста
     * @param aMessageFont Шрифт, который будет применен к тексту
     * @param aComponent   Компонент, в котором будет отображен текст
     * @author Y@Ldysse
     */
    public static double getTextWidth_Swing(final String aMessage, final Font aMessageFont, final Component aComponent)
    {
        double textWidth = 0;
        FontMetrics fm_obj = aComponent.getFontMetrics(aMessageFont);

        for (int k = 0; k < aMessage.length(); k++)
        {
            textWidth += fm_obj.charWidth(aMessage.charAt(k));
        }

        return textWidth;
    }

    public static double getTextWidth(final String aMessage, final int countCharacters, final Font aMessageFont, final Component aComponent)
    {
        double textWidth = 0;
        FontMetrics fm_obj = aComponent.getFontMetrics(aMessageFont);

        for (int k = 0; k < countCharacters; k++)
        {
            textWidth += fm_obj.charWidth(aMessage.charAt(k));
        }

        return textWidth;
    }

    /**
     * Возвращает массив индексов вхождение определенного символа в тексте
     *
     * @param aText Входящий текст, в котором будет произведен поиск
     * @param aChar Символ, количество вхождений которого нужно подсчитать
     * @return int[] с индексами вхождения символа
     * @author Y@Ldysse
     */
    public static int[] searchCharInText(final String aText, final char aChar)
    {
        StringBuilder strBuilder_obj = new StringBuilder();

        int countElements = 0;

        for (int k = 0; k < aText.length(); k++)
        {
            if (aText.charAt(k) == aChar)
            {
                countElements++;
                strBuilder_obj.append(k);
                strBuilder_obj.append(";");
            }

        }
        String[] Indexes_str = strBuilder_obj.toString().split(";");

        int[] IndexesConcordance = new int[Indexes_str.length];

        for (int k = 0; k < Indexes_str.length; k++)
        {
            IndexesConcordance[k] = Integer.parseInt(Indexes_str[k]);
        }

        return IndexesConcordance;
    }

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

    public static File getJarLocation() throws URISyntaxException
    {

        //return new File("");
        return new File (YALtools.class.getProtectionDomain().getCodeSource().getLocation().toURI());
    }

}