package servlet;

import DAO.ItemDAO;
import controller.ItemController;
import model.Item;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@WebServlet(urlPatterns = "/test")
public class MyServlet extends HttpServlet {
    private ItemController itemController = new ItemController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            String idString = req.getParameter("id");
            long id = Long.parseLong(idString);
            resp.getWriter().println(itemController.findById(id).toString());
        }catch (Exception e){
            resp.getWriter().println();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            String str = bodyContent(req.getReader());
            Item item = getItem(str);
            item.setId(null);
            itemController.save(item);
        }catch (Exception e){
            resp.getWriter().println();
        }
    }

    private Item getItem(String response) throws JSONException {
        try{
            JSONObject itemJson = new JSONObject(response);
            Long id = itemJson.getLong("id");
            String name = itemJson.getString("name");
            Date dateCreated = new SimpleDateFormat("dd/MM/yyyy").parse(itemJson.getString("dateCreated"));
            Date lastUpdatedDate = new SimpleDateFormat("dd/MM/yyyy").parse(itemJson.getString("lastUpdatedDate"));
            String description = itemJson.getString("description");

            Item item = new Item();
            item.setId(id);
            item.setName(name);
            item.setDateCreated(dateCreated);
            item.setLastUpdatedDate(lastUpdatedDate);
            item.setDescription(description);
            return item;
        }catch (Exception e){
            return null;
        }
    }

    private String bodyContent(BufferedReader reader) throws IOException {
        String input = null;
        StringBuilder requestBody = new StringBuilder();
        while((input = reader.readLine()) != null) {
            requestBody.append(input);
        }
        return requestBody.toString();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String str = bodyContent(req.getReader());
            Item item = getItem(str);
            itemController.update(item);
        }catch (Exception e){
            resp.getWriter().println();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String str = bodyContent(req.getReader());
            Item item = getItem(str);
            itemController.delete(item.getId());
        }catch (Exception e){
            resp.getWriter().println();
        }
    }
}
