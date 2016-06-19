package entities;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class Count {
	/*
    public static final int treeType=1;
	public static final int flowerType = 2;
	public static final int fernType = 3;
	public static final int lowtreeType = 4;
	public static final int lampType =  5;
	public static final int playerType = 6;
	public static final int vegetableType = 7;
	public static final int ironType = 8;
	*/
	public static final int tHeart = 0;
	public static final int tTree = 1;
	public static final int tStone = 2;
	public static final int tMineral = 3;
	public static final int tFood = 4;
	public static final int tFax = 5;
	public static final int tFire = 6;
	public static final int tFirecamp = 7;
	public static final int tTorch = 8;
	public static final int tHouse = 9;
	public static final int tBoot = 10;
	public static final int tBoat = 11;
	
	long cHeart = 20;
	long cTree = 0, cStone = 0, cMineral = 0, cFood = 0, cFax = 0, cFire = 0, cFirecamp = 0, cTorch = 0, cHouse = 0, cBoot = 0, cBoat = 0;
	
	public long getCount(int type) {
		switch (type) {
		case tHeart:
			return cHeart;
		case tTree:
			return cTree;
		case tStone:
			return cStone;
		case tMineral:
			return cMineral;
		case tFood:
			return cFood;
		case tFax:
			return cFax;
		case tFire:
			return cFire;
		case tFirecamp:
			return cFirecamp;
		case tTorch:
			return cTorch;
		case tHouse:
			return cHouse;
		case tBoot:
			return cBoot;
		case tBoat:
			return cBoat;
		default:
			return 0;
		}
	}
	
	public void setCount(int type) {
		switch (type) {
		case tHeart:
			--cHeart;
		case tTree:
			++cTree;
		case tStone:
			++cStone;
		case tMineral:
			++cMineral;
		case tFood:
			++cFood;
		case tFax:
			++cFax;
		case tFire:
			++cFire;
		case tFirecamp:
			++cFirecamp;
		case tTorch:
			++cTorch;
		case tHouse:
			++cHouse;
		case tBoot:
			++cBoot;
		case tBoat:
			++cBoat;
		default:
			return;
		}
	}
	
	public void clearCount(int type) {
		switch (type) {
		case tHeart:
			cHeart = 20;
		case tTree:
			cTree = 0;
		case tStone:
			cStone = 0;
		case tMineral:
			cMineral = 0;
		case tFood:
			cFood = 0; 
		case tFax:
			cFax = 0;
		case tFire:
			cFire = 0;
		case tFirecamp:
			cFirecamp = 0;
		case tTorch:
			cTorch = 0;
		case tHouse:
			cHouse = 0;
		case tBoot:
			cBoot = 0;
		case tBoat:
			cBoat = 0;
		default:
			return;
		}
	}
	
	/*
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
	
	public void clearCount(int type) {
		switch (type) {
		case treeType:
			treeCount = 0;
			break;
		case fernType:
			fernCount = 0;
			break;
		case lowtreeType:
			lowtreeCount = 0;
			break;
		case flowerType:
			flowerCount = 0;
			break;
		default:
			break;
		
		}
	}
	*/
	
}
