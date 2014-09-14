package com.nn.ishop.client.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class ReportUlti {
	public static Connection establishConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			String oracleURL = "jdbc:postgresql://localhost:1975/ishop";
			connection = DriverManager.getConnection(oracleURL, "ishop", "654321");
			connection.setAutoCommit(false);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return connection;
	}

	public static boolean previewReport(String reportName, HashMap jasperParameter) {
		boolean isSuccessful = true;
		Connection connection = null;
		try {
			JasperReport jasperReport;
			JasperPrint jasperPrint;
			connection = establishConnection();
			// jrxml compiling process
			jasperReport = JasperCompileManager.compileReport("reports/" + reportName + ".jrxml");

			// filling report with data from data source
			jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);
			JasperViewer viewer = new JasperViewer(jasperPrint);
			viewer.setTitle("IShop's report system"); // TODO: This is bullshit! Doesn't work.
			viewer.viewReport(jasperPrint, false);
		} catch (Exception ex) {
			ex.printStackTrace();
			isSuccessful = false;
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccessful;
	}
}
