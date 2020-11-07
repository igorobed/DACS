import com.google.gson.Gson;
import java.io.*;
import java.util.Stack;
import java.util.Iterator;

public class Lab1Final {
    public static void main(String[] argc) {
        //первый файл содержит все возможные скобочные пары
        //второй файл содержит текст, который над проверить на корректность скобочных последовательностей
        if (argc.length != 2) {
            System.out.println("Need only 2 data files: xxxx.json and xxxx.txt");
            return;
        }
        //строки с именами файлов
        String pathNameJson = argc[0];
        String pathNameText = argc[1];

        //считывание инфы о скобках
        ContainerWithBrackets typesBrackets = deserializeContainerWithBrackets(pathNameJson);
        //считывание обрабатываемой строки
        String checkedStr = new String();
        try(BufferedReader br = new BufferedReader(new FileReader(pathNameText))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            checkedStr = sb.toString();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        //написать метод проверки на корректность скобок внутри символьной последовательности
        if (isCorrect(checkedStr, typesBrackets)) {
            System.out.println("У нас правильная скобочная последовательность");
        }
        else {
            System.out.println("У нас НЕправильная скобочная последовательность");
        }

    }

    //напишем метод, для считывания инфы из JSON-файла
    private static ContainerWithBrackets deserializeContainerWithBrackets(String path) {
        ContainerWithBrackets tempContainerWithBrackets = new ContainerWithBrackets();
        try(FileReader fileReader = new FileReader(new File(path))) {
            Gson gson = new Gson();
            tempContainerWithBrackets = gson.fromJson(fileReader, ContainerWithBrackets.class);
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        return tempContainerWithBrackets;
    }

    //метод для проверки корректности скобочной последовательности
    private static boolean isCorrect(String in, ContainerWithBrackets brackets) {
        char tempChar;

        char tempBracket;
        //переменная, указывающая произошла ли несастыковка скобок в стэке или остались ли не закрытые скобки
        boolean inconsistency = false;//несоответствие = ложь
        ContainerWithBrackets.BracketsLeftRight tempLeftRight;
        //заведем стэк
        Stack<String> stackForCheck = new Stack<>();
        //запускаем цикл обработки проверяемой строки
        for (int i = 0; i < in.length(); i++) {
            //если найдено несоответствие прерываем цикл
            if (inconsistency) {
                break;
            }
            //берем очередной символ строки
            tempChar = in.charAt(i);
            Iterator<ContainerWithBrackets.BracketsLeftRight> iter = brackets.bracket.iterator();
            while (iter.hasNext()) {
                tempLeftRight = iter.next();
                if (tempChar == tempLeftRight.left.charAt(0)) {
                    //кладем в стэк и выскакиваем из цикла
                    stackForCheck.push(tempLeftRight.left);
                    break;
                }
                if (tempChar == tempLeftRight.right.charAt(0)) {
                    //смотрим на верхний элемент стэка
                    //если он парный к найденному, то удаляем его и выходим из цикла
                    //если стэк пуст или на верху лежит не парный эллемент несоответствие = истина
                    if (stackForCheck.size() == 0) {
                        inconsistency = true;
                        System.out.printf("Перед закрывающей скобкой %c(индекс: %d) отсутствует открывающая\n", tempChar, i);
                        break;
                    }
                    if (tempLeftRight.left == stackForCheck.peek()) {
                        stackForCheck.pop();
                    } else {
                        inconsistency = true;
                        System.out.printf("Перед закрывающей скобкой %c(индекс: %d) отсутствует открывающая\n", tempChar, i);
                    }
                    break;
                }
            }

        }
        //когда я окажусь тут мой стэк либо пуст, либ нет. и несоответствие тоже в одном из двух состояний
        if (inconsistency == true) {
            return false;
        }
        else {
            if (stackForCheck.isEmpty()) {
                return true;
            }
            else {
                System.out.println("Остались незакрытые скобки");
                StringBuilder sb = new StringBuilder();
                while (!stackForCheck.isEmpty()) {
                    sb.append(stackForCheck.pop());
                }
                System.out.println(sb.reverse().toString());

                return false;
            }
        }
    }
}
