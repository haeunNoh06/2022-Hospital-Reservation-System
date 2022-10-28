package setting;

import java.io.File;
import java.io.FileInputStream;
import java.sql.DriverAction;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Setting {

	public static void main(String[] args) throws Exception {

		//== Connection con = DriverManager.getConnection();
		//각각의 설정에 맞게 사용함
		//파일을 읽을 수 있게 됨
        var con = DriverManager.getConnection(
                "jdbc:mysql://localhost/?allowLoadLocalInfile=true",
                "root",
                "1234");
        var stmt = con.createStatement();
        
        stmt.execute("SET GLOBAL local_infile = true;");
		System.out.println("::DB Connection 생성::");
		
		try {
			stmt.execute("DROP DATABASE `2022지방_2`");
			System.out.println("2022지방_2 제거");
			
		}catch (Exception e) {
			System.out.println("2022지방_2 존재하지 않음");
		}
		
		//WorkBench DB를 만들어서 SQL 얻기
		//excute : 매개변수로 전달받은 SQL 구문을 수행하는 메서드
		stmt.execute("CREATE SCHEMA `2022지방_2` DEFAULT CHARACTER SET utf8 ;\r\n");
		System.out.println("2022지방_2 DB 생성");

		System.out.println("2022지방_2 user 테이블 생성");
		stmt.execute("CREATE TABLE `2022지방_2`.`user` ("
                + "u_no INT PRIMARY KEY AUTO_INCREMENT,"
                + "u_name VARCHAR(10),"
                + "u_id VARCHAR(10),"
                + "u_pw VARCHAR(15),"
                + "u_birth VARCHAR(15),"
                + "u_email VARCHAR(30),"
                + "u_gender INT,"
                + "u_graduate INT,"
                + "u_address VARCHAR(100),"
                + "u_img LONGBLOB"
                + ")");
		
		System.out.println("2022지방_2 company 테이블 생성");
		stmt.execute("CREATE TABLE `2022지방_2`.`company` ("
                + "c_no INT PRIMARY KEY AUTO_INCREMENT,"
                + "c_name VARCHAR(10),"
                + "c_ceo VARCHAR(10),"
                + "c_address VARCHAR(100),"
                + "c_category VARCHAR(15),"
                + "c_employee INT,"
                + "c_img LONGBLOB,"
                + "c_search INT"
                + ")");

		
		System.out.println("2022지방_2 employment 테이블 생성");
		stmt.execute("CREATE TABLE `2022지방_2`.`employment` ("
                + "e_no INT PRIMARY KEY AUTO_INCREMENT,"
                + "c_no INT,"
                + "e_title VARCHAR(30),"
                + "e_pay INT,"
                + "e_people INT,"
                + "e_gender INT,"
                + "e_graduate INT,"
                + "CONSTRAINT FK_0_e_no FOREIGN KEY (c_no) REFERENCES company (c_no) ON DELETE CASCADE ON UPDATE CASCADE"
                + ")");
		
		System.out.println("2022지방_2 applicant 테이블 생성");
		stmt.execute("CREATE TABLE `2022지방_2`.`applicant` ("
                + "a_no INT PRIMARY KEY AUTO_INCREMENT,"
                + "e_no INT,"
                + "u_no INT,"
                + "a_apply INT,"
                + "CONSTRAINT FK_1_e_no FOREIGN KEY (e_no) REFERENCES employment (e_no) ON DELETE CASCADE ON UPDATE CASCADE,"
                + "CONSTRAINT FK_2_u_no FOREIGN KEY (u_no) REFERENCES user (u_no) ON DELETE CASCADE ON UPDATE CASCADE"
                + ")");
		
		//txt 파일을 mysql DB importing 집어넣기
		//LOAD LOCAL INFILE
		//파일마다 띄어쓰기 할 것
        stmt.execute("LOAD DATA "
                + "LOCAL INFILE 'datafiles/user.txt' "
                + "INTO TABLE `2022지방_2`.`user` "
                + "IGNORE 1 LINES");
		stmt.execute("LOAD DATA "
				+ "LOCAL INFILE 'datafiles/company.txt' "
				+ "INTO TABLE `2022지방_2`.`company` "
				+ "IGNORE 1 LINES");
		stmt.execute("LOAD DATA "
				+ "LOCAL INFILE 'datafiles/employment.txt' "
				+ "INTO TABLE `2022지방_2`.`employment` "
				+ "IGNORE 1 LINES");
		stmt.execute("LOAD DATA "
				+ "LOCAL INFILE 'datafiles/applicant.txt' "
				+ "INTO TABLE `2022지방_2`.`applicant` "
				+ "IGNORE 1 LINES");
		
		stmt.execute("USE `2022지방_2`");
		
		//Image 삽입 LONGBLOB FILE 파일 BINARY
		//prepareStatement -> SQL 템플릿 만들고, 실행 준비 상태로 만듦.
		//try () => try 블록 이후 try 문에 사용된 자원을 close 없애줌
		try (var pstmt = con.prepareStatement("UPDATE user SET u_img = ? WHERE u_no = ?")) {
			
			//FIleInputStream
			for ( int i = 1; i <= 20; i++) {
				//첫 번째 ?
				pstmt.setObject(1, new FileInputStream(new File("datafiles/회원사진/"+i+".jpg")));
				pstmt.setObject(2, i);
				//mysql 실행하는 부분
				pstmt.execute();
			}
			
		}
		
		try (var pstmt = con.prepareStatement("UPDATE company SET c_img = ? WHERE c_name = ?")) {
			//fileList를 얻어와야 함
			var fileList = new File("datafiles/기업").listFiles();
			
			//근데 이미지가 두 개가 있으니까 일단 포문돌려
			 for ( var file : fileList) {
				 
				 //파일 이름이 1.jpg로 끝나는 것만 pstmt에 넣겠다
				 if ( file.getName().endsWith("1.jpg")) {
					 pstmt.setObject(1, new FileInputStream(file));
					 pstmt.setObject(2, file.getName().substring(0, file.getName().length() -5));
					 pstmt.execute();
				 }
			 }
		}
		
		
		//쿼리를 실행하고 결과값을 rs에 저장
		//ResultSet rs = stmt.executQuery("select * from t_emp");
		
		//while(rs.next()) : 결과값이 있는 동안 반복문을 실행
	}

}
