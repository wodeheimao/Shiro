package com.zelin.test;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

public class Md5Test {

    @Test
    public void md5Test(){
        Md5Hash md5Hash = new Md5Hash("123","rbtwy",1);
        System.out.println("md5Hash = " + md5Hash.toString());//ec1b86316c81b3f3440c07f65a74bf79

        SimpleHash simpleHash = new SimpleHash("md5","123","rbtwy",1);
        System.out.println("simpleHash = " + simpleHash.toString());
    }
}
