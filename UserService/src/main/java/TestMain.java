import com.example.userservice.dao.entity.enums.ERole;

import java.util.Arrays;
import java.util.Scanner;

public class TestMain {//(D-B) * C + A//A B C D
//    public static void main(String[] args) {TASK1
//        Scanner scan = new Scanner(System.in);
//        int[] arr = Arrays.stream(scan.nextLine()
//                        .trim()
//                        .split(" "))
//                .filter(x -> !x.equals(""))
//                .mapToInt(Integer::parseInt)
//                .toArray();
//
//        if((arr[3] - arr[1]) < 0 ){
//            System.out.println(arr[0]);
//            return;
//        }
//
//        System.out.println((arr[3] - arr[1]) * arr[2] + arr[0]);
//    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int countPeople = scan.nextInt();

        int countCuts = 0;

        countCuts = countPeople / 2;

        if(countPeople % 2 > 0){
            countCuts += countPeople % 2;
        }

        System.out.println(countCuts);
    }

}
