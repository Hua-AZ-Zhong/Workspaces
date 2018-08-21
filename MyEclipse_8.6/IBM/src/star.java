
public class star {
	public void printStar(int number){
		int line=0,space=0,star=0;
		for(line=0;line<number;line++){                    // first loop for line
			for(space=0;space<number-line-1;space++){      // second loop print space
				System.out.print(" ");
			}
			
			for(star=0;star<line*2+1;star++){         // third loop print *
				System.out.print("*");
			}
			System.out.println();
		}
	}
}
/** 中文测试 UTF-8字符集是否正常展示 **/