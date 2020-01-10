package com.hu;

public class leetCode4 {

    public static double MedianOfTwoSortArrays(int[] nums1, int[] nums2) {
        if (nums1.length < nums2.length) {
            MedianOfTwoSortArrays(nums2, nums2);
        }
        int len = nums1.length + nums2.length;
        int cut1 = 0;
        int cut2 = 0;
        int cutL = 0;
        int cutR = nums1.length;
        while (cut1 <= nums1.length) {
            cut1 = (cutR - cutL) / 2 + cutL;
            cut2 = len/2 - cut1;

        }
        return 0;
    }

    public static void main(String[] args) {
        int[] nums1 = {3, 5, 8, 9};
        int[] nums2 = {1, 2, 7, 10, 11, 12};
    }
}
