package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.ItemController;
import model.Item;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = "/test")
public class MyServlet extends HttpServlet {
    private ItemController itemController = new ItemController();
    private BufferedReader bufferedReader;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            String idString = req.getParameter("id");
            long id = Long.parseLong(idString);
            resp.getWriter().println(itemController.findById(id).toString());
        }catch (Exception e){
            resp.getWriter().println("404 Error. Data is not found.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            bufferedReader = req.getReader();
            String str = bodyContent(bufferedReader);
            Item item = getItem(str);
            if(item == null){
                throw new Exception();
            }
            item.setId(null);
            itemController.save(item);
        }catch (Exception e){
            resp.getWriter().println("500 Internal Server Error. Data is not saved.");
        }finally {
            bufferedReader.close();
        }
    }

    private Item getItem(String response) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            Item item = mapper.readValue(response, Item.class);
            return item;
        }catch (Exception e){
            return null;
        }
    }

    private String bodyContent(BufferedReader reader) throws IOException {
        String input;
        StringBuilder requestBody = new StringBuilder();
        while((input = reader.readLine()) != null) {
            requestBody.append(input);
        }
        return requestBody.toString();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            bufferedReader = req.getReader();
            String str = bodyContent(bufferedReader);
            Item item = getItem(str);
            if(item == null){
                throw new Exception();
            }
            itemController.update(item);
        }catch (Exception e){
            resp.getWriter().println("500 Internal Server Error. Data is not updated.");
        }finally {
            bufferedReader.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            bufferedReader = req.getReader();
            String str = bodyContent(bufferedReader);
            Item item = getItem(str);
            if(item == null){
                throw new Exception();
            }
            itemController.delete(item.getId());
        }catch (Exception e){
            resp.getWriter().println("500 Internal Server Error. Data is not deleted.");
        }finally {
            bufferedReader.close();
        }
    }
}
