package com.nn.ishop.client.logic.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.util.Identifiable;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.server.dto.product.ProductCategory;
import com.nn.ishop.server.dto.product.ProductUOM;

/**
 * Contain useful abstract methods using for client end.
 * @author NguyenNghia
 * @param <E> Class Parameter Type
 *
 */
public class BusinessUtil<E> {
	public BusinessUtil() {
		super();
	}	
	
	/**
	 * Abstract method to get DTO List from a specify Service Endpoint Reference 
	 * @param Id
	 * @param localPart
	 * @param responseType should be initialized as <code>new Class[] {SomeClass[].class}</code>
	 * @param parameter
	 * @param endpointReference - to define, refer to constant in 
	 * <code>ServiceConstants.EMPLOYEE_SERVICE_TARGET_EPR</code>
	 * @return
	 * @throws Exception
	 */
	public List<E> webserviceGetDtoList(
			Integer Id
			, String localPart
			, Class[] responseType
			, Object[] parameter
			, EndpointReference endpointReference
			)throws Exception{
		RPCServiceClient serviceClient = new RPCServiceClient();
	    Options options = serviceClient.getOptions();
	    options.setTo(endpointReference);
	    QName qname = new QName(SystemConfiguration.NAMESPACE_URI, localPart);	     
	    Object[] response = serviceClient.invokeBlocking(qname, parameter, responseType);
	    List<E> elementList = new ArrayList<E>();
	    E[] elementArray = (E[])response[0];
	    for(E e: elementArray){
	    	elementList.add(e);
	    }
		return elementList;
	}

	
	/**
	 * @param Id
	 * @param localPart
	 * @param responseType responseType should be initialized as 
	 * <code>new Class[] {SomeClass.class}</code>
	 * @param parameter should be initialized as <code> new Object[]{Object instance}</code> 
	 * @param endpointReference
	 * @return
	 * @throws Exception
	 */
	public E webserviceGetDto(
			Integer Id
			, String localPart
			, Class[] responseType
			, Object[] parameter
			, EndpointReference endpointReference
			)throws Exception{
		RPCServiceClient serviceClient = new RPCServiceClient();
	    Options options = serviceClient.getOptions();
	    options.setTo(endpointReference);
	    QName qname = new QName(SystemConfiguration.NAMESPACE_URI, localPart);	     
	    Object[] response = serviceClient.invokeBlocking(qname, parameter, responseType);
	    List<E> elementList = new ArrayList<E>();
	    E elm = (E)response[0];
		return elm;
	}	
	
	public E wsGetContainerVersion()throws Exception{
		E e=null;
		RPCServiceClient serviceClient = new RPCServiceClient();
	    Options options = serviceClient.getOptions();
	    options.setTo(SystemConfiguration.WS_VERSION_SERVICE_TARGET_EPR);
	    
	    QName qname = new QName(SystemConfiguration.WS_VERSION_NAMESPACE_URI
	    		, SystemConfiguration.LOCALPART_WS_VERSION_GET_VERSION);
	    
	    Object[] response = serviceClient.invokeBlocking(qname, new Object[]{null}, new Class[]{String.class});
	    e = (E)response[0];		
		return e;
	}
	
	
	
	
}
