package com.ligen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CaipiaoApplicationTests {

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("0000");
		String format = df.format(1);
		System.out.println(format);
	}
}
