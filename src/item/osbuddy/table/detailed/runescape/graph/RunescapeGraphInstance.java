package item.osbuddy.table.detailed.runescape.graph;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public class RunescapeGraphInstance {

	@SerializedName("daily")
	private final Map<String, Integer> dailyTrend;
	@SerializedName("average")
	private final Map<String, Integer> averageTrend;

	public RunescapeGraphInstance(HashMap<String, Integer> dailyTrend, HashMap<String, Integer> averageTrend) {
		this.dailyTrend = dailyTrend;
		this.averageTrend = averageTrend;
	}
}
