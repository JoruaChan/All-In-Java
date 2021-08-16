package cn.joruachan.study.sort;

/**
 * 选择排序<br>
 * 依次选出最小的放在有序的左边；
 *
 * @author JoruaChan
 */
public class SelectionSort {

    public static final int[] sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    // 如果比最小的还小
                    minIndex = j;
                }
            }

            // 交换
            if (minIndex != i) {
                int temp = array[i];

                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1343, 3234, 12121, 5212, 3533, 99, 1434, 100, 45, 12};

        for (int num : sort(array)) {
            System.out.println(num);
        }
    }

}
