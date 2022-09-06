package homeWork;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class firstTask {
    public static final String SEPARATOR = "\n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n";


    public static void main(String[] args) {
                /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени

            Что должно получиться Key: Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */

        //FIRST this line "Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени"
        // doesn't correspond to the output...
        //if had done as it is "Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени"

        //.distinct()
        //.sorted(comparing(Person::getId))
        //.collect(groupingBy(Person::getName, counting())) or .collect(toMap(Person::getName, v -> 1, Integer::sum)
        //it would have yielded :

        //Key: Amelia
        //Value:4
        //Key: Emily
        //Value:1
        //Key: Harry
        //Value:3
        //Key: Jack
        //Value:1
        // but this is only FICTION ... if we had added LinkedHashMap as mapFactory we would have seen that HashMap doesn't support order

//                .distinct()
//                .sorted(comparing(Person::getId))
//                .collect(toMap(Person::getName, v -> 1, Integer::sum,LinkedHashMap::new))
//                .forEach((name, amount) ->
//                        System.out.printf("Key: %s\nValue:%d\n", name, amount));
        // it gave us this output :

        //Key: Harry
        //Value:3
        //Key: Emily
        //Value:1
        //Key: Jack
        //Value:1
        //Key: Amelia
        //Value:4

        // to address the correct output we have to sort that stream as
        //              .sorted(comparing(Person::getName)
        //                     .thenComparing(Person::getId))

        // but it does not match this condition "отсортировать по идентификатору"

        // THE MOST INTERESTING ONE that "english version" of condition was written relatively correctly - "Duplicate filtered, grouped by name, sorted by name and id: "
        // and that correspond to the given output

        // all related intel here https://stackoverflow.com/questions/52192078/collectors-groupingby-returns-result-sorted-in-ascending-order-java/52192853#52192853

        // that's  why may Main solution as it is

        //That is main solution, please consider that as solution
        Arrays.stream(RAW_DATA)
                .distinct()
                .sorted(comparing(Person::getName)
                        .thenComparing(Person::getId))
                .collect(groupingBy(Person::getName, LinkedHashMap::new, mapping(Person::getId, toList())))
                .forEach((name, idList) ->
                        System.out.printf("Key: %s\nValue:%d\n", name, idList.size()));

        System.out.println(SEPARATOR);

        //second
        Arrays.stream(RAW_DATA)
                .distinct()
                .sorted(comparing(Person::getName)
                        .thenComparing(Person::getId))
                .collect(groupingBy(Person::getName, LinkedHashMap::new, counting()))
                .forEach((name, count) ->
                        System.out.printf("Key: %s\nValue:%d\n", name, count));

        System.out.println(SEPARATOR);

        //third
        Arrays.stream(RAW_DATA)
                .distinct()
                .sorted(comparing(Person::getName)
                        .thenComparing(Person::getId))
                .collect(toMap(Person::getName, v -> 1, Integer::sum, LinkedHashMap::new))
                .forEach((name, amount) ->
                        System.out.printf("Key: %s\nValue:%d\n", name, amount));

        System.out.println(SEPARATOR);

        /*
        Duplicate filtered, grouped by name, sorted by name and id:

        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */
        //solution for output above
        Arrays.stream(RAW_DATA)
                .distinct()
                .sorted(comparing(Person::getName)
                        .thenComparing(Person::getId))
                .collect(groupingBy(Person::getName, LinkedHashMap::new, mapping(Person::getId, toList())))
                .forEach((name, idList) ->
                {
                    System.out.println(name + ":");
                    AtomicInteger count = new AtomicInteger(0);
                    idList.forEach(id ->
                            System.out.printf("%d - %s (%d)\n", count.incrementAndGet(), name, id));

                });
    }

    // this class absolutely synonymous to "record Person(int id, String name) {}"
    // records also have under-hood's implementation "equals and hashCode"
    // if we replace this one with record, nothing would have changes except getters and setters
    // by the way I like that new style name(); id(); =3
    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };
}
