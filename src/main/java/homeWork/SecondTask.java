package homeWork;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class SecondTask {
    //        Task2
    //            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
    //         */

    //this the best solution for this task and my main
    static BiConsumer<Integer[], Long> loopsWay = ((longs, stopNumber) -> {
        if (stopNumber == null) throw new IllegalArgumentException();
        for (Integer l0 : longs) {
            for (Integer l1 : longs) {
                if (l1 == null) throw new NullPointerException();
                long pair = l0 + l1;
                if (pair == stopNumber) {
                    System.out.printf("[%d, %d]", l0, l1);
                    return;
                }
            }
        }
    });
    
    
    public static void main(String[] args) {

        loopsWay.accept(new Integer[]{3, 4, 2, 7}, 10L);

        //uncomment this code but
        //be careful this "big data" was executing on 8 physical therefore 16 virtual
        // if you have less, I strongly recommend you correct this bigData's line ".limit(100000)"

//        Integer[] array = bigData();
//        Long sum = addPair(array);
//
//        loopsWay.accept(array, sum);
//
//
//        printFirstPair(new Integer[]{3, 4, 2, 7}, 10L);
//        printFirstPair(array,sum);
    }

    public static void printFirstPair(Integer[] array, Long stopSum) {
        System.out.println("\n");
        // Dummy benchmark but for these porpoises it's more than enough
        long start0 = System.nanoTime();
        System.out.print("LoopWay result: ");
        loopsWay.accept(array, stopSum);
        long res0 = System.nanoTime() - start0;
        System.out.printf("\nTime taken by loopWay: %d", res0);

        System.out.println("\n");
        long start1 = System.nanoTime();
        System.out.print("streamWay result: ");
        helpMe.accept(array, stopSum);
        long res1 = System.nanoTime() - start1;
        System.out.println("\nTime taken by streamWay: "+ res1);

        long result = res1 / res0;
        System.out.printf("The cycle is faster in %d times",result);

    }

    //but if I had had "stream disease", I would have written something as that =).
    // I must have had to add it - https://youtu.be/vxikpWnnnCU?t=1679
    // I was trying to optimize it as good as I can
    //still it looks terrible....
    //
    // the most interesting, I thought since we have to use  .findFirst(); - (to support this pretty tough)
    // we won't get lots of gain of performance using ".parallel()", but I was wrong (on my machine it is relatively equals under heavy load)
    // however despite this fact, the cycle style will be more preferable because
    // we get the same performance on ONE core, and we can leave more resources to
    //other parts of owr application
    static BiConsumer<Integer[], Long> helpMe = ((longs, stopNumber) -> {
        Arrays.stream(longs)
                .parallel()
                .filter(Objects::nonNull)
                .takeWhile(firstPair -> {
                    Optional<Integer> opLong = Arrays.stream(longs)
                            .dropWhile(nestedLong -> {
                                long sum = firstPair + nestedLong;
                                return !(sum == stopNumber);
                            })
                            .findFirst();
                    opLong.ifPresent(secondPair -> {
                        System.out.printf("[%d, %d]", firstPair, secondPair);
                    });
                    return opLong.isEmpty();
                }).count();
    });


    public static Integer[] bigData() {
        RandomGeneratorFactory<RandomGenerator> factory = RandomGeneratorFactory.of("Random");
        RandomGenerator randomGenerator = factory.create();
        return randomGenerator.ints()
                .parallel()
                .limit(100000)
                .boxed()
                .toArray(Integer[]::new);
    }

    public static long addPair(Integer[] array) {
        int[] index = ThreadLocalRandom.current()
                .ints(0, array.length - 1)
                .limit(2)
                .toArray();
        return array[index[0]] + array[index[1]];
    }
}