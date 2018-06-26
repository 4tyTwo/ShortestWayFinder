import java.util.Scanner;

public class ShortestWayFinder {

    private int matrix[][]; //Представление матрицы, хранящее путь до текущей точки
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
        rows = source.length;
        columns = source[0].length();
        //Проверка на првильность длины каждой строки
        if(!(checkColumnsLength(source) && checkIfFinishExists(source) && checkIfStartExists(source)))
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
        int step = 0, distance = 0;
        boolean finishReached = false;
        findFinish();
        while(!finishReached && step < rows * columns){
            for (int i = 0; i < rows; ++i){
                for (int j = 0; j < columns; ++j) {
                    //Условимся, что движение по диагонали с шагом 1 невозможно
                    try {
                        if(matrix[i][j] == distance){
                            //4 клетки вокруг
                            if(matrix[i - 1][j] == -2 || matrix[i - 1][j] == -1  ){
                                matrix[i - 1][j] = distance + 1;
                            }
                            if(matrix[i + 1][j] == -2 || matrix[i + 1][j] == -1) {
                                matrix[i + 1][j] = distance + 1;
                            }
                            if(matrix[i][j - 1] == -2 || matrix[i][j - 1] == -1) {
                                matrix[i][j - 1] = distance + 1;
                            }
                            if(matrix[i][j + 1] == -2 || matrix[i][j + 1] == -1) {
                                matrix[i][j + 1] = distance + 1;
                            }
                        }
                    }
                    catch (IndexOutOfBoundsException e){
                        //pass
                        //TO DO заменить на нормальные проверки
                    }
                }
            }
            distance++;
            if (matrix[finishRow][finishColumn]!= -1){
                //printMatrix();
                return distance;
            }
        }
        return -1; //Путь не найден
    }

    private void findFinish(){
        //Для удобства нам лучше знать кординаты финишной точки
        for (int i = 0; i < matrix.length; ++i){
            for (int j = 0; j < matrix[i].length; ++i){
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

    private boolean checkIfFinishExists(String[] checked){
        //Проверка существования конечной точки
        for (int i = 0; i < checked.length; ++i) {
            if (checked[i].indexOf('X') != -1) {
                System.out.println("Ошибка: Конечная точка не найдена");
                return true;
            }
        }
        return false;
    }

    private boolean checkIfStartExists(String[] checked){
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
