package DAO;

import model.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.util.List;

import static util.HibernateSessionFactory.createSessionFactory;

public class ItemDAO {
    public Item save(Item item)throws Exception{
        checkItemNull(item);
        existItem(item);

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            session.save(item);

            tr.commit();

            return item;
        } catch (HibernateException e) {
            System.err.println("Exception in method ItemDAO.save. Save item with ID: " + item.getId() + " is failed");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    public Item findById(long id){
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            Item item = session.get(Item.class, id);

            tr.commit();
            return item;
        } catch (HibernateException e) {
            System.err.println("Exception in method ItemDAO.findById. Item with ID: " + id + " is not defined in DB.");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    public Item update(Item item)throws Exception{
        checkItemNull(item);

        if(item.getId() == null){
            throw new Exception("Exception in method ItemDAO.update. Enter id for item.");
        }

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            session.update(item);

            tr.commit();
            return item;
        } catch (HibernateException e) {
            System.err.println("Exception in method ItemDAO.update. Update item with ID: " + item.getId() + " is failed");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    public void delete(long id)throws Exception{
        Item item = findById(id);

        if(item == null){
            throw new Exception("Exception in method ItemDAO.delete. Item with ID: " + id + " is not defined in DB.");
        }

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            session.delete(item);

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Exception in method ItemDAO.delete. Delete item with ID: " + item.getId() + " is failed.");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    private static void checkItemNull(Item item)throws Exception{
        if(item == null){
            throw new Exception("Exception in method ItemDAO.checkItemNull. Item can't be null.");
        }
    }

    private static void existItem(Item item) throws Exception{
        if(getItemFromDB(item) > 0){
            throw new Exception("Exception in method ItemDAO.existItem. Item is exist in DB already");
        }
    }

    @SuppressWarnings("unchecked")
    private static int getItemFromDB(Item item){
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM ITEMS WHERE ITEM_NAME = ?";
            NativeQuery query = session.createNativeQuery(sql, Item.class);
            query.setParameter(1, item.getName());

            List<Item> list = session.createNativeQuery(sql).setParameter(1, item.getName()).getResultList();

            tr.commit();
            return list.size();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }
}
