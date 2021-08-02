package Collections;

import java.util.*;

/**
 Задание 3.1
 На основе массива из домашнего задания 2.1 реализуйте простой список и коллекцию.
 Оцените время выполнения преобразования.

 Задание 3.2
 На основе списка из задания 3.1 реализуйте основные методы добавления, удаления и
 получения объекта или элемента из списка.
 Оценить выполненные методы с помощью базового класса System.nanoTime().

 Задание 3.3
 Реализуйте простой односвязный список и его базовые методы.

 Задание 3.4
 На основе списка из задания 3.1 реализуйте простой двусторонний список и его базовые методы.
 Реализуйте список заполненный объектами из вашего класса из задания 1.3

 Задание 3.5
 Реализуйте итератор на основе связанных списков из задания 3.4 и выполните базовые операции итератора.
 Оцените время выполнения операций с помощью базового метода System.nanoTime()
 */

public class MyCollections {
    public static void main(String[] args) {

        MyInt[] myArray = new MyInt[10000];
        MyInt[] myArrayCopy;
        Integer[] IntegerArr = new Integer[10000];

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            myArray[i] = new MyInt(random.nextInt(998));
            IntegerArr[i] = random.nextInt(1000);
        }
        IntegerArr[999] = 10009;

        myArrayCopy = Arrays.copyOf(myArray, myArray.length);

        Long start = System.nanoTime();
        List<Integer> myIntArrayList = Arrays.asList(IntegerArr);
        Long end = System.nanoTime();
        System.out.println("Преобразование массива в список заняло: " + (end - start) + " nanosec\n" +
                "======================================================\n");

        start = System.nanoTime();
        List<MyInt> myIntCollection = new ArrayList<>();
        Collections.addAll(myIntCollection, myArrayCopy);
        end = System.nanoTime();
        System.out.println("Преобразование массива в коллекцию заняло: " + (end - start) + " nanosec\n" +
                "======================================================\n");

        start = System.nanoTime();
        myIntArrayList.remove("1000");
        myIntArrayList.set(999, 1000);
        myIntArrayList.get(999);
        end = System.nanoTime();
        System.out.println("Добавленный элемент: "+ myIntArrayList.get(999));
        System.out.println("Процесс удаления, добавления и получения элемента по индексу" +
                " из списка заняло : " + (end - start) + " наносекунд" +
                "\n=====================================================\n");

        start = System.nanoTime();
        myIntCollection.remove(999);
        myIntCollection.add(999, new MyInt(1000));
        myIntCollection.get(999);
        end = System.nanoTime();
        System.out.println("Добавленный элемент: "+ myIntCollection.get(999).getNumber());
        System.out.println("Процесс удаления, добавления и получения элемента по индексу" +
                " из коллекции заняло : " + (end - start) + " наносекунд" +
                "\n=====================================================\n");

        MyLinkedList<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.insert("Bob");
        myLinkedList.insert("Bond");
        myLinkedList.insert("Bibo");
        myLinkedList.insert("Bobo");

        myLinkedList.display();
    }

    /**
     *  Реализуем сначала элемент списка
     */
    public static class Link<T> {
        private T link;
        private Link<T> next;

        public Link() {
            this.next = null;
        }

        public Link(Link<T> next) {
            this.next = next;
        }

        public Link(T link) {
            this.link = link;
        }

        public T getLink() {
            return link;
        }

        public Link<T> getNext() {
            return next;
        }
        public void setNext(Link<T> next) {
            this.next = next;
        }
    }

    /**
     * Создаем класс который будет собирать элементы в список
     * @param <T>
     */

    static class MyLinkedList<T>{
        private Link<T> first;

        public MyLinkedList() {
            this.first = null;
        }

        public void insert(T link){
            Link<T> l = new Link <T>(link);
            l.setNext(first);
            this.first = l;
        }

        public boolean isEmpty(){
            return (first == null);
        }

        public Link<T> delete(){
            Link<T> tmp = first;
            first = first.getNext();
            return tmp;
        }

        public void display(){
            Link<T> current = first;
            while (current != null) {
                System.out.print(current.getLink());
                current = current.getNext();
                if(current!=null){
                    System.out.print(",");
                }
            }
            if(first == null){
                System.out.print("null");
            }
            System.out.println();
        }

        public T find (T search){
            Link<T> find = new Link<T>(search);
            Link<T> current = first;
            while (current != null){
                if(current.getLink().equals(find.getLink())){
                    return find.getLink();
                }
            }
            return null;
        }
    }


    static class MyInt{
        private int number;

        public MyInt(int number){
            this.number = number;
        }

        public int getNumber(){
            return number;
        }

        public void setNumber(int number){
            this.number = number;
        }
    }
}


