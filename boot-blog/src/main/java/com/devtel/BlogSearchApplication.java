package com.devtel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

@SpringBootApplication
public class BlogSearchApplication {

	public static void main(String[] args) {
		System.setProperty("file.encoding","UTF-8");
		try{
			Field charset = Charset.class.getDeclaredField("defaultCharset");
			charset.setAccessible(true);
			charset.set(null,null);
		}
		catch(Exception e){
		}
		SpringApplication.run(BlogSearchApplication.class, args);
	}

}
