package service;

import DAO.ItemDAO;
import model.Item;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();

    public Item save(Item item)throws Exception{
        checkItemNull(item);
        return itemDAO.save(item);
    }

    public Item findById(long id)throws Exception{
        return itemDAO.findById(id);
    }

    public Item update(Item item)throws Exception {
        checkItemNull(item);
        if(item.getId() == null){
            throw new Exception("Exception in method ItemService.update. Enter id for item.");
        }
        return itemDAO.update(item);
    }

    public void delete(long id)throws Exception{
        itemDAO.delete(id);
    }

    private static void checkItemNull(Item item)throws Exception{
        if(item == null){
            throw new Exception("Exception in method ItemService.checkItemNull. Item can't be null.");
        }
    }
}
