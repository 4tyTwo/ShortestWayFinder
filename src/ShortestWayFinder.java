import java.awt.*;
import java.util.Scanner;

public class ShortestWayFinder {

    private int matrix[][]; //Представление матрицы, хранящее путь до текущей точки
    private Point[] path; //Массив точек, через которые проходит путь
    private int rows, columns;
    private int finishRow,finishColumn;
    /*Внутреннее представление матрицы:
    -3: Препядствие
    -2: Свободная клетка
    -1: Конечная точка
     0: Исходная клетка
    1+: Расстояние от этой клетки до исходной точки
     */


    public static void main(String[] args){
        //Пока ради скорости отключено
        Scanner reader = new Scanner(System.in);
        System.out.print("Введите количесвто строк матрицы: ");
        int rows = reader.nextInt();
        String[] map = new String[rows];
        System.out.println("Введите матрицу");
        reader.nextLine();
        for (int i = 0; i < rows; ++i){
            map[i] = reader.nextLine();
        }
        /*String[] map = {"****X",
                        "*####",
                        "*****",
                        "***#*",
                        "*O***"};*/
        ShortestWayFinder swf = new ShortestWayFinder(map);
        int distance = swf.findPath();
        if (distance > 0) {
            System.out.println("Кратчайший путь " + String.valueOf(distance) + "\nВизуализация:");
            swf.printMatrix();
        }
        else{
            System.out.println("Путь не был обнаружен");
        }

    }

    public ShortestWayFinder(String[] source){
        //Инициализируется
        rows = source.length;
        columns = source[0].length();
        //Проверка на првильность длины каждой строки
        if(!(checkColumnsLength(source) && checkIfFinishExistsAndSingle(source) && checkIfStartExistsAndSingle(source)))
            System.exit(1); //Выход из программы в случае неправильных данных
        matrix = new int[rows][columns];
        for (int i = 0; i < rows; ++i){
            for (int j = 0; j < columns; ++j){
                switch (source[i].charAt(j)){
                    case '#': matrix[i][j] = -3; //Препядствие
                        break;
                    case 'X': matrix[i][j] = -1; //Конечная клетка
                        break;
                    case 'O': matrix[i][j] = 0; //Исходная клетка
                        break;
                    default: matrix[i][j] = -2; //Неопределнность трактуется как свободная клетка
                        break;
                }
            }
        }
    }

    public int findPath(){
        int[][]tmpMatrix = new int[rows][columns];
        for (int i = 0; i < rows; ++i) {
            System.arraycopy(matrix[i], 0, tmpMatrix[i], 0, columns);
        }
        int step = 0, distance = 0;
        boolean finishReached = false, deadlock = false;
        findFinish();
        while(!finishReached && !deadlock){
            deadlock = true; //Если за итерацию путь не был продолжен, то мы попали в неразрешимую ситуацию
            for (int i = 0; i < rows; ++i){
                for (int j = 0; j < columns; ++j) {
                    //Условимся, что движение по диагонали с шагом 1 невозможно
                        if(tmpMatrix[i][j] == distance){
                            //4 клетки вокруг
                            if (i - 1 >= 0) {
                                if (tmpMatrix[i - 1][j] == -2 || tmpMatrix[i - 1][j] == -1) {
                                    tmpMatrix[i - 1][j] = distance + 1;
                                    deadlock = false;
                                }
                            }
                            if (i + 1 < rows) {
                                if (tmpMatrix[i + 1][j] == -2 || tmpMatrix[i + 1][j] == -1) {
                                    tmpMatrix[i + 1][j] = distance + 1;
                                    deadlock = false;
                                }
                            }
                            if (j - 1 >= 0) {
                                if (tmpMatrix[i][j - 1] == -2 || tmpMatrix[i][j - 1] == -1) {
                                    tmpMatrix[i][j - 1] = distance + 1;
                                    deadlock = false;
                                }
                            }
                            if (j + 1 < columns) {
                                if (tmpMatrix[i][j + 1] == -2 || tmpMatrix[i][j + 1] == -1) {
                                    tmpMatrix[i][j + 1] = distance + 1;
                                    deadlock = false;
                                }
                            }
                        }
                }
            }
            distance++;
            if (tmpMatrix[finishRow][finishColumn]!= -1){
                path = new Point[distance+1];
                createPath(tmpMatrix);
                return distance;
            }
        }
        return -1; //Путь не найден
    }

