package test.img;

import javax.swing.JFrame;

import com.nn.ishop.client.gui.components.ImageLabel;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.util.ImgUtil;

public class ImgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String fileName = "F:/Projects/iShop/iShopV1.1/tmp/1.png";
		byte[] ret = ImgUtil.getImageAsByteArray(fileName);		
		ImageLabel imgLabel = new ImageLabel();
		Util.setImageLabelIcon(imgLabel, ret);
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(imgLabel);
		f.pack();
		f.setVisible(true);

	}

}
