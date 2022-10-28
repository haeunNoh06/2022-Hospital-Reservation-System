package app;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class SignUpFrame extends CommonFrame {

	
	public SignUpFrame() {
		super(500, 500, "회원가입");
		
		var panel = new JPanel(null);
		
		panel.setBorder(
				new TitledBorder(
						new LineBorder(Color.BLACK),
						"회원가입"));
		
		add(this.setBounds(panel, 20, 20, 450, 400));
		
		var labelList = "이름,아이디,비밀번호,생년월일,이메일,성별,최종학력,주소".split(",");
		var tfList = new JTextField[6];
		var cbList = new JComboBox[3];
		var rdoList = new JRadioButton[2];
		
		for (int i = 0; i < labelList.length; i++) {
			panel.add(this.setBounds(new JLabel(labelList[i]), 10, 40 + 40 * i, 80, 20));
			
			if (i <= 3) {
				tfList[i] = new JTextField();
				
				panel.add(this.setBounds(tfList[i], 80, 40 + 40 * i, 140, 24));
			} else if (i == 4) {
				tfList[i] = new JTextField();
				panel.add(this.setBounds(tfList[i], 80, 40 + 40 * i, 80, 24));
				panel.add(this.setBounds(new JLabel("@"), 160, 40 + 40 * i, 80, 20));
				
				cbList[0] = new JComboBox("naver.com, outlook.com, daum.com, gmail.com, nate.com, kebi.com, yahoo.com, korea.com, empal.com, hanmail.net".split(", "));
				panel.add(this.setBounds(cbList[0], 180, 40 + 40 * i, 120, 24));
			} else if (i == 5) {
				var group = new ButtonGroup();
				
				group.add(rdoList[0] = new JRadioButton("남"));
				group.add(rdoList[1] = new JRadioButton("여"));
						
				panel.add(this.setBounds(rdoList[0], 80, 40 + 40 * i, 40, 24));
				panel.add(this.setBounds(rdoList[1], 120, 40 + 40 * i, 40, 24));
			} else if (i == 6) {
				panel.add(this.setBounds(cbList[1] = new JComboBox(",대학교 졸업,고등학교 졸업,중학교 졸업".split(",")),
						80, 40 + 40 * i, 120, 24));
			} else {
				panel.add(this.setBounds(cbList[2] = new JComboBox(",서울,부산".split(",")),
						80, 40 + 40 * i, 70, 24));
				panel.add(this.setBounds(tfList[5] = new JTextField(),
						160, 40 + 40 * i, 140, 24));
				
				tfList[5].setEditable(false);
			}
		}
		
		var lbImg = new JLabel();
		
		lbImg.setBorder(new LineBorder(Color.BLACK));
		
		panel.add(setBounds(lbImg, 290, 30, 150, 150));
		
		var btSubmit = new JButton("가입");
		
		add(setBounds(btSubmit, 380, 424, 80, 24));
		
		// 주소 콤보박스
		cbList[2].addActionListener(e -> {
			
			tfList[5].setText("");
			
			if (cbList[2].getSelectedIndex() == 0) {
				// 빈칸 클릭
				tfList[5].setEditable(false);
				return; // 현재 실행 함ㅅ 종료
			}
			
			// 주소를 클릭한 부분
			tfList[5].setEditable(true);
			tfList[5].grabFocus();
			
		});
		
		btSubmit.addActionListener(e -> {
			
		});
		
	}
	
	public static void main(String[] args) {
		new SignUpFrame().setVisible(true);
	}
	
}
