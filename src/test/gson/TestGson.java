package test.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.sale.SaleOrderType;

public class TestGson {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Properties props = Util.getProperties("cfg/sale.cfg");
		String jString =  props.getProperty("order.type");
		
		//List<SaleOrderType> sotList = new ArrayList<SaleOrderType>();
		
		Gson gson = new Gson();
		
		Type collectionType = new TypeToken<Collection<SaleOrderType>>(){}.getType();
		Collection<SaleOrderType> enums = gson.fromJson(jString, collectionType);
		
		for(SaleOrderType s:enums)
			System.out.println(s);
		/*JsonParser parser = new JsonParser();
	    JsonArray Jarray = parser.parse(jString).getAsJsonArray();
	    
	    for(JsonElement obj : Jarray )
	    {
	    	SaleOrderType sot = gson.fromJson( obj , SaleOrderType.class);
	    	sotList.add(sot);
	    	System.out.println(sot);
	    }*/
	}

}
