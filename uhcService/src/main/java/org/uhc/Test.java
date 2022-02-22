package org.uhc;

import java.io.File;

public class Test {

	public static void main(String[] args) {
		
		Test12 h=new Test12();
		System.out.print(h.getFile());
	}

}

 class Test12 {

	public File getFile() {
		ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(".").getFile());
        return file;

	}

}
