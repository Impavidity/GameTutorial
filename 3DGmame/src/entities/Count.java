package entities;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class Count {
    public static final int treeType=1;
	public static final int flowerType = 2;
	public static final int fernType = 3;
	public static final int lowtreeType = 4;
	public static final int lampType =  5;
	public static final int playerType = 6;
	public static final int vegetableType = 7;
	public static final int ironType = 8;
	
	long treeCount, fernCount, lowtreeCount, flowerCount;

	public long getCount(int type) {
		switch (type) {
		case treeType:
			return treeCount;
		case fernType:
			return fernCount;
		case lowtreeType:
			return lowtreeCount;
		case flowerType:
			return flowerCount;
		default:
			return 0;
		}
	}

	public void setCount(int type) {
		switch (type) {
		case treeType:
			treeCount ++;
			break;
		case fernType:
			fernCount ++;
			break;
		case lowtreeType:
			lowtreeCount++;
			break;
		case flowerType:
			flowerCount ++;
			break;
		default:
			break;
		}
	}
}
