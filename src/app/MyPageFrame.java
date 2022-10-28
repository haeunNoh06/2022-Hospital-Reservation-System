package app;

import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MyPageFrame extends CommonFrame {

	static int userNo;
	
	public MyPageFrame() {
		super(600, 300, "Mypage");

		//컬럼명 들을 ,로 
		var model = new DefaultTableModel("번호,기업명,모집정보,시급,모집정원,최종학력,성별,합격여부".split(","),0);
		var table = new JTable(model);
//		
		
		add(setBounds(new JScrollPane(table), 16, 100, 550, 120));
		
		try ( var rs = getResulSet("SELECT a_no, c_name, e_title, e_pay, e_people, e_graduate, e_gender, a_apply\r\n"
				+ "FROM applicant a\r\n"
				+ "INNER JOIN employment e ON a.e_no = e.e_no\r\n"
				+ "INNER JOIN company c ON e.c_no = c.c_no\r\n"
				//user의 no로 실행해야 함, a_no는 하나의 결과만 나오기 때문에 
				+ "WHERE u_no = ?", userNo)) {
					
			int id = 0;
			
			while ( rs.next()) {
				model.addRow(new Object[] {
					++id,
					rs.getString(2),
					rs.getString(3),
					rs.getInt(4),
					rs.getInt(5),
					rs.getInt(6),
					rs.getInt(7),
					rs.getInt(8),
					
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		userNo = 1;
		var myPageFrame = new MyPageFrame();
		myPageFrame.setVisible(true);
	}

}
