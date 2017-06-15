package item.osbuddy.table.detailed.runescape;

import com.google.gson.annotations.SerializedName;
import item.osbuddy.table.detailed.runescape.trend.DayTrend;
import item.osbuddy.table.detailed.runescape.trend.IncrementalDayChange;


/**
 * Created by Alex on 6/8/2017.
 *
 */
public final class RunescapeItemInstance {

	public RunescapeItemInstance(String icon, String largeIcon, int id, String type, String typeIcon, String name, String description, DayTrend currentTrend, DayTrend todaysTrend, boolean membersItem, IncrementalDayChange thirtyDayTrend, IncrementalDayChange ninetyDayTrend, IncrementalDayChange oneHundredEigthyDayTrend) {
		this.icon = icon;
		this.largeIcon = largeIcon;
		this.id = id;
		this.type = type;
		this.typeIcon = typeIcon;
		this.name = name;
		this.description = description;
		this.currentTrend = currentTrend;
		this.todaysTrend = todaysTrend;
		this.membersItem = membersItem;
		this.thirtyDayTrend = thirtyDayTrend;
		this.ninetyDayTrend = ninetyDayTrend;
		this.oneHundredEigthyDayTrend = oneHundredEigthyDayTrend;
	}

	public String getLargeIcon() {
		return largeIcon;
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public DayTrend getCurrentTrend() {
		return currentTrend;
	}

	public DayTrend getTodaysTrend() {
		return todaysTrend;
	}

	public boolean isMembersItem() {
		return membersItem;
	}

	public IncrementalDayChange getThirtyDayTrend() {
		return thirtyDayTrend;
	}

	public IncrementalDayChange getNinetyDayTrend() {
		return ninetyDayTrend;
	}

	public IncrementalDayChange getOneHundredEigthyDayTrend() {
		return oneHundredEigthyDayTrend;
	}

	@SerializedName("icon")
	private final String icon;
	@SerializedName("icon_large")
	private final String largeIcon;
	@SerializedName("id")
	private final int id;
	@SerializedName("type")
	private final String type;
	@SerializedName("typeIcon")
	private final String typeIcon;
	@SerializedName("name")
	private final String name;
	@SerializedName("description")
	private final String description;
	@SerializedName("current")
	private final DayTrend currentTrend;
	@SerializedName("today")
	private final DayTrend todaysTrend;
	@SerializedName("members")
	private final boolean membersItem;
	@SerializedName("day30")
	private final IncrementalDayChange thirtyDayTrend;
	@SerializedName("day90")
	private final IncrementalDayChange ninetyDayTrend;
	@SerializedName("day180")
	private final IncrementalDayChange oneHundredEigthyDayTrend;

}
