package y2022.day06;

import filehandlers.FileReader;

import java.util.LinkedList;
import java.util.List;

public class Day6 {

    FileReader reader = new FileReader();

    public static void main(String[] args) {
        Day6 day = new Day6();
//        for (int i = 1; i <= 5; i++) {
//            day.solvePuzzle("example" + i, 14);
//        }
        day.solvePuzzle("input", 4);
        day.solvePuzzle("input", 14);
    }

    public void solvePuzzle(String fileName, int targetLength) {
        var input = reader.read("src/y2022/day06/files/" + fileName).get(0);

        int result = findFirstNonRepeating(input, targetLength);
        System.out.println(result);
    }

    public int findFirstNonRepeating(String input, int targetLength) {
        List<Character> marker = new LinkedList<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (marker.contains(c)) {
                marker.subList(0, marker.indexOf(c) + 1).clear();
            }
            marker.add(c);
            if (marker.size() == targetLength) {
                return i + 1;
            }
        }
        return -1;
    }
}
