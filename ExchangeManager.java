import java.io.*;
import java.util.*;

public class ExchangeManager {

    private List<Clothing> items = new ArrayList<>();
    private int nextID = 1;

    // find next ID after loading
    private void updateNextID() {
        int max = 0;
        for (Clothing c : items) {
            if (c.getItemID() > max) max = c.getItemID();
        }
        nextID = max + 1;
    }

    // add item
    public Clothing addClothing(String type, String size, String condition,
                                String donor, boolean available) {

        int id = nextID++;

        String time = java.time.LocalDateTime.now().toString();

        Clothing c = new Clothing(id, type, size, condition, donor, available, time);

        items.add(c);
        return c;
    }

    // claim
    public boolean claimItem(int id) {
        for (Clothing c : items) {
            if (c.getItemID() == id && c.isAvailable()) {
                c.setAvailable(false);
                return true;
            }
        }
        return false;
    }

    // remove
    public boolean removeItem(int id) {
        for (Clothing c : items) {
            if (c.getItemID() == id) {
                items.remove(c);
                return true;
            }
        }
        return false;
    }

    // update donor name only
    public boolean updateItem(int id, String newDonor) {
        for (Clothing c : items) {
            if (c.getItemID() == id) {
                c.setDonorName(newDonor);
                return true;
            }
        }
        return false;
    }

    public List<Clothing> getAllItems() {
        return new ArrayList<>(items);
    }

    public List<Clothing> getAvailableItems() {
        List<Clothing> temp = new ArrayList<>();
        for (Clothing c : items) {
            if (c.isAvailable()) temp.add(c);
        }
        return temp;
    }

    // searches
    public List<Clothing> searchByType(String t) {
        List<Clothing> out = new ArrayList<>();
        for (Clothing c : items)
            if (c.getType().equalsIgnoreCase(t))
                out.add(c);
        return out;
    }

    public List<Clothing> searchBySize(String s) {
        List<Clothing> out = new ArrayList<>();
        for (Clothing c : items)
            if (c.getSize().equalsIgnoreCase(s))
                out.add(c);
        return out;
    }

    public List<Clothing> searchByCondition(String con) {
        List<Clothing> out = new ArrayList<>();
        for (Clothing c : items)
            if (c.getCondition().equalsIgnoreCase(con))
                out.add(c);
        return out;
    }

    // save
    public boolean saveToCSV(String file) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            for (Clothing c : items) {
                pw.println(c.getItemID() + "," +
                           c.getType() + "," +
                           c.getSize() + "," +
                           c.getCondition() + "," +
                           c.getDonorName() + "," +
                           c.isAvailable() + "," +
                           c.getDateTime());
            }
            pw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // load
    public boolean loadFromCSV(String file) {
        try {
            File f = new File(file);
            if (!f.exists()) return false;

            items.clear();
            Scanner in = new Scanner(f);

            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] p = line.split(",");

                if (p.length < 7) continue;

                int id = Integer.parseInt(p[0]);
                String type = p[1];
                String size = p[2];
                String condition = p[3];
                String donor = p[4];
                boolean available = Boolean.parseBoolean(p[5]);
                String time = p[6];

                Clothing c = new Clothing(id, type, size, condition, donor, available, time);
                items.add(c);
            }

            in.close();
            updateNextID();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
