package com.nn.ishop.server.bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.nn.ishop.server.jdbc.PostgreSQLConnectionPool;
import com.nn.ishop.server.util.Formatter;

public class ClientDataWrapper {
//	private Company comp;
//	private ArrayList<ItemCat> cats = new ArrayList<ItemCat>();
//	private Hashtable<String, IssueBill> issues = new Hashtable<String, IssueBill>();
//	private Hashtable<String, StoreReceipt> receipts = new Hashtable<String,StoreReceipt>();
//	private java.sql.Connection dbConnection;
//
//	public static final String SELECT_CATEGORY
//		= "SELECT * FROM sic_retailer.category";
	private static ClientDataWrapper instance = null;

	public static ClientDataWrapper getInstance()
	{
		if(instance == null){
			instance = new ClientDataWrapper();
			return instance;
		}else
		{
			return instance;
		}
	}
	public static ClientDataWrapper getNewInstance()
	{
		instance = new ClientDataWrapper();
		return instance;
	}
//	public ItemCat getItemCat(String catId)
//	{
//		ArrayList<ItemCat> catsA = getCats();
//		for(ItemCat i:catsA)
//			if(i.getCatCode().equals(catId))return i;
//		return null;
//	}
//	public ItemWrapper[] getCategoriesWrapper(){
//		ArrayList<ItemCat> catsA = getCats();
//		ItemWrapper[] cbCats = new ItemWrapper[catsA.size()];
//		for(int i=0;catsA != null && i< catsA.size();i++)
//		{
//			ItemCat cat = (ItemCat)catsA.get(i);
//			cbCats[i] = new ItemWrapper(cat.getCatCode(),
//					cat.getCatName());
//		}
//		return cbCats;
//	}
//	private ClientDataWrapper() {
//		log.info("Start loading Client Data ");
//		this.comp = new Company();
//		loadCategoryInfor();
//	}
//
//	public void reloadAllData()
//	{
//		this.comp.loadCustomerInfor();
//		this.comp.loadWarehouseInfor();
//		this.comp.loadEmployeeInfor();
//		loadCategoryInfor();
//	}
//	public void loadCategoryInfor()
//	{
//		try {
//			this.cats = new ArrayList<ItemCat>();
//			if(dbConnection == null)dbConnection = PostgreSQLConnectionPool.getConnection();
//			PreparedStatement stmt
//				= dbConnection.prepareStatement(SELECT_CATEGORY);
//			stmt.execute();
//			ResultSet rs = stmt.getResultSet();
//			while(rs.next()){
//				ItemCat cat = new ItemCat(
//						rs.getString("id"),
//						rs.getString("name"),
//						rs.getString("parent_cat")
//						);
//				cat.loadItemsForCat(dbConnection);
//				this.cats.add(cat);
//			}
//			stmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public IssueBill getIssueById(String billId)
//	{
//		searchIssueInforById(billId, false);
//		for(IssueBill i:issues.values())
//			if(i.getBillCode().equals(billId))return i;
//		return null;
//	}
//
//	public void searchIssueInforById(String billId, boolean idExactly)
//	{
//		try {
//			if(this.issues == null)
//				this.issues = new Hashtable<String, IssueBill>();
//			if(dbConnection == null)
//				dbConnection = PostgreSQLConnectionPool.getConnection();
//
//			PreparedStatement stmt = null;
//			if(!idExactly)
//				stmt = dbConnection.prepareStatement("SELECT * FROM sic_retailer.item_order WHERE id like '%"+billId+"%'");
//			else
//				stmt = dbConnection.prepareStatement("SELECT * FROM sic_retailer.item_order WHERE id = '%"+billId+"%'");
//			stmt.execute();
//			ResultSet rs = stmt.getResultSet();
//			while(rs.next()){
//				IssueBill bill = new IssueBill(
//						rs.getString("id"),
//						rs.getString("lot_id"),
//						rs.getString("customer_id"),
//						rs.getString("issue_date"),
//						rs.getString("truck_number"),
//						rs.getString("order_type"),
//						rs.getString("warehouse_from"),
//						rs.getString("warehouse_to")
//						);
//				this.issues.remove(bill.getBillCode());
//				this.issues.put(bill.getBillCode(), bill);
//			}
//			stmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void searchIssueInforByCustomer(String customerName)
//	{
//		try {
//			if(this.issues == null)
//				this.issues = new Hashtable<String, IssueBill>();
//			if(dbConnection == null)
//				dbConnection = PostgreSQLConnectionPool.getConnection();
//			PreparedStatement stmt
//				= dbConnection.prepareStatement("SELECT t1.*, t2.name FROM sic_retailer.item_order as t1, sic_retailer.customer as t2" +
//						" WHERE t1.customer_id = t2.id " +
//						" and t2.name like '%"
//						+ customerName + "%'");
//			stmt.execute();
//			ResultSet rs = stmt.getResultSet();
//			while(rs.next()){
//				IssueBill bill = new IssueBill(
//						rs.getString("id"),
//						rs.getString("lot_id"),
//						rs.getString("customer_id"),
//						rs.getString("issue_date"),
//						rs.getString("truck_number"),
//						rs.getString("order_type"),
//						rs.getString("warehouse_from"),
//						rs.getString("warehouse_to")
//						);
//				this.issues.remove(bill.getBillCode());
//				this.issues.put(bill.getBillCode(), bill);
//			}
//			stmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void searchIssueInforByDate(String startDate, String endDate)
//	{
//		try {
//			if(this.issues == null)
//				this.issues = new Hashtable<String, IssueBill>();
//			if(dbConnection == null)
//				dbConnection = PostgreSQLConnectionPool.getConnection();
//			PreparedStatement stmt = null;
//			if(startDate != null && endDate != null)
//				stmt = dbConnection.prepareStatement("SELECT * FROM sic_retailer.item_order " +
//						" WHERE to_date(issue_date,'dd/MM/yyyy') >= to_date('"+startDate+"','dd/MM/yyyy') "
//						+" and to_date(issue_date,'dd/MM/yyyy') <= to_date('"+endDate+"','dd/MM/yyyy') ");
//			if(startDate == null && endDate != null)
//				stmt = dbConnection.prepareStatement("SELECT * FROM sic_retailer.item_order WHERE "
//						//" WHERE to_date(issue_date,'dd/MM/yyyy') >= to_date('"+startDate+"','dd/MM/yyyy') "
//						+" to_date(issue_date,'dd/MM/yyyy') <= to_date('"+endDate+"','dd/MM/yyyy') ");
//			if(startDate != null && endDate == null)
//				stmt = dbConnection.prepareStatement("SELECT * FROM sic_retailer.item_order " +
//						" WHERE to_date(issue_date,'dd/MM/yyyy') >= to_date('"+startDate+"','dd/MM/yyyy') ");
//						//+" and to_date(issue_date,'dd/MM/yyyy') <= to_date('"+endDate+"','dd/MM/yyyy') ");
//			if(startDate == null && endDate == null) return;
//
//			stmt.execute();
//			ResultSet rs = stmt.getResultSet();
//			while(rs.next()){
//				IssueBill bill = new IssueBill(
//						rs.getString("id"),
//						rs.getString("lot_id"),
//						rs.getString("customer_id"),
//						rs.getString("issue_date"),
//						rs.getString("truck_number"),
//						rs.getString("order_type"),
//						rs.getString("warehouse_from"),
//						rs.getString("warehouse_to")
//						);
//				this.issues.remove(bill.getBillCode());
//				this.issues.put(bill.getBillCode(), bill);
//			}
//			stmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void addBill(IssueBill bill)
//	{
//		this.issues.put(bill.getBillCode(), bill);
//	}
//	public StoreReceipt getReceipt(String recId)
//	{
//		loadReceiptsById(recId);
//		Hashtable<String,StoreReceipt> a = getReceiptsById(recId);
//		for(StoreReceipt i:a.values())
//			if(i.getReceiptCode().equals(recId))return i;
//		return null;
//	}
//
//	public void loadReceiptsById(String recId)
//	{
//		try {
//			this.receipts = new Hashtable<String,StoreReceipt>();
//			if(dbConnection == null)dbConnection = PostgreSQLConnectionPool.getConnection();
//			PreparedStatement stmt
//				= dbConnection.prepareStatement("SELECT * FROM sic_retailer.item_receipt WHERE id like '%"+recId+"%'");
//			stmt.execute();
//			ResultSet rs = stmt.getResultSet();
//			while(rs.next()){
//				StoreReceipt bill = new StoreReceipt(
//						rs.getString("id"),
//						rs.getString("lot_id"),
//						rs.getString("customer_id"),
//						rs.getString("input_date"),
//						rs.getString("description"),
//						rs.getString("po_type"),
//						rs.getString("warehouse_from"),
//						rs.getString("warehouse_to")
//						);
//				this.receipts.remove(bill.getReceiptCode());
//				this.receipts.put(bill.getReceiptCode(),bill);
//			}
//			stmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void loadReceiptsByCustomer(String customerIdOrName)
//	{
//		try {
//			this.receipts = new Hashtable<String,StoreReceipt>();
//			if(dbConnection == null)dbConnection = PostgreSQLConnectionPool.getConnection();
//			PreparedStatement stmt
//			= dbConnection.prepareStatement("SELECT t1.*, t2.name FROM sic_retailer.item_receipt as t1, sic_retailer.customer as t2" +
//					" WHERE t1.customer_id = t2.id " +
//					" and t2.name like '%"
//					+ customerIdOrName + "%'");
//			stmt.execute();
//			ResultSet rs = stmt.getResultSet();
//			while(rs.next()){
//				StoreReceipt bill = new StoreReceipt(
//						rs.getString("id"),
//						rs.getString("lot_id"),
//						rs.getString("customer_id"),
//						rs.getString("input_date"),
//						rs.getString("description"),
//						rs.getString("po_type"),
//						rs.getString("warehouse_from"),
//						rs.getString("warehouse_to")
//						);
//				this.receipts.remove(bill.getReceiptCode());
//				this.receipts.put(bill.getReceiptCode(),bill);
//			}
//			stmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void loadReceiptsByDate(String startDate, String endDate)
//	{
//		try {
//			if(this.issues == null)
//				this.issues = new Hashtable<String, IssueBill>();
//			if(dbConnection == null)
//				dbConnection = PostgreSQLConnectionPool.getConnection();
//			PreparedStatement stmt = null;
//			if(startDate != null && endDate != null)
//				 stmt = dbConnection.prepareStatement("SELECT * FROM sic_retailer.item_receipt " +
//						" WHERE to_date(input_date,'dd/MM/yyyy') >= to_date('"+startDate+"','dd/MM/yyyy') "
//						+" and to_date(input_date,'dd/MM/yyyy') <= to_date('"+endDate+"','dd/MM/yyyy') ");
//			if(startDate == null && endDate != null)
//				 stmt = dbConnection.prepareStatement("SELECT * FROM sic_retailer.item_receipt WHERE "
//						//+" to_date(input_date,'dd/MM/yyyy') >= to_date('"+startDate+"','dd/MM/yyyy') "
//						+" to_date(input_date,'dd/MM/yyyy') <= to_date('"+endDate+"','dd/MM/yyyy') ");
//			if(startDate != null && endDate == null)
//				 stmt = dbConnection.prepareStatement("SELECT * FROM sic_retailer.item_receipt " +
//						" WHERE to_date(input_date,'dd/MM/yyyy') >= to_date('" + startDate + "','dd/MM/yyyy')");
//			if(startDate == null && endDate == null)
//				return;
//
//
//			stmt.execute();
//			ResultSet rs = stmt.getResultSet();
//			while(rs.next()){
//				StoreReceipt bill = new StoreReceipt(
//						rs.getString("id"),
//						rs.getString("lot_id"),
//						rs.getString("customer_id"),
//						rs.getString("input_date"),
//						rs.getString("description"),
//						rs.getString("po_type"),
//						rs.getString("warehouse_from"),
//						rs.getString("warehouse_to")
//						);
//				this.receipts.remove(bill.getReceiptCode());
//				this.receipts.put(bill.getReceiptCode(),bill);
//			}
//			stmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public Company getComp() {
//		return comp;
//	}
//	public void setComp(Company comp) {
//		this.comp = comp;
//	}
//	public ArrayList<ItemCat> getCats() {
//		return cats;
//	}
//	public void setCats(ArrayList<ItemCat> cats) {
//		this.cats = cats;
//	}
//	public Hashtable<String, IssueBill> getIssuesById(String input) {
//			searchIssueInforById(input, false);
//		return issues;
//	}
//	public Hashtable<String, IssueBill> getIssuesByCustomer(String input) {
//		searchIssueInforByCustomer(input);
//		return issues;
//	}
//	public Hashtable<String, IssueBill> getIssuesByDate(String startDate, String endDate){
//		searchIssueInforByDate(startDate, endDate);
//		return issues;
//	}
//	public Hashtable<String,StoreReceipt> getReceiptsById(String likeId) {
//		loadReceiptsById(likeId);
//		return receipts;
//	}
//	public Hashtable<String,StoreReceipt> getReceiptsByCustomer(String likeName) {
//		loadReceiptsByCustomer(likeName);
//		return receipts;
//	}
//	public Hashtable<String,StoreReceipt> getReceiptsByDate(String startDate, String endDate) {
//		loadReceiptsByDate(startDate, endDate);
//		return receipts;
//	}
	//static Formatter fm = new Formatter();
	/** <ItemId,Stock> */
	private Hashtable<String,Double> htStock = new Hashtable<String, Double>();
	static Logger log = Logger.getLogger(ClientDataWrapper.class);

//	public Double getStock(String itemId)
//	{
//		calculateStock(itemId);
//		return htStock.get(itemId);
//	}

