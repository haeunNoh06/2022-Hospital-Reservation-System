package app;

public class StaticMember {

	static class ABC {
		//static : 정적(움직이지 않는다)
		//공간이 변하지 않는 것
		//하나의 공간만 바라봄
		static int num;
		
		int age;
	}
	
	//new를 쓰지 않아도(새로운 메모리)를 쓰지 않아도 사용가능
	public static void main(String[] args) {
		ABC a = new ABC();
		ABC b = new ABC();
		ABC c = new ABC();
		
		a.age = 10;
		b.age = 10;
		c.age = 10;
		
		
//		ABC.num = 100;
//		ABC.num = 200;
//		ABC.num = 300;
		a.num = 100;
		b.num = 200;
		c.num = 300;
	}
}
