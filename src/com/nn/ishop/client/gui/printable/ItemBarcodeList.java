package com.nn.ishop.client.gui.printable;

import com.nn.ishop.client.gui.components.CWhitePanel;

public class ItemBarcodeList extends CWhitePanel {

//	protected ArrayList<Item> items = new ArrayList<Item>();
//	private static final long serialVersionUID = -8705033354733687328L;
//	static Logger log = Logger.getLogger(ItemBarcodeList.class);
//	
//	private JBarcodeBean getBarCode(String code)
//	{
//		JBarcodeBean barCode = new JBarcodeBean();
//		barCode.setCodeType(new Code128());
//		barCode.setCode(code);
//		barCode.setShowText(true);
//		barCode.setBarcodeHeight(10);		
//		barCode.setFont(new Font("Arial", Font.PLAIN,6));
//		barCode.setBackground(Color.WHITE);
//		barCode.setBorder(BorderFactory.createTitledBorder(""));		
//		
//		return barCode;
//	}
//	public ItemBarcodeList(ArrayList<Item> items){
//		setLayout(new BorderLayout());
//		
//		CWhitePanel tempPnl = null;
//		CWhitePanel mainPnl = new CWhitePanel(new GridBagLayout());
//		
//		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0f, 1.0f,
//				GridBagConstraints.NORTHWEST,
//				GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);		
//		try {
//			if(items == null || items.size() == 0)return;
//			int rowNum = 0;		
//			Item[] itemArr = new Item[items.size()];
//			itemArr = items.toArray(itemArr);
////			int arrLength = itemArr.length;
//			int len = itemArr.length;
//			int loopCount = 1;
//			loopCount =(78 > len)?
//						Math.round(78/len)
//					:1;
//			int mod = 0;
////			if(62 > len){
////				mod = 62 % len;
////			}
//			log.info("Loop count: " +loopCount);			
//			for (int n =0;n<loopCount;n++){
//				for(int i=0; i<len;i+=5){
//					gbc.gridy = rowNum;				
//					for(int k=0;k<5;k++)
//					{
//						if(i+1 == len && k > 0)break;
//						gbc.gridx = k;
//						tempPnl = new CWhitePanel(new VerticalFlowLayout());
//						JLabel l = new JLabel(itemArr[i+k].getItemName());
//						l.setFont(new Font("Arial",Font.PLAIN,6));
//						tempPnl.add(l);
//						tempPnl.add(getBarCode(itemArr[i+k].getItemCode()));
//						mainPnl.add(tempPnl, gbc);
//					}
//					rowNum++;
//				}
//			}
//			CWhitePanel mainPnlParent = new CWhitePanel(new BorderLayout());
//			mainPnlParent.add(mainPnl, BorderLayout.WEST);			
//			CWhitePanel main0 = new CWhitePanel(new BorderLayout());
//			main0.add(mainPnlParent, BorderLayout.NORTH);
//			add(main0, BorderLayout.NORTH);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e.getMessage());			
//		}				
//	}
//
//	/*
//	 * This constructor create a page of the same barcode for one item
//	 */
//	public ItemBarcodeList(String barCode)
//	{
//		setLayout(new BorderLayout());	
//		CWhitePanel tempPnl = null;
//		CWhitePanel mainPnl = new CWhitePanel(new GridBagLayout());
//		
//		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0f, 1.0f,
//				GridBagConstraints.NORTHWEST,
//				GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);		
//		try {
//			//13 row/page
//			Item item = Item.getItem(barCode);
//			int rowNum = 0;		
//			int arrLength = 60;
//			for(int i=0; i<arrLength;i+=5){
//				gbc.gridy = rowNum;
//				int limit = 5;
////				if(item.getItemCode().length()<=2)limit =4;
////				if(item.getItemCode().length()>2 
////						&& item.getItemCode().length()<4)limit =4;
////				if(item.getItemCode().length()>4 
////						&& item.getItemCode().length()<5)limit =3;
////				if(item.getItemCode().length()>5)limit =3;
////				if(item.getItemCode().length()>9)limit =2;
//				
//				for(int k=0;k<limit;k++)
//				{
//					if(i+1==arrLength && k > 0)break;
//					gbc.gridx = k;
//					tempPnl = new CWhitePanel(new VerticalFlowLayout());
//					JLabel l = new JLabel(item.getItemName());
//					l.setFont(new Font("Arial",Font.PLAIN,8));
//					tempPnl.add(l);
//					tempPnl.add(getBarCode(item.getItemCode()));
//					mainPnl.add(tempPnl, gbc);
//				}
//				rowNum++;
//			}
//			CWhitePanel mainPnlParent = new CWhitePanel(new BorderLayout());
//			mainPnlParent.add(mainPnl, BorderLayout.WEST);			
//			add(mainPnlParent, BorderLayout.NORTH);			
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			e.printStackTrace();
//		}			
//	}
//	public ItemBarcodeList(){
//		setLayout(new BorderLayout());	
//		CWhitePanel tempPnl = null;
//		CWhitePanel mainPnl = new CWhitePanel(new GridBagLayout());
//		
//		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0f, 1.0f,
//				GridBagConstraints.NORTHWEST,
//				GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);		
//		try {
//			ArrayList<Item> items = ItemCat.loadAllItems();
//			if(items == null || items.size() == 0)return;
//			int rowNum = 0;		
//			Item[] itemArr = new Item[items.size()];
//			itemArr = items.toArray(itemArr);
//			
//			int arrLength = itemArr.length;
//			for(int i=0; i<arrLength;i+=3){
//				gbc.gridy = rowNum;
//				for(int k=0;k<3;k++)
//				{
//					if(i+1==arrLength && k > 0)break;
//					gbc.gridx = k;
//					tempPnl = new CWhitePanel(new VerticalFlowLayout());
//					JLabel l = new JLabel(itemArr[i+k].getItemName());
//					l.setFont(new Font("Arial",Font.PLAIN,8));
//					tempPnl.add(l);
//					tempPnl.add(getBarCode(itemArr[i+k].getItemCode()));
//					mainPnl.add(tempPnl, gbc);
//				}
//				rowNum++;
//			}
//			CWhitePanel mainPnlParent = new CWhitePanel(new BorderLayout());
//			mainPnlParent.add(mainPnl, BorderLayout.WEST);			
//			add(mainPnlParent, BorderLayout.NORTH);			
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			e.printStackTrace();
//		}				
//	}
	

}
