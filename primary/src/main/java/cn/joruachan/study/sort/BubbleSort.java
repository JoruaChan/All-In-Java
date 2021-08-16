package cn.joruachan.study.sort;

/**
 * 冒泡排序<br>
 * 两两交换，将数大的到后面。右边保持有序
 *
 * @author JoruaChan
 */
public class BubbleSort {
    public static final int[] sort(int[] array) {
        // 记录最后的已经排好序的个数
        int sortedCount = 0;

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 1; j < array.length - sortedCount; j++) {
                if (array[j] < array[j - 1]) {
                    // 如果前面比后面的大，则交换位置
                    int temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
            }

            ++sortedCount;
        }

        return array;
    }

    /**
     * 更好的编程
     *
     * @param array
     * @return
     */
    public static final int[] betterSort(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1343, 3234, 100, 12121, 99, 5212, 3533, 99, 1434, 100, 45, 12};

        for (int num : betterSort(array)) {
            System.out.println(num);
        }
    }
}
