package com.hu;
import	java.util.HashMap;

import java.util.Map;

public class LeetCode1 {

    public static int[] twoSum(int[] nums, int target){
        if(nums == null || nums.length < 2){
            return null;
        }
        int[] res = new int[]{-1,-1};
        Map<Integer,Integer> map = new HashMap<Integer, Integer> ();
        for (int i = 0; i < nums.length; i++) {
            if(map.containsKey(target-nums[i])){
                res[0] = map.get(target-nums[i]);
                res[1] = i;
            }
            map.put(nums[i],i);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {2,3,5,6,7,8};
        int target = 0;
        int[] ints = twoSum(nums, target);
        for (int i = 0; i < ints.length; i++) {
            System.out.println("i = " + ints[i]);
        }
    }
}
