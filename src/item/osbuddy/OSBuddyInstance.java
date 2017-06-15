package item.osbuddy;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public final class OSBuddyInstance {

	@SerializedName("name")
	private final String name;
	@SerializedName("buy_average")
	private final int buyAverage;
	@SerializedName("id")
	private final int id;
	@SerializedName("overall_average")
	private final int overallAverage;
	@SerializedName("members")
	private final boolean members;
	@SerializedName("sp")
	private final int storePrice;
	@SerializedName("sell_average")
	private final int sellAverage;

	public OSBuddyInstance(String name, int buyAverage, int id, int overallAverage, boolean members, int storePrice, int sellAverage) {
		this.name = name;
		this.buyAverage = buyAverage;
		this.id = id;
		this.overallAverage = overallAverage;
		this.members = members;
		this.storePrice = storePrice;
		this.sellAverage = sellAverage;
	}

	public int getBuyAverage() {
		return buyAverage;
	}

	public int getId() {
		return id;
	}

	public int getOverallAverage() {
		return overallAverage;
	}

	public int getSellAverage() {
		return sellAverage;
	}

	public int getStorePrice() {
		return storePrice;
	}

	public String getName() {
		return name;
	}

	public boolean isMembers() {
		return members;
	}

}

