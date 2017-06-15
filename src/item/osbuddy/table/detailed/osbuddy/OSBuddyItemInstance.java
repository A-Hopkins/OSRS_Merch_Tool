package item.osbuddy.table.detailed.osbuddy;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 6/8/2017.
 *
 */
class OSBuddyItemInstance {

	public OSBuddyItemInstance(int overall, int buying, int buyingQuantity, int selling, int sellingQuantity) {
		this.overall = overall;
		this.buying = buying;
		this.buyingQuantity = buyingQuantity;
		this.selling = selling;
		this.sellingQuantity = sellingQuantity;
	}

	public int getOverall() {
		return overall;
	}

	public int getBuying() {
		return buying;
	}

	public int getBuyingQuantity() {
		return buyingQuantity;
	}

	public int getSelling() {
		return selling;
	}

	public int getSellingQuantity() {
		return sellingQuantity;
	}

	@SerializedName("overall")
	private final int overall;
	@SerializedName("buying")
	private final int buying;
	@SerializedName("buyingQuantity")
	private final int buyingQuantity;
	@SerializedName("selling")
	private final int selling;
	@SerializedName("sellingQuantity")
	private final int sellingQuantity;

}

