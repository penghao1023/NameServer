  
/**    
* �ļ�����JJYTableCell.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��2��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package JJYComponent;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**    
*     
* ��Ŀ���ƣ�ProxyManager    
* �����ƣ�JJYTableCell    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��2�� ����4:31:57    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��2�� ����4:31:57    
* �޸ı�ע��    
* @version     
*     
*/
public class JJYTableCell implements TableCellRenderer {

	/*
	* Title: getTableCellRendererComponent
	*Description: 
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @param arg4
	* @param arg5
	* @return 
	 
	*/
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelect, boolean hasFocus, int row,
			int column) {
//		Boolean b=(Boolean)value;
		JCheckBox ck=new JCheckBox();
		ck.setSelected(isSelect);
		ck.setHorizontalAlignment((int) 0.5f);
		return ck;
	}

}