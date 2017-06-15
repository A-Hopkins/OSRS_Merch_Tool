package item.osbuddy.table.detailed.runescape.trend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public class IncrementalDayChange {

	public String getTrend() {
		return trend;
	}

	public String getChange() {
		return change;
	}

	@SerializedName("trend")
	private String trend;
	@SerializedName("change")
	private String change;

	public IncrementalDayChange(String trend, String change) {
		this.trend = trend;
		this.change = change;
	}
}
