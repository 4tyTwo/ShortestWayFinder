import java.util.Scanner;

public class ShortestWayFinder {

    private int matrix[][]; //Представление матрицы, хранящее путь до текущей точки
    /*Внутреннее представление матрицы:
    -3: Препядствие
    -2: Свободная клетка
    -1: Конечная точка
     0: Исходная клетка
    1+: Расстояние от этой клетки до исходной точки
     */


    public static void main(String[] args){
        //Пока ради скорости отключено
        /*Scanner reader = new Scanner(System.in);
        System.out.print("Введите размеры матрицы в формате строки столбцы: ");
        int rows = reader.nextInt();
        String[] map = new String[rows];
        System.out.println("Введите матрицу");
        reader.nextLine();
        for (int i = 0; i < rows; ++i){
            map[i] = reader.nextLine();
        }*/
        String[] map = {"****X","*###*","*****","***#*","*O***"};
        ShortestWayFinder swf = new ShortestWayFinder(map);
    }

    public ShortestWayFinder(String[] source){
        //Инициализируется
        int rows = source.length, columns = source[0].length();
        //Проверка на првильность длины каждой строки
        for (int i = 1; i < rows; ++i){
                if (source[i].length() != columns){
                    System.out.println("Строки матрицы не равны по длине, дальнейшая работа невозможна");
                    System.exit(1);
                }
        }
        matrix = new int[rows][columns];
        for (int i = 0; i < rows; ++i){
            for (int j = 0; j < columns; ++j){
                switch (source[i].charAt(j)){
                    case '#': matrix[i][j] = -3; //Препядствие
                        break;
                    case 'X': matrix[i][j] = -1; //Конечная клетка
                        break;
                    case 'O': matrix[i][j] = -0; //Исходная клетка
                        break;
                    default: matrix[i][j] = -2; //Неопределнность трактуется как свободная клетка
                        break;
                }
            }
        }
    }

    private boolean checkCollumnsLength(String[] checked) {
        //Проверка длины строк, она додна быть одинаковой
        for (int i = 1; i < checked.length; ++i) {
            if (checked[i].length() != checked[0].length()) {
                System.out.println("Ошибка: Строки матрицы не равны по длине");
                return false;
            }
        }
        return true;
    }

    private boolean checlIfFinishExists(String[] checked){
        //Проверка существования конечной точки
        for (int i = 0; i < checked.length; ++i) {
            if (checked[i].indexOf('X') != -1) {
                System.out.println("Ошибка: Конечная точка не найдена");
                return true;
            }
        }
        return false;
    }

    private boolean checlIfStartExists(String[] checked){
        //Проверка существования начальной точки
        for (int i = 0; i < checked.length; ++i) {
            if (checked[i].indexOf('O') != -1){
                System.out.println("Ошибка: Начальная точка не найдена");
                return true;
            }
        }
        return false;
    }
}
