package test;

import java.io.File;
import java.net.URL;

public class ClassLoaderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		URL url = classLoader.getResource("cfg/application_en.properties");
//		File file = new File(url.toURI());
		System.out.println(System.getProperty("user.dir")+"/cfg/application_en.properties");

	}

}
