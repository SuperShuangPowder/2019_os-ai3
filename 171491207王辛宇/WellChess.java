import java.util.Scanner;
	import java.util.Random;
public class WellChess {
	
		char[][] chess = new char[4][4];
		//�ĳ����ַ�����
		char player,computer;
		boolean playerF;
		boolean detectWin(char player) {
			//�ж����player�Ƿ�ʤ��
			for(int i=1;i<4;i++)
				if(chess[i][1]==player&&chess[i][2]==player&&chess[i][3]==player) {
					return true;
				}else {
					if(chess[1][i]==player&&chess[2][i]==player&&chess[3][i]==player)
						return true;
				}
			if(chess[1][1]==player&&chess[2][2]==player&&chess[3][3]==player)
				return true;
			if(chess[1][3]==player&&chess[2][2]==player&&chess[3][1]==player)
				return true;
			return false;
		}
		
		boolean isEmpty() {//�ж������Ƿ�Ϊ��
			for(int i=1;i<4;i++) {
				for(int j=1;j<4;j++) {
					if(chess[i][j]=='-')
						return false;
				}
			}
			return true;
		}
		
		//��ʼ����Ϸ
		public void startGame() {
			for(int i=1;i<4;i++)
				for(int j=1;j<4;j++)
					chess[i][j]='-';
			Scanner input = new Scanner(System.in);
			player = 'O';
			computer='X';
			System.out.print("��ѡ����ҷ���X/O,(����1����ѡ��X������2����ѡ��O,Ĭ��ΪO)��");
			int get = input.nextInt();
			if(get==1) {
				player = 'X';
				computer = 'O';
			}else if(get!=2) {
				System.out.println("��������Ĭ��ΪO");
			}
			playerF = true;
			System.out.println("�Ƿ�����1/2(1�����ǣ�2�����Ĭ����)��");
			get = input.nextInt();
			if(get==2)playerF=false;
			else if(get!=1) {
				System.out.println("��������Ĭ��������֣�");
			}
			if(playerF) {
				Print();
			}
			else {
				Random rand = new Random();
				int startPoint = rand.nextInt(9)+1;
				int row;
				if(startPoint%3==0)row=startPoint/3;
				else row = startPoint/3+1;
				int col = startPoint-(row-1)*3;
				chess[row][col]=computer;
				Print();
			}
	//		input.close();
		}
		void Print() {
			System.out.println("-------------");
			for(int i=1;i<4;i++) {
				for(int j=1;j<4;j++) {
					System.out.print("| ");
					System.out.print(chess[i][j]+" ");
					if(j==3)System.out.println("|");
				}
				if(i==3)System.out.println("-------------");
			}
		}
		void playerInput() {
			System.out.print("�ֵ������ˣ����������λ�ã�");
			int row,col;
			Scanner input = new Scanner(System.in);
			while(true) {
				int num = input.nextInt();
				if(num>=1&&num<=9) {
					if(num%3==0)row = num/3;
					else row = num/3+1;
					col = num-3*(row-1);
					if(chess[row][col]!='-')System.out.println("��λ����������");
					else break;
				}else {
					System.out.println("������������������");
					continue;
				}
			}
			chess[row][col]=player;
			Print();
	//		input.close();
		}
		//-1,0,1�ֱ����������ʤ����ƽ�֣�����ʤ��ʱ�ڵ��ֵ
		int bestInput(String state,String nextState,int alpha,int beta) {
			//���룬���ü�֦�Ĺ���
			char ch;
			if(state.equals("computer"))ch=computer;
			else ch=player;
			if(detectWin(ch)) {
				if(state.equals("computer"))return 1;
				else  return -1;
			}else if(isEmpty()) {
				return 0;
			}
			else {//û��Ӯ
				int score;
				for(int i=1;i<4;i++) {
					for(int j=1;j<4;j++) {
						if(chess[i][j]=='-') {
							chess[i][j]=ch;
							score=bestInput(nextState,state,alpha,beta);
							//ֹͣ�ݹ��״����
							chess[i][j]='-';
							if(state.equals("computer")) {//��ǰ������Ϊ����
								if(score>=alpha)alpha=score;
								//������½�
								if(alpha>beta)return beta;
							}
							else {//������Ϊ���� 
								if(score<beta)beta=score;
								if(beta<=alpha)return alpha;
							}
						}
					}
				}
				if(state.equals("computer"))return alpha;
				else return beta;
			}
		}
		
		void computerInput() {
			int best=0;
			int bestScore=-1000; 
			int score;
			for(int i=1;i<=3;i++) {
				for(int j=1;j<=3;j++) {
					if(chess[i][j]=='-') {
						chess[i][j]=computer;
						score = bestInput("player","computer",-1000,1000);
						//alpha-beta��֦��һ���������½��޼�֦���㷨����ʼ�����½���Ϊ����
						if(score>bestScore) {
						//��ͬһ��Ľڵ�������Ҫ������̽�Եݹ飬�û��ݷ��ҵ�����ʵ������ʹ�Լ�ʤ�����
							bestScore=score;
							best=(i-1)*3+j;
						}
						chess[i][j]='-';
					}
				}
			}
			int row,col;
			if(best%3==0)row = best/3;
			else row = best/3+1;
			col = best-(row-1)*3;
			chess[row][col]=computer;
			Print();
		}
		
		public static void main(String[] args) {
			Chess c = new Chess();
			c.startGame();//��ʼ������
			String current = "player";
			while(!c.detectWin(c.computer)&&!c.detectWin(c.player)&&!c.isEmpty()) {
				//��ֹ�����ǵ�ǰ����Ϊ�ջ�����һ��ʤ��
				switch(current) {
				case "player":
					c.playerInput();current="computer";break;//�����������ֵ�������
				case "computer":
					c.computerInput();current="player";break;
				default:break;
				}
			}
			if(c.detectWin(c.computer))System.out.println("����ʤ����");
			else if(c.detectWin(c.player))System.out.println("���ʤ��");
			else System.out.println("ƽ�֣�");
		}

}
