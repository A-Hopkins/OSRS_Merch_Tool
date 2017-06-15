package item.osbuddy.table.detailed.runescape.trend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public class DayTrend {

	public String getTrend() {
		return trend;
	}

	public String getPrice() {
		return price;
	}

	@SerializedName("trend")
	private final String trend;
	@SerializedName("price")
	private final String price;

	public DayTrend(String trend, String price) {
		this.trend = trend;
		this.price = price;
	}
}
