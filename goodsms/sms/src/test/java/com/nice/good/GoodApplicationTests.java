package com.nice.good;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodApplicationTests {

	@Test
	public void contextLoads() {

    }

    @Test
    public void getNum(){

	    String goodLink="https://item.taobao.com/item.htm?spm=a1z38n.10677092.0.0.11891debip0pXo&id=572548993429";
        String[] goodLinks = goodLink.split("id=");
        String id=null;
        if (goodLinks.length > 1) {
            String partLink = goodLinks[1];
            String[] parts = partLink.split("&");
            id = parts[0];
        }
        System.out.println(id);
    }

    public static void main(String args[]){

//        String goodLink="https://item.taobao.com/item.htm?spm=a1z38n.10677092.0.0.11891debip0pXo&id=572548993429";
//        String[] goodLinks = goodLink.split("id=");
//        String id=null;
//        if (goodLinks.length > 1) {
//            String partLink = goodLinks[1];
//            String[] parts = partLink.split("&");
//            id = parts[0];
//        }
//        System.out.println(id);

        String responseResult1="https:https://img.alicdn.com/imgextra/i2/2840082088/O1CN011RILRDNpk5j0uYU_!!2840082088.jpg_500x500.jpg";

        String replace = responseResult1.replace("https:https:", "https:");

        System.out.println(replace);

    }







}
