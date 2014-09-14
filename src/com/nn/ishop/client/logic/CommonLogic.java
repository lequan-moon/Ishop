package com.nn.ishop.client.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.Main;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CList;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.client.util.CollectionSorter;
import com.nn.ishop.client.util.Identifiable;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.AbstractIshopEntity;
import com.nn.ishop.server.dto.bank.Bank;
import com.nn.ishop.server.dto.bstranx.TransactionType;
import com.nn.ishop.server.dto.customer.CustomerType;
import com.nn.ishop.server.dto.exrate.Currency;
import com.nn.ishop.server.dto.exrate.ExRate;
import com.nn.ishop.server.dto.geographic.Location;
import com.nn.ishop.server.dto.grn.GrnHistoryBean;
import com.nn.ishop.server.dto.pricelist.PriceList;
import com.nn.ishop.server.dto.pricelist.PriceListType;
import com.nn.ishop.server.dto.product.ProductCategory;
import com.nn.ishop.server.dto.product.ProductUOM;
import com.nn.ishop.server.dto.sale.SaleOrderType;
import com.nn.ishop.server.dto.warehouse.Lot;
import com.nn.ishop.server.dto.warehouse.Warehouse;

public class CommonLogic {
	/**
	 * Each entity must extend <code>AbstractIshopEntity</code> and should have insert user id, update user id 
	 * , time to insert and update,... these are abstract features so this method set that common features.
	 * @param ent
	 * @param isInsert
	 */
	public static void setExtraEntityInfor(AbstractIshopEntity ent,CAction action){
		if(action.equals(CAction.ADD)){
			ent.setIns_time(SystemConfiguration.sdf.format((new GregorianCalendar()).getTime()));
			ent.setIns_user_id((Main.loggedInUser!= null)?Main.loggedInUser.getEm().getId():1);
			ent.setUpd_user_id(-1);
			ent.setUsage_flg(1);	
		}else if(action.equals(CAction.UPDATE)){
			ent.setUpd_time(SystemConfiguration.sdf.format((new GregorianCalendar()).getTime()));
			ent.setUpd_user_id((Main.loggedInUser!= null)?Main.loggedInUser.getEm().getId():1);			
			ent.setUsage_flg(1);			
		}else if(action.equals(CAction.DELETE)){
			ent.setUpd_time(SystemConfiguration.sdf.format((new GregorianCalendar()).getTime()));
			ent.setUpd_user_id((Main.loggedInUser!= null)?Main.loggedInUser.getEm().getId():1);			
			ent.setUsage_flg(-1);
		}
		
	}
	public static void updateCategory(List<?> list, CComboBox combo){
		try {
			Identifiable[] objs = new Identifiable[list.size()+1];
			objs[0] = new ItemWrapper("-1","  ");
			
			for (int i=0;i< list.size();i++) {
				Object obj = list.get(i);
				if(obj instanceof ProductCategory){
					ProductCategory pcg = (ProductCategory)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(pcg.getCategoryId()),
							pcg.getCategoryNote());				
				}else if(obj instanceof ProductUOM){
					ProductUOM uom = (ProductUOM)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(uom.getUomId()),
							uom.getUomDesc());	
				}					
			}			
			combo.setItems(objs);
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	public static void updateComboBox(List<?> list, CComboBox combo){
		try {
			Identifiable[] objs = new Identifiable[list.size()+1];
			//objs[0] = new ItemWrapper("-1","  ");
			
			for (int i=0;i< list.size();i++) {
				Object obj = list.get(i);
				if(obj instanceof ProductCategory){
					ProductCategory pcg = (ProductCategory)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(pcg.getCategoryId()),
							pcg.getCategoryNote());				
				}else if(obj instanceof ProductUOM){
					ProductUOM uom = (ProductUOM)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(uom.getUomId()),
							uom.getUomDesc());	
				}else if (obj instanceof Bank){
					Bank bank = (Bank)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(bank.getId()),
							bank.getDescriprion());
				}else if (obj instanceof Currency){
					Currency currency = (Currency)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(currency.getId()),
							currency.getDescriprion());
				}else if (obj instanceof Location){
					Location loc = (Location)obj;
					if(loc.getParent_id() == 0)
						objs[i+1] = new ItemWrapper(String.valueOf(loc.getId()),
							loc.getName());
					else
						objs[i+1] = new ItemWrapper(String.valueOf(loc.getId()),
								loc.getName());						
				}else if (obj instanceof CustomerType){
					CustomerType cType = (CustomerType)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(cType.getType_id()),
							cType.getType_desc());
				}else if (obj instanceof PriceListType){
					PriceListType plt = (PriceListType)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(plt.getPlTypeName().toLowerCase()),
							plt.getPlTypeName().toUpperCase());
				}else if (obj instanceof SaleOrderType){
					SaleOrderType sot = (SaleOrderType)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(sot.getSaleOrderTypeId().toLowerCase()),
							Language.getInstance().getString(sot.getSaleOrderTypeDesc()));
				}else if (obj instanceof Warehouse){
					Warehouse sot = (Warehouse)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(sot.getId().toLowerCase()),
							sot.getName().toUpperCase());
				}else if (obj instanceof Lot){
					Lot sot = (Lot)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(sot.getId().toLowerCase()),
							sot.getNote().toUpperCase());
				}else if (obj instanceof String){
					objs[i/*+1*/] = new ItemWrapper(((String) obj).toLowerCase(), Language.getInstance().getString((String) obj));
				}else if (obj instanceof TransactionType){
					TransactionType txType = (TransactionType)obj;
					objs[i+1] = new ItemWrapper(String.valueOf(txType.getTranxTypeId()),
							Language.getInstance().getString(txType.getTranxTypeDesc()));
				}
					
			}			
			combo.setItems(objs);
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	/**
	 * Sort a CList data 
	 * @param sortOrder 0 = ASC, 1= DESC
	 * @param list The CList target will be sorted 
	 */
	@SuppressWarnings("unchecked")
	public static void sortTheCList(int sortOrder, CList list){
		Comparator<Object> comparator =(sortOrder == 0)? new CollectionSorter.ObjectComparatorAsc()
			: new CollectionSorter.ObjectComparatorDesc();
		List<Object> ret = (List)list.getListData(); 
		Collections.sort(ret, comparator);
		list.setListData(ret.toArray());
		list.validate();
	}
	
	//===================== Price List ===========================//
	@SuppressWarnings("unchecked")
	public static PriceList insertPriceList(PriceList p) throws Exception{
		setExtraEntityInfor(p, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_COMMON_INSERT_PRICELIST);
		Object[] params = new Object[] { p };
		Class[] types = new Class[] { PriceList.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (PriceList) res[0];
		
	}
	
	@SuppressWarnings("unchecked")
	public static PriceList updatePriceList(PriceList p) throws Exception{
		setExtraEntityInfor(p, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_COMMON_UPDATE_PRICELIST);
		Object[] params = new Object[] { p };
		Class[] types = new Class[] { PriceList.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (PriceList) res[0];		
	}
	
	public static List<PriceList> loadPriceList() throws Exception{		
		BusinessUtil<PriceList> bs = new BusinessUtil<PriceList>();
		List<PriceList> priceList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_COMMON_LOAD_PRICELIST,
				new Class[] { PriceList[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return priceList;	
	}
	public static List<PriceList> loadPriceListByCat(int catId) throws Exception{		
		BusinessUtil<PriceList> bs = new BusinessUtil<PriceList>();
		List<PriceList> priceList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_COMMON_LOAD_PRICELIST_BY_CAT,
				new Class[] { PriceList[].class }, 
				new Object[] { new Integer(catId) },
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return priceList;	
	}
	
	public static List<PriceList> searchPriceList(String searchStr) throws Exception{		
		BusinessUtil<PriceList> bs = new BusinessUtil<PriceList>();
		List<PriceList> priceList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_COMMON_SEARCH_PRICELIST,
				new Class[] { PriceList[].class }, 
				new Object[] { searchStr },
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return priceList;	
	}
	
	public Vector<PriceList> loadPriceListVector() throws Exception{
		List<PriceList> priceList = loadPriceList();
		Vector<PriceList> vctPriceList = new Vector<PriceList>();
		for(PriceList c:priceList)
			vctPriceList.add(c);
		return vctPriceList;	
	}
	
	public static PriceList getPriceList(String itemId, String currencyId, String plType) throws Exception{
		BusinessUtil<PriceList> bs = new BusinessUtil<PriceList>();
		PriceList pl = bs.webserviceGetDto(1,
				SystemConfiguration.LP_COMMON_GET_PRICELIST,
				new Class[] { PriceList.class }, 
				new Object[] {itemId, currencyId, plType},
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return pl;
	}
	
	//===================== BANK ===========================//
	@SuppressWarnings("unchecked")
	public static Bank insertBank(Bank bank) throws Exception{
		setExtraEntityInfor(bank, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_COMMON_INSERT_BANK);
		Object[] params = new Object[] { bank };
		Class[] types = new Class[] { Bank.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Bank) res[0];
		
	}
	
	@SuppressWarnings("unchecked")
	public static Bank updateBank(Bank bank) throws Exception{
		setExtraEntityInfor(bank, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_COMMON_UPDATE_BANK);
		Object[] params = new Object[] { bank };
		Class[] types = new Class[] { Bank.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Bank) res[0];		
	}
	
	public static List<Bank> loadBank() throws Exception{		
		BusinessUtil<Bank> bs = new BusinessUtil<Bank>();
		List<Bank> bankList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_COMMON_LOAD_BANK,
				new Class[] { Bank[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return bankList;	
	}
	
	public Vector<Bank> loadBankVector() throws Exception{
		List<Bank> bankList = loadBank();
		Vector<Bank> vctBank = new Vector<Bank>();
		for(Bank c:bankList)
			vctBank.add(c);
		return vctBank;	
	}
	
	public static Bank getBank(String bankId) throws Exception{
		BusinessUtil<Bank> bs = new BusinessUtil<Bank>();
		Bank bank = bs.webserviceGetDto(1,
				SystemConfiguration.LP_COMMON_GET_BANK,
				new Class[] { Bank.class }, 
				new Object[] {bankId},
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return bank;
	}
	
	//===================== LOCATION ===========================//
	@SuppressWarnings("unchecked")
	public static Location insertLocation(Location p) throws Exception{
		setExtraEntityInfor(p, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_COMMON_INSERT_LOCATION);
		Object[] params = new Object[] { p };
		Class[] types = new Class[] { Location.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Location) res[0];
		
	}
	@SuppressWarnings("unchecked")
	public static Location updateLocation(Location p) throws Exception{
		setExtraEntityInfor(p, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_COMMON_UPDATE_BANK);
		Object[] params = new Object[] { p };
		Class[] types = new Class[] { Location.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Location) res[0];		
	}
	
	public static List<Location> loadCountry() throws Exception{		
		BusinessUtil<Location> bs = new BusinessUtil<Location>();
		List<Location> locList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_COMMON_LOAD_COUNTRY,
				new Class[] { Location[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return locList;	
	}
	public static List<Location> loadLocationByParentId(Integer parentId, Integer locType) throws Exception{		
		BusinessUtil<Location> bs = new BusinessUtil<Location>();
		List<Location> locList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_COMMON_LOAD_LOCATION_BY_PARENT_ID,
				new Class[] { Location[].class }, 
				new Object[] { parentId, locType },
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return locList;	
	}
	
	public static Location getLocation(String p) throws Exception{
		BusinessUtil<Location> bs = new BusinessUtil<Location>();
		Location ret = bs.webserviceGetDto(1,
				SystemConfiguration.LP_COMMON_GET_LOCATION,
				new Class[] { Location.class }, 
				new Object[] {p},
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return ret;
	}
	
	//===================== CURRENCY ===========================//
	@SuppressWarnings("unchecked")
	public static Currency insertCurrency(Currency param) throws Exception{
		setExtraEntityInfor(param, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_COMMON_INSERT_CURRENCY);
		Object[] params = new Object[] { param };
		Class[] types = new Class[] { Currency.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Currency) res[0];
		
	}
	@SuppressWarnings("unchecked")
	public static Currency updateCurrency(Currency p) throws Exception{
		setExtraEntityInfor(p, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_COMMON_UPDATE_CURRENCY);
		Object[] params = new Object[] { p };
		Class[] types = new Class[] { Currency.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Currency) res[0];		
	}
	
	public static List<Currency> loadCurrency() throws Exception{		
		BusinessUtil<Currency> bs = new BusinessUtil<Currency>();
		List<Currency> cList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_COMMON_LOAD_CURRENCY,
				new Class[] { Currency[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return cList;	
	}
	public Vector<Currency> loadCurrencyVector() throws Exception{
		List<Currency> cList = loadCurrency();
		Vector<Currency> vctCurrency = new Vector<Currency>();
		for(Currency c:cList)
			vctCurrency.add(c);
		return vctCurrency;	
	}
	public static Currency getCurrency(String cId) throws Exception{
		BusinessUtil<Currency> bs = new BusinessUtil<Currency>();
		Currency c = bs.webserviceGetDto(1,
				SystemConfiguration.LP_COMMON_GET_CURRENCY,
				new Class[] { Currency.class }, 
				new Object[] {cId},
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return c;
	}
	
	//=====================ExRate===========================//
	@SuppressWarnings("unchecked")
	public static ExRate insertExRate(ExRate exRate) throws Exception{
		setExtraEntityInfor(exRate, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_COMMON_INSERT_EXRATE);
		Object[] params = new Object[] { exRate };
		Class[] types = new Class[] { ExRate.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (ExRate) res[0];
		
	}
	@SuppressWarnings("unchecked")
	public static ExRate updateExRate(ExRate exRate) throws Exception{
		setExtraEntityInfor(exRate, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_COMMON_UPDATE_EXRATE);
		Object[] params = new Object[] { exRate };
		Class[] types = new Class[] { ExRate.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (ExRate) res[0];		
	}
	
	public static List<ExRate> loadExRate() throws Exception{		
		BusinessUtil<ExRate> bs = new BusinessUtil<ExRate>();
		List<ExRate> exRateList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_COMMON_LOAD_EXRATE,
				new Class[] { ExRate[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return exRateList;	
	}
	public Vector<ExRate> loadExRateVector() throws Exception{
		List<ExRate> exRateList = loadExRate();
		Vector<ExRate> vctExRate = new Vector<ExRate>();
		for(ExRate c:exRateList)
			vctExRate.add(c);
		return vctExRate;	
	}
	public static ExRate getExRate(String currencyId, String bankId, String date) throws Exception{
		BusinessUtil<ExRate> bs = new BusinessUtil<ExRate>();
		ExRate exRate = bs.webserviceGetDto(1,
				SystemConfiguration.LP_COMMON_GET_EXRATE,
				new Class[] { ExRate.class }, 
				new Object[] {currencyId, bankId, date},
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return exRate;
	}
	
	//=============== Customer Type ===========================//
	public static List<CustomerType> loadCustomerType(){
		List<CustomerType> ret = new ArrayList<CustomerType>();
		ret.add(new CustomerType(1, "Cá nhân"));
		ret.add(new CustomerType(2, "Tổ chức"));
		return ret;
		
	}
	
	public static List<PriceListType> loadPriceListType(){
		List<PriceListType> ret = new ArrayList<PriceListType>();
		Properties props = Util.getSystemProperties();
		String plType = props.getProperty("pricelist.type");
		String[] plTypeArr = plType.split(",");
		for(String s:plTypeArr){
			PriceListType plt = new PriceListType(s);
			ret.add(plt);
		}		
		return ret;
	}
	/**
 	 * Load SaleOrderType from sale.cfg (contain JSON string describe SaleOrderType)
 	 * @return
 	 */
	public static List<SaleOrderType> loadSaleOrderType(){
		Properties props = Util.getProperties("cfg/sale.cfg");
		String jString =  props.getProperty("order.type");
		
		Gson gson = new Gson();
		
		/*
		// Quickest way to convert
		Type collectionType = new TypeToken<Collection<SaleOrderType>>(){}.getType();
		Collection<SaleOrderType> sotCols = gson.fromJson(jString, collectionType);
		*/			
		
		//The other way to return ArrayList of SaleOrderType
		List<SaleOrderType> sotList = new ArrayList<SaleOrderType>();
		JsonParser parser = new JsonParser();
	    JsonArray Jarray = parser.parse(jString).getAsJsonArray();
	    
	    for(JsonElement obj : Jarray )
	    {
	    	SaleOrderType sot = gson.fromJson( obj , SaleOrderType.class);
	    	sotList.add(sot);
	    }
	    //return sotCols;
	    return sotList;
	}
	
	public static List<TransactionType> loadTransactionType(){
		Properties props = Util.getProperties("cfg/daily_tranx.cfg");
		String jString =  props.getProperty("tranx.type");
		
		Gson gson = new Gson();
		
		/*
		// Quickest way to convert
		Type collectionType = new TypeToken<Collection<SaleOrderType>>(){}.getType();
		Collection<SaleOrderType> sotCols = gson.fromJson(jString, collectionType);
		*/			
		
		//The other way to return ArrayList of SaleOrderType
		List<TransactionType> txTypeList = new ArrayList<TransactionType>();
		JsonParser parser = new JsonParser();
	    JsonArray Jarray = parser.parse(jString).getAsJsonArray();
	    
	    for(JsonElement obj : Jarray )
	    {
	    	TransactionType txType = gson.fromJson( obj , TransactionType.class);
	    	txTypeList.add(txType);
	    }
	    //return sotCols;
	    return txTypeList;
	}

	public static void main(String[] args) throws Exception{
//		Bank b = CommonLogic.insertBank(new Bank("LVPB", "Lienviet Post Bank"));
//		b = CommonLogic.insertBank(b);
//		Bank b = CommonLogic.insertBank(new Bank("ACB", "Ã� ChÃ¢u Bank - Update"));
//		b = CommonLogic.updateBank(b);
//		Currency c = CommonLogic.insertCurrency(new Currency("VND", "Viá»‡t nam Ä‘á»“ng"));
		
//		Location l = new Location();
//		l.setName("Viá»‡t nam");
//		l.setLocation_type(0);
//		l = CommonLogic.insertLocation(l);
//		
		ExRate e =  new ExRate("GPB","VCB",1,"2013-04-16");
		e.setIns_time(String.valueOf(new Date()));
		e = CommonLogic.insertExRate(e);		
		
//		ExRate e1 =  CommonLogic.getExRate("USD", "VCB", "2013-04-16");//
//		System.out.println(e1);
		
		
		
	}
	public static List<GrnHistoryBean> loadGRNHistory(String ppId) throws Exception{		
		BusinessUtil<GrnHistoryBean> bs = new BusinessUtil<GrnHistoryBean>();
		List<GrnHistoryBean> grnHisList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_COMMON_LOAD_GRN_HISTORY,
				new Class[] { GrnHistoryBean[].class }, 
				new Object[] { ppId },
				SystemConfiguration.COMMON_SERVICE_TARGET_EPR);
		return grnHisList;	
	}
	
}
