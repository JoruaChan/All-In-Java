package cn.joruachan.study.sort;

/**
 * 文件概要说明<br>
 * 详细描述、功能使用方法
 *
 * @author 19037893
 * @since [版本号]
 */
public class QSort {

    // partition、quicksort

    /**
     * 确定基准值的位置
     *
     * @param nums
     * @param low
     * @param high
     * @return
     */
    public static int partition(int[] nums, int low, int high) {
        int pivot = nums[low];

        // 要一直循环到下标重叠
        while (low < high) {
            // 从后往前找到比基准值小的，交换位置
            while (low != high && nums[high] >= pivot) high--;
            nums[low] = nums[high];

            // 从前往后找到比基准值大的，交换位置
            while (low != high && nums[low] <= pivot) low++;
            nums[high] = nums[low];
        }
        nums[low] = pivot;
        return low;
    }

    public static void qSort(int[] nums, int low, int high) {
        if (low >= high) return;

        int partition = partition(nums, low, high);
        qSort(nums, low, partition - 1);
        qSort(nums, partition + 1, high);
    }

    public static void main(String[] args) {
        int[] array = new int[]{1343, 3234, 100, 12121, 99, 5212, 3533, 99, 1434, 100, 45, 12};
        qSort(array, 0, array.length - 1);

        for (int i : array) {
            System.out.println(i);
        }
    }

}
