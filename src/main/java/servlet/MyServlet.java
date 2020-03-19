package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.ItemController;
import model.Item;

import javax.servlet.ServletException;
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
            Item item = itemController.findById(id);
            if(item == null){
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().println(resp.getStatus());
            }else {
                resp.getWriter().println(itemController.findById(id).toString());
            }
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(resp.getStatus());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            Item item = requestRead(req);
            item.setId(null);
            itemController.save(item);
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(resp.getStatus());
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
            Item item = requestRead(req);
            itemController.update(item);
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(resp.getStatus());
        }finally {
            bufferedReader.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            Item item = requestRead(req);
            itemController.delete(item.getId());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(resp.getStatus());
        }finally {
            bufferedReader.close();
        }
    }

    private Item requestRead(HttpServletRequest req)throws Exception{
        bufferedReader = req.getReader();
        String str = bodyContent(bufferedReader);
        Item item = getItem(str);
        if(item == null){
            throw new Exception();
        }
        return item;
    }
}
