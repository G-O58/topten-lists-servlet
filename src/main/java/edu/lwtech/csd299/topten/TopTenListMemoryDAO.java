package edu.lwtech.csd299.topten;

import java.util.*;

import org.apache.log4j.Logger;

public class TopTenListMemoryDAO implements DAO<TopTenList> {
    
    private static final Logger logger = Logger.getLogger(TopTenListMemoryDAO.class.getName());
    
    private int nextID;
    private List<TopTenList> memoryDB;

    public TopTenListMemoryDAO() {
        this.nextID = 1000;
        this.memoryDB = new ArrayList<>();
        // addSampleData();
    }

    public boolean init() {
        return true;
    }

    public int insert(TopTenList list) {
        logger.debug("Inserting " + list + "...");

        if (list.getID() != -1) {
            logger.error("Attempting to add previously added list: " + list);
            return -1;
        }
        
        TopTenList newList = new TopTenList(generateNextID(), list);       // Replace -1 ID with official ID
        memoryDB.add(newList);
        
        logger.debug("Item successfully inserted!");
        return newList.getID();
    }
    
    public void delete(int id) {
        logger.debug("Trying to delete list with ID: " + id);

        TopTenList listFound = null;
        for (TopTenList list : memoryDB) {
            if (list.getID() == id) {
                listFound = list;
                break;
            }
        }
        if (listFound != null)
            memoryDB.remove(listFound);
    }

    public TopTenList getByID(int id) {
        logger.debug("Trying to get list with ID: " + id);
        
        TopTenList listFound = null;
        for (TopTenList list : memoryDB) {
            if (list.getID() == id) {
                listFound = list;
                break;
            }
        }
        return listFound;
    }
    
    public TopTenList getByIndex(int index) {
        // Note: indexes are zero-based
        logger.debug("Getting list with index: " + index);
        return memoryDB.get(index);
    }
    
    public List<TopTenList> getAll() {
        logger.debug("Getting all items");
        return new ArrayList<>(memoryDB);       // Return copy of List colleciton
    }    
    
    public List<Integer> getAllIDs() {
        logger.debug("Getting Item IDs...");

        List<Integer> itemIDs = new ArrayList<>();
        for (TopTenList item : memoryDB) {
            itemIDs.add(item.getID());
        }
        return itemIDs;
    }

    public int size() {
        return memoryDB.size();
    }

    public void disconnect() {
        memoryDB = null;
    }

    // =================================================================
    
    public synchronized int generateNextID() {
        return nextID++;
    }
}
