public class Clothing {

    private int itemID;
    private String type;
    private String size;
    private String condition;
    private String donorName;
    private boolean available;
    private String dateTime;

    public Clothing(int itemID, String type, String size, String condition,
                    String donorName, boolean available, String dateTime) {

        this.itemID = itemID;
        this.type = type;
        this.size = size;
        this.condition = condition;
        this.donorName = donorName;
        this.available = available;
        this.dateTime = dateTime;
    }

    public int getItemID() { return itemID; }
    public String getType() { return type; }
    public String getSize() { return size; }
    public String getCondition() { return condition; }
    public String getDonorName() { return donorName; }
    public boolean isAvailable() { return available; }
    public String getDateTime() { return dateTime; }

    public void setDonorName(String d) { donorName = d; }
    public void setAvailable(boolean a) { available = a; }

    public String toString() {
        return itemID + " | " + type + " | " + size + " | " + condition +
               " | " + donorName + " | " + (available ? "Available" : "Taken") +
               " | " + dateTime;
    }
}
