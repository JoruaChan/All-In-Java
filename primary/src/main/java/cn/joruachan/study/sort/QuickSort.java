package cn.joruachan.study.sort;

/**
 * 快速排序<br>
 * 利用标准值，保证标准值左边比标准值小，右边比标准值大, partition+quicksort分治排序
 *
 * @author JoruaChan
 */
public class QuickSort {

    public static final int[] sort(int[] array) {
        quickSort(array, 0, array.length - 1);
        return array;
    }

    public static final void quickSort(int[] array, int left, int right) {
        if (left >= right) return;

        // 将数组分成两个部分
        int pivot = partition(array, left, right);

        // 递归排序左序列
        quickSort(array, left, pivot - 1);
        // 递归排序右序列
        quickSort(array, pivot + 1, right);
    }

    /**
     * 找到标准值的位置，从后往前找到比标准值小的，从前往后找比标准值大的
     *
     * @return
     */
    public static final int partition(int[] array, int left, int right) {
        int pivot = array[left];
        while (left < right) {
            // 先从后往前找到比基准值小的，然后交换下
            while (left != right && array[right] >= pivot) {
                --right;
            }
            array[left] = array[right];

            // 再从前往后找比基准值大的，然后交换下
            while (left != right && array[left] <= pivot) {
                left++;
            }
            array[right] = array[left];
        }
        array[left] = pivot;
        return left;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1343, 3234, 100, 12121, 99, 5212, 3533, 99, 1434, 100, 45, 12};

        for (int num : sort(array)) {
            System.out.println(num);
        }
    }
}