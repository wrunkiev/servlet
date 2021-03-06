package service;

import DAO.ItemDAO;
import model.Item;

import java.util.NoSuchElementException;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();

    public Item save(Item item)throws Exception{
        return itemDAO.save(item);
    }

    public Item findById(long id)throws NoSuchElementException{
        if(itemDAO.findById(id) == null){
            throw new NoSuchElementException();
        }
        return itemDAO.findById(id);
    }

    public Item update(Item item)throws Exception {
        return itemDAO.update(item);
    }

    public void delete(long id)throws Exception{
        itemDAO.delete(id);
    }
}