package com.test.gtjf;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Insets;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JFhelloworld {
	private static int 图片张数 = 5000;
	private static String baseJPEGDir = "E:\\AAA\\JPEGImages\\";
	private static String baseAnnotationsDir = "E:\\AAA\\Annotations\\";
	private static int seNo = 1;

	// 保存图片的全目录文件名
	public static String 生成截图文件名(){
		File dir =new File(baseJPEGDir); 
		if  (!dir.exists())      
		{       
		    dir.mkdir();    
		}
		String filename = String.format("%06d.jpg", seNo);
		System.out.println("截图文件名:"+filename);
		
		return filename;
	}

	public static String 生成标注文件名(){
		File dir =new File(baseAnnotationsDir); 
		if  (!dir.exists())      
		{       
		    dir.mkdir();    
		}
		String filename = String.format("%06d.xml", seNo);
		System.out.println("标注文件名:"+filename);
		
		seNo++; // 序号加一
		
		return filename;
	}

	public static void main(String[] args) {
        //新建一个JFrame对象frame，同时其标题栏为No Title  
		JFrame frame=new JFrame("No Title");  
        //新建一个JLabel组件label，里面的内容为Hello world!  
        JLabel label=new JLabel("Hello world!");  
        //新建一个JPanel面板panel，上面用来摆东西  
        JPanel panel=new JPanel();  
        //在panel上面摆上label  
        panel.add(label);  
        //设置panel的布局为任意null布局，这样下面的setBounds语句才能生效，并且label在这个面板的(125,75)位置，且大小为100x20px  
        panel.setLayout(null);  
        label.setBounds(125,75,100,20);  
        //在frame中添加panel  
        frame.getContentPane().add(panel);  
        //设置frame的大小为300x200，且可见默认是不可见的  
        frame.setSize(300,200);  
        frame.setLocation(0,0);
        frame.setVisible(true);  
        //使右上角的关闭按钮生效，如果没有这句，点击右上角的关闭按钮只能关闭窗口，无法结束进程  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  

        // 保存图片的目录
		String dirName = "E:" + File.separator + "AAA" + File.separator;
		File dir =new File(dirName);
		if(!dir.exists()){
			dir.mkdir();
		}

        System.out.println("开始！");
        for (int i = 0; i < 图片张数; i++) {
        	// 显示一个动态生成的Frame
    		EventQueue.invokeLater(new Runnable() {
    			public void run() {
    				try {
    					FirstFrame frame = new FirstFrame();
    					frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    					frame.setVisible(true);
    					
    					// 用异步定时器的方法，防止阻塞
    					Thread timer = new Thread(new Runnable(){
    						@Override
    						public void run() {
    							try {
    					            Thread.sleep(2000);
    					        } catch (InterruptedException e) {
    					            e.printStackTrace(); 
    					        }
    							System.out.println("销毁！");
    							FirstFrame.captureScreen( baseJPEGDir, 生成截图文件名(), baseAnnotationsDir, 生成标注文件名(),frame);
    							frame.dispose();
    						}
    					}); 
    					timer.start();
    					
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		});

    		// 等候一段时间，再启动下一幅界面
			try {
	            Thread.sleep(3000);
	        } catch (InterruptedException e) {
	            e.printStackTrace(); 
	        }

        }

	}

}
