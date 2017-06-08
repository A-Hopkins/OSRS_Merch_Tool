package entry;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public final class EntryInstance {

	private String dateStamp;
	private String name;
	private int bought;
	private int sold;
	private int amount;

	public EntryInstance(String date, String name, int bought, int sold, int amount) {
		super();
		this.dateStamp = date;
		this.name = name;
		this.bought = bought;
		this.sold = sold;
		this.amount = amount;
	}

	public String getDate() {
		return dateStamp;
	}

	public void setDate(String date) {
		this.dateStamp = date;
	}

	public String getName() {
		return name;
	}

	public int getBought() {
		return bought;
	}

	public int getSold() {
		return sold;
	}

	public int getAmount() {
		return amount;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBought(int bought) {
		this.bought = bought;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
