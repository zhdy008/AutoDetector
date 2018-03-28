package com.test.gtjf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Document; 
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;  
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;  

public class ShowPic extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowPic frame = new ShowPic();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShowPic() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1450, 800);
		contentPane = new ImagePanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
	}

	class ImagePanel extends JPanel {
        public void paint(Graphics g) {
			super.paint(g);
//			Image image = Toolkit.getDefaultToolkit().getImage("E:\\ML\\tmp\\000002.jpg");
			Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Think\\Downloads\\windows_v1.5.1\\000002.jpg");
			if(image.getWidth(contentPane)<0)
				return;
			
			System.out.println("-------------------------------");
			System.out.println("Pic-width = " + image.getWidth(contentPane));
			g.drawImage(image, 0, 0, image.getWidth(contentPane), image.getHeight(contentPane), this);
			
			// 绘制对象线框
			try {
				//File f = new File("E:\\ML\\tmp\\000001.xml");
				
				//找到Document
				SAXBuilder sbBuilder = new SAXBuilder();
//				Document doc = sbBuilder.build("E:\\ML\\tmp\\000002.xml");
				Document doc = sbBuilder.build("C:\\Users\\Think\\Downloads\\windows_v1.5.1\\000002.xml");
				//读取根元素
				Element stu = doc.getRootElement();
				//得到全部object子元素
				List<Element> list = stu.getChildren("object");
				for(int i=0;i<list.size();i++){
					Element obj = list.get(i);
					String name = obj.getChildText("name").trim();
//					String pose = obj.getChildText("pose");
					int xmin = Integer.parseInt(obj.getChild("bndbox").getChildText("xmin"));
					int ymin = Integer.parseInt(obj.getChild("bndbox").getChildText("ymin"));
					int xmax = Integer.parseInt(obj.getChild("bndbox").getChildText("xmax"));
					int ymax = Integer.parseInt(obj.getChild("bndbox").getChildText("ymax"));

					System.out.println("<name>" + name+"</name>");
//					System.out.println("<pose>" + pose+"</pose>");
					System.out.println("<bndbox><xmin>" + xmin+"</xmin></bndbox>");
					System.out.println("<bndbox><ymin>" + ymin+"</ymin></bndbox>");
					System.out.println("<bndbox><xmax>" + xmax+"</xmax></bndbox>");
					System.out.println("<bndbox><ymax>" + ymax+"</ymax></bndbox>");

					Color red = new Color(0xff, 0, 0);
					g.setColor(red);
					g.drawRect(xmin, ymin, xmax-xmin, ymax-ymin);
					g.drawString(name, xmin, ymin -6);
//					System.out.println("x = "+xmin);
//					System.out.println("y = "+ymin);
//					System.out.println("width = "+String.valueOf(xmax-xmin));
//					System.out.println("height = "+String.valueOf(ymax-ymin));
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
