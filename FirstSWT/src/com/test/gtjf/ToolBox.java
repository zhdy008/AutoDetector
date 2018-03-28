package com.test.gtjf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToolBox {

	static List<String> labels = new ArrayList<String>();

	public static void genLabel() throws IOException {
		String encoding = "utf-8";
		File file = new File("【百度】财经词汇大全.txt");
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				if (lineTxt.length() < 4) {
					labels.add(lineTxt);
//					System.out.println(lineTxt);
				}
			}
			read.close();
		} else {
			System.out.println("找不到指定的文件");
		}
		// return null; //没有合适的返回项
	}

	public static String getLabel() throws IOException {
		if (labels.size() == 0)
			genLabel();
		// System.out.println("个数：" + labels.size());
		Random random = new Random();
		int index = Math.abs(random.nextInt()) % labels.size();
		return labels.get(index);
	}

	public static String getText() throws IOException {
		// 允许返回空字符串""
		if (labels.size() == 0)
			genLabel();
		// System.out.println("个数：" + labels.size());
		Random random = new Random();
		int index = Math.abs(random.nextInt()) % labels.size();
		if((Math.abs(random.nextInt()) % 5) > 1)
			return "";
		else
			return labels.get(index);
	}

	private static void test_getLabel() {
		for (int i = 0; i < 50; i++) {
			try {
				String str = getLabel();
				System.out.println(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		test_getLabel();
	}

}
