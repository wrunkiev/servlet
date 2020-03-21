package controller;

import model.Item;
import service.ItemService;
import util.HibernateSessionFactory;

public class ItemController {
    private ItemService itemService = new ItemService();

    public Item save(Item item)throws Exception{
        return itemService.save(item);
    }

    public Item findById(long id){
        return itemService.findById(id);
    }    

    public Item update(Item item)throws Exception {
        return itemService.update(item);
    }

    public void delete(long id)throws Exception{
        itemService.delete(id);
    }
}