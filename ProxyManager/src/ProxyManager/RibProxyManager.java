  
/**    
* 文件名：RibProxyManager.java    
*    
* 版本信息：    
* 日期：2017年1月24日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package ProxyManager;

import java.awt.BorderLayout;
import java.awt.Dimension;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import java.awt.event.WindowAdapter;


/**    
*     
* 项目名称：ProxyManager    
* 类名称：RibProxyManager    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年1月24日 上午1:21:51    
* 修改人：jinyu    
* 修改时间：2017年1月24日 上午1:21:51    
* 修改备注：    
* @version     
*     
*/
public class RibProxyManager extends JFrame {

	/**   
	* @Title: RibProxyManager.java 
	* @Package ProxyManager 
	* @Description: 启动
	* @author fsjohnhuang
	* @date 2017年1月25日 下午2:00:16 
	* @version V1.0   
	*/
	
	private static final long serialVersionUID = 1L;
	static {  
        try {  
         
            javax.swing.UIManager.setLookAndFeel(  
            		"org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
  //根据图片的地址获取该图片，返回ResizableIcon  
    public static ResizableIcon getResizableIconFromResource(String resource) {  

    try
    {
        return ImageWrapperResizableIcon.getIcon(  
        		RibProxyManager.class.getClassLoader().getResource(resource),   
                new Dimension(48, 48));  
    }
    catch(Exception ex)
    {
    	System.out.println(resource);
    	return null;
    }

    }  
    
	private JPanel contentPane;
    private JProxyManager panelManager;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true); //windows功能失效  
    	JDialog.setDefaultLookAndFeelDecorated(true); //Dialog功能失效
    	 SwingUtilities.invokeLater(new Runnable() {  
		//EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RibProxyManager frame = new RibProxyManager();
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
	public RibProxyManager() {
		setTitle("\u670D\u52A1\u4E2D\u5FC3\u7BA1\u7406\u5668");
		addWindowListener(new WindowAdapter() {
			
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		//
		panelManager=new JProxyManager();
		getContentPane().add(panelManager);
		
	}

}
