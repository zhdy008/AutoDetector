package com.test.gtjf;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.awt.Window.Type;
import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;
import javax.swing.JSlider;
import javax.swing.JTree;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;

public class FirstFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Random random = new Random();
	private JPanel contentPane;
	private JTextField textField_姓名;

	public static void captureScreen(String baseJPEGDir, String picFileName, String baseAnnotationsDir, String annotationFilename, JFrame frame) {
//		System.out.println("picFileName:" + picFileName);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
//		 Rectangle screenRectangle = new Rectangle(screenSize);
		Rectangle screenRectangle = new Rectangle(frameSize);
		try {
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(screenRectangle);
			// 绘制线框
			Graphics graphics = image.getGraphics();
			Color red = new Color(0xff, 0, 0);
			Color green = new Color( 0x00, 0xff, 0x00);
			Color blue = new Color( 0x00, 0x00, 0xff);

			//SAXBuilder sbBuilder = new SAXBuilder();
			Document document = null;
			//定义根节点
	        Element annotation = new Element("annotation");
	        //声明一个Document对象
	        document = new Document(annotation);
	        // 增加 folder 节点
	        Element folder = new Element("folder");
	        folder.setText("AAA");
	        annotation.addContent(folder);
	        // 增加 filename 节点
	        Element filename = new Element("filename");
	        filename.setText(picFileName);
	        annotation.addContent(filename);
	        // 增加 size 节点
	        Element size = new Element("size");
	        Element width = new Element("width");
	        width.setText("" + frameSize.width);
	        Element height = new Element("height");
	        height.setText("" + frameSize.height);
	        Element depth = new Element("depth");
	        depth.setText("3");
	        size.addContent(width);
	        size.addContent(height);
	        size.addContent(depth);
	        annotation.addContent(size);

			Component[] components = frame.getContentPane().getComponents();
			for(int i = 0; i< components.length;i++){
				String className = null;
				// 由于标题&边框引起的偏移量，要细调
				if(components[i] instanceof JButton){
					graphics.setColor(red);
					className = "Button";
				} else if (components[i] instanceof JLabel) {
					graphics.setColor(green);
					className = "Label";
				} else if (components[i] instanceof JTextField) {
					graphics.setColor(green);
					className = "TextField";
				} else if (components[i] instanceof JComboBox) {
					graphics.setColor(green);
					className = "ComboBox";
				} else if (components[i] instanceof JCheckBox) {
					graphics.setColor(green);
					className = "CheckBox";
				} else if (components[i] instanceof JList) {
					graphics.setColor(green);
					className = "List";
				} else {
					graphics.setColor(blue);
				}
				// 绘制线框
				if(className == null){
					graphics.drawRect(components[i].getX()-4, components[i].getY()+28-4, components[i].getWidth()+8, components[i].getHeight()+9);
				}
				
				// 添加标注 object 节点
		        Element object = new Element("object");
		        
		        // name
		        Element name = new Element("name");
		        name.setText(className);
		        object.addContent(name);
		        
		        // bndbox
		        Element bndbox = new Element("bndbox");
		        
		        Element xmin = new Element("xmin");
		        xmin.setText(String.valueOf(components[i].getX()-4));
		        bndbox.addContent(xmin);
		        
		        Element ymin = new Element("ymin");
		        ymin.setText(String.valueOf(components[i].getY()+28-4));
		        bndbox.addContent(ymin);
		        
		        Element xmax = new Element("xmax");
		        xmax.setText(String.valueOf(components[i].getX()-4 + components[i].getWidth()+8));
		        bndbox.addContent(xmax);

		        Element ymax = new Element("ymax");
		        System.out.println("组件Y:"+ components[i].getY());
		        System.out.println("组件高度:"+ components[i].getHeight());
		        ymax.setText(String.valueOf(components[i].getY()+28-4 + components[i].getHeight()+9));
		        bndbox.addContent(ymax);

		        object.addContent(bndbox);

		        annotation.addContent(object);

			}
			// 保存图片
			ImageIO.write(image, "jpg", new File(baseJPEGDir + picFileName));

			// 保存标注
	        XMLOutputter out = new XMLOutputter(); //用来输出XML文件
	        Format f = Format.getPrettyFormat();  
	        out.setFormat(f);
	        out.setFormat(out.getFormat().setEncoding("utf-8")); //设置输出编码
	        //输出XML文件
            out.output(document, new FileOutputStream(baseAnnotationsDir + annotationFilename));
		} catch (AWTException | IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public FirstFrame(String title) throws HeadlessException {
		super(title);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstFrame frame = new FirstFrame();
					Insets insets = frame.getInsets();
					frame.setLocation(0, 0);
					frame.setExtendedState(Frame.MAXIMIZED_BOTH);
					frame.setVisible(true);
					frame.setTitle(
							"测试 - top=" + insets.top + 
							", left = " + insets.left + 
							", bottom = " + insets.bottom);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 返回一个 0 ~ N-1 的整数.
	 */
	private int RandomN(int N) {
		int index = Math.abs(random.nextInt()) % N;
		return index;
	}

	private void genComponent(int x, int y) {
		int index = RandomN(5); // 一共 5种控件
		try {
			switch (index) {
			case 0:
				// JLabel
				JLabel label = new JLabel(ToolBox.getLabel());
				label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				label.setBounds(x, y, 50+RandomN(10), 24);
				contentPane.add(label);

				// TextField 输入框
				JTextField textField = new JTextField();
				textField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				textField.setText(ToolBox.getText());
				textField.setBounds(x + 70, y, 120+RandomN(40), 24);
				contentPane.add(textField);
				break;
			case 1:
				JLabel label_combo = new JLabel(ToolBox.getLabel());
				label_combo.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				label_combo.setBounds(x, y, 45+RandomN(10), 24);
				contentPane.add(label_combo);

				// 下拉框
				JComboBox comboBox = new JComboBox();
				comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				comboBox.setModel(new DefaultComboBoxModel(new String[] { ToolBox.getLabel(), ToolBox.getLabel() }));
				comboBox.setBounds(x + 70, y, 120, 24);
				contentPane.add(comboBox);
				break;
			case 2:
				JLabel label_checkbox = new JLabel(ToolBox.getLabel());
				label_checkbox.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				label_checkbox.setBounds(x, y, 45+RandomN(10), 24);
				contentPane.add(label_checkbox);

				// 检查框
				JCheckBox checkBox = new JCheckBox(ToolBox.getLabel());
				checkBox.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				if(RandomN(5)>2)
					checkBox.setSelected(true);
				else
					checkBox.setSelected(false);
				checkBox.setBounds(x + 70, y, 120+RandomN(15), 24);
				contentPane.add(checkBox);
				break;
			case 3:
				JLabel label_list = new JLabel(ToolBox.getLabel());
				label_list.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				label_list.setBounds(x, y, 45+RandomN(10), 24);
				contentPane.add(label_list);

				// 列表
				JList list = new JList();
				list.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				list.setModel(new AbstractListModel() {
					String[] values = new String[] {ToolBox.getLabel(), ToolBox.getLabel(), ToolBox.getLabel()};
					public int getSize() {
						return values.length;
					}
					public Object getElementAt(int index) {
						return values[index];
					}
				});
				list.setSelectedIndex(RandomN(3));
				list.setBounds(x + 70, y, 220+RandomN(20), 70);
				contentPane.add(list);
				break;
			case 4:
				JButton button = new JButton(ToolBox.getLabel());
				button.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				button.setBounds(x, y, 100+RandomN(40), 27);
				contentPane.add(button);

			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public FirstFrame() {
		setAlwaysOnTop(true);
		JFrame frame = this;
		// 使右上角的关闭按钮生效，如果没有这句，点击右上角的关闭按钮只能关闭窗口，无法结束进程
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 594, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane); // 重要设定
		contentPane.setLayout(null);

		for (int i = 0; i < 8; i++) {
			int startX = 40;
			int startY = i* 80 + 26;
			genComponent(startX, startY);
			
			startX = 400;
			startY = i* 80 + 26;
			genComponent(startX, startY);

			startX = 800;
			startY = i* 80 + 26;
			genComponent(startX, startY);
		}
//		JLabel label = new JLabel("姓名：");
//		label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
//		label.setBounds(40, 26, 45, 18);
//		contentPane.add(label);
//
//		textField_姓名 = new JTextField();
//		textField_姓名.setText("张三");
//		textField_姓名.setBounds(99, 24, 121, 24);
//		contentPane.add(textField_姓名);
//		textField_姓名.setColumns(10);

//		JLabel label_性别 = new JLabel("性别：");
//		label_性别.setFont(new Font("微软雅黑", Font.PLAIN, 15));
//		label_性别.setBounds(270, 31, 45, 18);
//		contentPane.add(label_性别);
//
//		JComboBox comboBox_性别 = new JComboBox();
//		comboBox_性别.setModel(new DefaultComboBoxModel(new String[] { "男", "女" }));
//		comboBox_性别.setBounds(356, 28, 107, 24);
//		contentPane.add(comboBox_性别);

//		JButton button_点我截屏 = new JButton("点我截屏");
//		button_点我截屏.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				captureScreen("E:" + File.separator + System.currentTimeMillis() + ".png", frame);
//			}
//		});
//		button_点我截屏.setFont(new Font("微软雅黑", Font.PLAIN, 15));
//		button_点我截屏.setBounds(449, 387, 113, 27);
//		contentPane.add(button_点我截屏);
//
//		JButton button_确认 = new JButton("确认");
//		button_确认.setFont(new Font("微软雅黑", Font.PLAIN, 15));
//		button_确认.setBounds(142, 340, 113, 27);
//		contentPane.add(button_确认);
//
//		JButton button_取消 = new JButton("取消");
//		button_取消.setFont(new Font("微软雅黑", Font.PLAIN, 15));
//		button_取消.setBounds(327, 340, 113, 27);
//		contentPane.add(button_取消);
		
//		JCheckBox checkBox_已婚 = new JCheckBox("已婚");
//		checkBox_已婚.setSelected(true);
//		checkBox_已婚.setBounds(95, 86, 133, 27);
//		contentPane.add(checkBox_已婚);
		
//		JList list = new JList();
//		list.setModel(new AbstractListModel() {
//			String[] values = new String[] {"上海", "北京", "广州", "深圳"};
//			public int getSize() {
//				return values.length;
//			}
//			public Object getElementAt(int index) {
//				return values[index];
//			}
//		});
//		list.setSelectedIndex(1);
//		list.setBounds(270, 130, 262, 142);
//		contentPane.add(list);
	}
}
