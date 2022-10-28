package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CommonFrame extends JFrame {
	static Connection con;
	static Statement stmt;
	
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/2022지방_2", "root", "1234");
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public CommonFrame(int width, int height, String title) {
		setSize(width, height);
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(2);
		
		setLayout(null);
	}
	
	JComponent setBounds(JComponent comp, int x, int y, int width, int height) {
		comp.setBounds(x, y, width, height);
		
		return comp;
	}
	
	static void errorMsg(String text) {
		JOptionPane.showMessageDialog(null, text, "경고", JOptionPane.ERROR_MESSAGE);
	}
	
	static void infoMsg(String text) {
		JOptionPane.showMessageDialog(null, text, "정보", JOptionPane.INFORMATION_MESSAGE);
	}
	
	static ResultSet getResulSet(String sql, Object... paramter) {
		try {
			var pstmt = con.prepareStatement(sql);
			
			for (int i = 0; i < paramter.length; i++) {
				pstmt.setObject(i + 1, paramter[i]);
			}
			
			return pstmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