    private void createPath(int[][] tmpMatrix){
        //При наличии равно великих путей, выбираться будет только 1
        int currRow = finishRow, currColumn = finishColumn;
        boolean moved;
        int i = 0;
        int curr = tmpMatrix[currRow][currColumn];
        while (curr!=0){
            moved = false;
            if (currRow - 1 >= 0) {
                if (tmpMatrix[currRow - 1][currColumn] == curr - 1 && !moved) {
                    --curr;
                    --currRow;
                    moved = true;
                }
            }
            if (currRow + 1 < rows) {
                if (tmpMatrix[currRow + 1][currColumn] == curr - 1 && !moved) {
                    --curr;
                    ++currRow;
                    moved = true;
                }
            }
            if (currColumn - 1 >= 0) {
                if (tmpMatrix[currRow][currColumn - 1] == curr - 1 && !moved) {
                    --curr;
                    --currColumn;
                    moved = true;
                }
            }
            if (currColumn + 1 < columns) {
                if (tmpMatrix[currRow][currColumn + 1] == curr - 1 && !moved) {
                    --curr;
                    ++currColumn;
                    moved = true; //Не нужно, но есть для единообразия кода
                }
            }
            path[i++] = new Point(currRow,currColumn);
            matrix[currRow][currColumn] = curr;
        }
    }

    public void printMatrix(){
        for (int i = 0; i < matrix.length; ++i){
            for (int j = 0; j < matrix[i].length; ++j) {
                switch (matrix[i][j]){
                    case -3: System.out.print('#');
                        break;
                    case -2: System.out.print('.');
                        break;
                    case -1: System.out.print('X');
                        break;
                    case 0: System.out.print('O');
                        break;
                    default: System.out.print('*');
                        break;
                }
            }
            System.out.print('\n');
        }
    }

    private void findFinish(){
        //Для удобства нам лучше знать кординаты финишной точки
        for (int i = 0; i < matrix.length; ++i){
            for (int j = 0; j < matrix[i].length; ++j){
                if(matrix[i][j] == -1){
                    finishRow = i;
                    finishColumn = j;
                    return;
                }
            }
        }
    }

    private boolean checkColumnsLength(String[] checked) {
        //Проверка длины строк, она додна быть одинаковой
        for (int i = 1; i < checked.length; ++i) {
            if (checked[i].length() != checked[0].length()) {
                System.out.println("Ошибка: Строки матрицы не равны по длине");
                return false;
            }
        }
        return true;
    }

    private boolean checkIfFinishExistsAndSingle(String[] checked){
        //Проверка существования конечной точки
        int finishCounter = 0;
        for (int i = 0; i < checked.length; ++i) {
            if (checked[i].indexOf('X') != -1) {
                ++finishCounter;
            }
        }
        if (finishCounter != 1){
            System.out.println("Ошибка: Конечная точка не найдена или неединственна");
            return false;
        }
        return true;
    }

    private boolean checkIfStartExistsAndSingle(String[] checked){
        //Проверка существования начальной точки
        int startCounter = 0;
        for (int i = 0; i < checked.length; ++i) {
            if (checked[i].indexOf('O') != -1){
                ++startCounter;
            }
        }
        if(startCounter != 1) {
            System.out.println("Ошибка: Начальная точка не найдена или неединственна");
            return false;
        }
        return true;
    }
}