	public double enhancedCalculateStockForSale(String itemId, String fromWarehouse)
	{
		String stockSQL = "SELECT distinct warehouse, item_id, sum(item_quantity) as so_ton  " +
				"from sic_retailer.view_stock_by_item " +
				" WHERE item_id =? AND warehouse = ? "+
				"GROUP BY warehouse, item_id";
		double stock = 0.0d;
		try {
			Connection conn = PostgreSQLConnectionPool.getConnection();
			PreparedStatement stmt1 = conn.prepareStatement(stockSQL);
			stmt1.setString(1, itemId);
			stmt1.setString(2, fromWarehouse);
			stmt1.execute();
			ResultSet rs1 = stmt1.getResultSet();
			
			while (rs1.next())
			{
				String stockString = rs1.getString("so_ton");
				
				stockString =replaceSpecialChars(stockString);
				stock += Double.valueOf(stockString);
			}
			stmt1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stock;
	}
//	/**
//	 * @deprecated - used enhancedCalculateStockForSale instead of
//	 * @param itemId
//	 */
//	public void calculateStock(String itemId)
//	{
//		try {
//			double stock = 0.0, input = 0.0, issue = 0.0;
//			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//
//			String yesterDayString =
//	    		df.format(new Date(System.currentTimeMillis() - 24*60*60*1000));
//			String todayString = df.format(new Date(System.currentTimeMillis()));
//
//			String storeQuery = "select item_quantity from sic_retailer.item_receipt as t1, "
//					+ " sic_retailer.items_receipt_association as t2 where "
//					+ " t1.id = t2.receipt_id "
//					+ " and item_id =?"
//					+ " and to_date(t1.input_date,'dd/MM/yyyy') = to_date('"+todayString+"','dd/MM/yyyy')";
//
//			String issueQuery = "select item_quantity from sic_retailer.item_order as t1, sic_retailer.item_order_association as t2 where"
//					+ " t1.id = t2.order_id "
//					+ " and item_id = ?"
//					+ " and to_date(t1.issue_date,'dd/MM/yyyy') = to_date('"+todayString+"','dd/MM/yyyy')";
//
//			Connection conn = PostgreSQLConnectionPool.getConnection();
//			PreparedStatement stmt1 = conn.prepareStatement(storeQuery);
//			PreparedStatement stmt2 = conn.prepareStatement(issueQuery);
//
//			// Input
//			stmt1.setString(1, itemId);
//			stmt1.execute();
//			ResultSet rs1 = stmt1.getResultSet();
//			while (rs1.next())
//			{
//				String realQuantity = rs1.getString("item_quantity");
//				realQuantity =replaceSpecialChars(realQuantity);
//				input += Double.parseDouble(realQuantity);
//			}
//
//			// issue
//			stmt2.setString(1, itemId);
//			stmt2.execute();
//			ResultSet rs2 = stmt2.getResultSet();
//			while (rs2.next()){
//				String realQuantity = rs2.getString("item_quantity");
//				realQuantity =replaceSpecialChars(realQuantity);
//					realQuantity.replace((char)160, ' ');
//				issue += Double.parseDouble(realQuantity);
//			}
//	    	String lastStockQuery = "select stock from sic_retailer.daily_stock where to_date(cal_date,'dd/MM/yyyy') = to_date('" +yesterDayString
//			+"','dd/MM/yyyy') and item_code = '"+itemId+"'";
//	    	PreparedStatement stmt3 = conn.prepareStatement(lastStockQuery);
//	    	stmt3.execute();
//			ResultSet rs3 = stmt3.getResultSet();
//			double yesterdayStock = 0.0d;
//			while (rs3.next()){
//				String lastStock = rs3.getString("stock");
//				lastStock = replaceSpecialChars(lastStock);
//				yesterdayStock = Double.parseDouble(lastStock);
//			}
//			stock = input - issue + yesterdayStock;
//			htStock.remove(itemId);
//			htStock.put(itemId, Double.valueOf(stock));
//			stmt1.close();stmt2.close();stmt3.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	public static String replaceSpecialChars(String inputString){
		if(inputString == null || inputString.equals(""))
			return inputString;
		inputString = inputString.replace((char)160, ' ');
		inputString = inputString.replaceAll(" ", "");
		inputString = inputString.replaceAll(",", "");
		inputString = inputString.replaceAll(String.valueOf((char)160), "");
		return new String(inputString);
	}
}
