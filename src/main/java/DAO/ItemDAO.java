package DAO;

import model.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import javax.persistence.NoResultException;
import static util.HibernateSessionFactory.createSessionFactory;

public class ItemDAO {
    public Item save(Item item)throws Exception{
        checkItemNull(item);
        existItem(item);

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.save(item);

            tr.commit();
            return item;
        }catch (HibernateException e){
            System.err.println("Exception in method ItemDAO.save. Save item with ID: " + item.getId() + " is failed");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public Item findById(long id){
        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            Item item = session.get(Item.class, id);

            tr.commit();
            return item;
        }catch (HibernateException e){
            System.err.println("Exception in method ItemDAO.findById. Item with ID: " + id + " is not defined in DB.");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public Item update(Item item)throws Exception{
        checkItemNull(item);

        if(item.getId() == null){
            throw new Exception("Exception in method ItemDAO.update. Enter id for item.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.update(item);

            tr.commit();
            return item;
        }catch (HibernateException e){
            System.err.println("Exception in method ItemDAO.update. Update item with ID: " + item.getId() + " is failed");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public void delete(long id)throws Exception{
        Item item = findById(id);

        if(item == null){
            throw new Exception("Exception in method ItemDAO.delete. Item with ID: " + id + " is not defined in DB.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.delete(item);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Exception in method ItemDAO.delete. Delete item with ID: " + item.getId() + " is failed.");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    private static void checkItemNull(Item item)throws Exception{
        if(item == null){
            throw new Exception("Exception in method ItemDAO.checkItemNull. Item can't be null.");
        }
    }

    private static void existItem(Item item) throws Exception{
        if(getItemFromDB(item) != null){
            throw new Exception("Exception in method ItemDAO.existItem. Item with ID: " +
                    getItemFromDB(item).getId() + " is exist in DB already");
        }
    }

    private static Item getItemFromDB(Item item){
        Session session = null;
        Transaction tr = null;
        Item it;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM ITEMS WHERE ITEM_NAME = ?";
            NativeQuery query = session.createNativeQuery(sql, Item.class);
            query.setParameter(1, item.getName());

            it = (Item)query.getSingleResult();

            tr.commit();
            return it;
        }catch (NoResultException ex){
            System.err.println(ex.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }catch (HibernateException e){
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }
}
