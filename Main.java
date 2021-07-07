import java.util.Arrays;
import java.util.Random;

/**
 * Задание 2.1
 * На основе программного кода из домашнего задания №1 реализуйте массив на основе существующих
 * примитивных или ссылочных типов данных.
 * Выполните обращение к массиву и базовые операции класса Arrays.
 * Оценить выполненные методы с помощью базового класса System.nanoTime().
 * Задание 2.2
 * На основе написанного кода в задании 2.1 реализуйте линейный и двоичный поиск.
 * Оценить алгоритмы линейного и двоичного поиска с помощью базового класса System.nanoTime(),
 * при необходимости расширьте уже существующий массив данных.
 * Задание 2.3
 *
 * Создайте массив размером 400 элементов.
 * Выполните сортировку с помощью метода sort().
 * Оцените сортировку с помощью базового класса System.nanoTime().
 * Задание 2.4
 * На основе существующего массива данных из задания 2.3 реализуйте алгоритм сортировки пузырьком.
 * Оцените сортировку с помощью базового класса System.nanoTime().
 * Сравните время выполнения алгоритмы сортировки методом sort() из задания 2.1 и сортировку пузырьком.
 * Задание 2.5
 * На основе массива данных из задания 2.3 реализуйте алгоритм сортировки методом выбора.
 * Оцените сортировку с помощью базового класса System.nanoTime().
 * Сравните с временем выполнения алгоритмов сортировки из прошлых заданий 2.3 и 2.4.
 * Задание 2.6
 * На основе массива данных из задания 2.3 реализуйте алгоритм сортировки методом вставки.
 * Оцените сортировку с помощью базового класса System.nanoTime().
 * Сравните с временем выполнения алгоритмов сортировки из прошлых заданий 2.3, 2.4 и 2.5.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
    int[] myArray = new int[10000];
    int[] myArrayCopy;
    int[] myArrayCopy2;
    int[] myArrayCopy3;

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            myArray[i] = random.nextInt(998);
        }
        myArray[9999] = 999; //Сделаем последний элемент таким который мы будем искать,
        // чтобы оценить разницу худшей ситуации в линейном поиске.
        myArrayCopy = Arrays.copyOf(myArray, myArray.length);
        myArrayCopy2 = Arrays.copyOf(myArray, myArray.length);
        myArrayCopy3 = Arrays.copyOf(myArray, myArray.length);
        //System.out.println(Arrays.toString(myArray));
        //System.out.println(Arrays.equals(myArray, myArrayCopy));
        Thread treadsort = new Thread(()->{
            Long start = System.nanoTime();
            Arrays.sort(myArrayCopy);
            Long end = System.nanoTime();
            System.out.println("Сортировка методом sort() заняло " + ((double)(end - start)*0.000001) + " миллисекунд");
            System.out.println("=======================================================");
        });
        treadsort.start();
        treadsort.join();
        Thread treadSortBubble = new Thread(()->{
            Long start = System.nanoTime();
            bubbleSort(myArray);
            Long end = System.nanoTime();
            System.out.println("Сортировка пузырьковым методом заняло " + ((double)(end - start)*0.000001) + " миллисекунд");
            System.out.println("=======================================================");
        });
        treadSortBubble.start();
        treadSortBubble.join();
        Thread treadInsertSort = new Thread(()->{
            Long start = System.nanoTime();
            insertSort(myArrayCopy3);
            Long end = System.nanoTime();
            System.out.println("Сортировка методом вставки заняло " + ((double)(end - start)*0.000001) + " миллисекунд");
            System.out.println("=======================================================");
        });
        treadInsertSort.start();
        treadInsertSort.join();
        Thread treadSelectionSort = new Thread(()->{
            Long start = System.nanoTime();
            selectionSort(myArrayCopy2);
            Long end = System.nanoTime();
            System.out.println("Сортировка методом выбора заняло " + ((double)(end - start)*0.000001) + " миллисекунд");
            System.out.println("=======================================================");
        });
        treadSelectionSort.start();
        treadSelectionSort.join();
        Thread thread1 = new Thread(()-> findArrayElementLine(myArray, 999));
        Thread thread2 = new Thread(()-> findArrayElementBinary(myArrayCopy,999));
        thread1.start();
        thread1.join();
        thread2.start();
    }

    private static void bubbleSort(int[] myArray) {
        for (int out = myArray.length - 1; out >= 1; out--){  //Внешний цикл
            for (int in = 0; in < out; in++){       //Внутренний цикл
                if(myArray[in] > myArray[in + 1])               //Если порядок элементов нарушен
                    toSwap(myArray,in, in + 1);//вызвать метод, меняющий местами
            }
        }
    }

    private static void toSwap(int[] a, int first, int second){ //метод меняет местами пару чисел массива
        int dummy = a[first];      //во временную переменную помещаем первый элемент
        a[first] = a[second];       //на место первого ставим второй элемент
        a[second] = dummy;          //вместо второго элемента пишем первый из временной памяти
    }

    public static void findArrayElementLine(int[] array, int findElement){
        Long start = System.nanoTime();
        for (int i = 0; i <array.length ; i++) {
            if (array[i] == findElement) {
                Long end = System.nanoTime();
                System.out.println(findElement + " является " + ++i + " элементом в массиве"
                + "\nЛинейный поиск занял  " + (end - start) + " наносекунд"
                +"\n=======================================================");
                return;
            }
        }
        System.out.println(0);
        Long end = System.nanoTime();
        System.out.println( "Элемент не найден\nЛинейный поиск занял  " +  (end - start) + " наносекунд");
    }

    public static void findArrayElementBinary(int[] array, int findElement){
        Long start = System.nanoTime();
        int position;
        int comparisonCount = 1;    // для подсчета количества сравнений
        int first = 0;
        int last = array.length-1;

        // для начала найдем индекс среднего элемента массива
        position = (first + last) / 2;

        while ((array[position] != findElement) && (first <= last)) {
            comparisonCount++;
            if (array[position] > findElement) {  // если число заданного для поиска
                last = position - 1; // уменьшаем позицию на 1.
            } else {
                first = position + 1;    // иначе увеличиваем на 1
            }
            position = (first + last) / 2;
        }
        if (first <= last) {
            Long end = System.nanoTime();
            //System.out.println(findElement + " является " + ++position + " элементом в массиве");
            System.out.println("Метод бинарного поиска нашел число после " + comparisonCount +
                    " сравнений"+ "\n" + findElement + " является " + ++position + " элементом в массиве" +
                    "\nБинарный поиск занял  " + (end - start) + " наносекунд");
        } else {
            System.out.println("Элемент не найден в массиве. Метод бинарного поиска закончил работу после "
                    + comparisonCount + " сравнений");
            Long end = System.nanoTime();
            System.out.println("Бинарный поиск занял:  " + (end - start) + " наносекунд");
        }
    }

    public static void selectionSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
        /*Предполагаем, что первый элемент является минимальным */
            int min = arr[i];
            int min_i = i;
        /*В оставшейся части ищем элемент, который меньше предположенного минимума*/
            for (int j = i+1; j < arr.length; j++) {
                //Если находим, запоминаем его индекс
                if (arr[j] < min) {
                    min = arr[j];
                    min_i = j;
                }
            }
        /*Если нашелся элемент, меньший, чем на текущей позиции,
          меняем их местами*/
            if (i != min_i) {
                toSwap(arr, i, min_i);
            }
        }
    }

    public static void insertSort(int[] arr){
        int buff;
        int in;
        for (int i = 1; i <arr.length ; i++) {
            buff = arr[i];
            in = i;
            while (in>0 && arr[in - 1] >= buff){
                arr[in] = arr[in -1];
                --in;
            }
            arr[in]=buff;
        }
    }
}