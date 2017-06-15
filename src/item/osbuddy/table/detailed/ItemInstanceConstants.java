package item.osbuddy.table.detailed;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public class ItemInstanceConstants {

	private final static String RUNESCAPE_ITEM_INSTANCE_BASE_LINK = "http://services.runescape.com/m=itemdb_oldschool";

	private final static String RUNESCAPE_ITEM_INSTANCE_DETAILS = "/api/catalogue/detail.json?item=";

	private final static String RUNESCAPE_ITEM_INSTANCE_GRAPH_DETAILS = "/api/graph/";

	public static String getRunescapeItemInstanceDetails(int id) {
		return RUNESCAPE_ITEM_INSTANCE_BASE_LINK + RUNESCAPE_ITEM_INSTANCE_DETAILS + id;
	}

	/**
	 *
	 *
	 * @param id
	 * @return Epoch Time : Value
	 */
	public static String getRunescapeItemInstanceGraphDetails(int id) {
		return RUNESCAPE_ITEM_INSTANCE_BASE_LINK + RUNESCAPE_ITEM_INSTANCE_GRAPH_DETAILS + id + ".json";
	}

	private final static String OSBUDDY_ITEM_INSTANCE_BASE_LINK = "http://api.rsbuddy.com/grandExchange?a=guidePrice&i=";

}
