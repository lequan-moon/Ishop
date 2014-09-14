package test.report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

/*import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;*/

public class TestReport {
	/* JasperReport is the object
	that holds our compiled jrxml file 
	static JasperReport jasperReport;


	 JasperPrint is the object contains
	report after result filling process 
	static JasperPrint jasperPrint;
	
	static JasperViewer jasperViewer;
	
	public static Connection establishConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			String oracleURL = "jdbc:postgresql://localhost:1975/ishop";
			connection = DriverManager.getConnection(oracleURL, "ishop",
					"654321");
			connection.setAutoCommit(false);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return connection;

	}
	*//**
	 * @param args
	 *//*
	public static void main(String[] args) throws Exception {
		// connection is the data source we used to fetch the data from
		Connection connection = establishConnection(); 
		// jasperParameter is a Hashmap contains the parameters
		// passed from application to the jrxml layout
		HashMap jasperParameter = new HashMap();		
		// jrxml compiling process
		jasperReport = JasperCompileManager.compileReport("F:\\Projects\\iShop\\iShopV1.1\\reports\\Coffee.jrxml");

		// filling report with data from data source

		jasperPrint = JasperFillManager.fillReport(jasperReport,jasperParameter, connection); 
		JasperViewer.viewReport(jasperPrint, false);
	}
	
*/
}
