package com.company;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        //Для поиска несовершеннолетних используйте промежуточный метод filter() и терминальный метод count().
        Stream<Person> streamTeenager = persons.stream();
        long count = streamTeenager
                .filter(x -> (x.getAge() < 18))
                .count();
        //Для получения списка призывников потребуется применить несколько промежуточных методов filter(),
        // а также для преобразования данных из Person в String (так как нужны только фамилии) используйте метод map().
        // Так как требуется получить список List<String> терминальным методом будет collect(Collectors.toList()).
        Stream<Person> streamMilitaryPerson = persons.stream();
        List<String> surnamesMilitaryPerson = streamMilitaryPerson
                .filter(x -> (x.getAge() >= 18 && x.getAge() <= 27))
                .map(Person::getFamily)
                .collect(Collectors.toList());
        //Для получения отсортированного по фамилии списка потенциально работоспособных людей с высшим образованием
        // необходимо применить ряд промежуточных методов filter(), метод sorted() в который нужно будет положить
        // компаратор по фамилиям Comparator.comparing(). Завершить стрим необходимо методом collect().
        Stream<Person> streamWorkerPeople = persons.stream();
        List<Person> workerPeopleList = streamWorkerPeople
                .filter(x -> (x.getAge() >= 18 && x.getAge() <= 60 && x.getSex() == Sex.WOMAN && x.getEducation() == Education.HIGHER)
                        ||(x.getAge() >= 18 && x.getAge() <= 65 && x.getSex() == Sex.MAN && x.getEducation() == Education.HIGHER))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
    }
}
