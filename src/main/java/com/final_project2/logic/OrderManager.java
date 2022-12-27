package com.final_project2.logic;

import com.final_project2.db.DBException;
import com.final_project2.db.DBManager;
import com.final_project2.entity.Order;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderManager {
    private static final Logger log = Logger.getLogger(OrderManager.class);
    private static final String PROVIDER_INTRO = "car rental service provider:";
    private static final String PROVIDER = "Car rental final project";
    private static final String IBAN_PROVIDER = "UA2535820912340000056789";
    private static final String CLIENT_INTRO = "Client:";


    private static OrderManager instance;

    private DBManager dbManager;

    private OrderManager(){dbManager = DBManager.getInstance();}

    public static synchronized OrderManager getInstance(){
        if (instance==null){
            instance = new OrderManager();
        }
        return instance;
    }

    /**
     *
     * @param order to add to database
     * @throws DBException if something wrong with database
     */
    public void addNewOrder(Order order) throws DBException {
        try (Connection connection = dbManager.getConnection()){
            dbManager.addNewOrder(connection, order);
        }catch (SQLException e){
            log.error("connection is fail " + e);
            throw new DBException("Cannot add new order", e);
        }
    }

    /**
     *
     * @return total number of orders in the database
     * @throws DBException if something wrong with database
     */
    public int getAllOrdersSize() throws DBException {
        try (Connection connection = dbManager.getConnection()){
            return dbManager.getAllOrdersSize(connection);
        } catch (SQLException e) {
            throw new DBException("cannot get all orders size ",e);
        }
    }

    /**
     *
     * @param id of user by which to search for his orders
     * @return total number of orders in the database
     * @throws DBException if something wrong with database
     */
    public int getOrdersSizeByUser(int id) throws DBException {
        try (Connection connection = dbManager.getConnection()){
            return dbManager.getOrdersSizeByUser(connection, id);
        } catch (SQLException e) {
            throw new DBException("cannot get orders by user id size ",e);
        }
    }

    /**
     *
     * @param offset - part of all orders from database
     * @param limit - amount items in List for show in page
     * @return List<Order> - part of all orders from according to the parameters
     * @throws DBException if something wrong with database
     */
    public List<Order> getAllOrders(int offset, int limit) throws DBException {
        List<Order> orders;
            try (Connection connection = dbManager.getConnection()) {
                orders = dbManager.getAllOrders(connection, offset,limit);
                log.debug("all orders from database "+ orders);
                return orders;
            } catch (SQLException e) {
                log.error("cannot get all orders ", e);
                throw new DBException("Cannot get all orders ", e);
        }
    }

    /**
     *
     * @param id of user by which to search for his orders
     * @param offset - part of all orders from database
     * @param limit - amount items in List for show in page
     * @return List<Order> - part of all orders from database by user
     * @throws DBException if something wrong with database
     */
    public List<Order> getAllOrdersByUser(int id, int offset, int limit) throws DBException {
        List<Order> orders;
        try (Connection connection = dbManager.getConnection()) {
            orders = dbManager.getAllOrdersByUser(connection, id, offset,limit);
            log.debug("all orders by User from database "+ orders);
            return orders;
        } catch (SQLException e) {
            log.error("cannot get all orders by User ", e);
            throw new DBException("Cannot get all orders by User", e);
        }
    }

    /**
     *
     * @param orderId to search
     * @return order by id
     * @throws DBException if something wrong with database
     */
    public Order getOrderById(int orderId) throws DBException {
        try (Connection connection = dbManager.getConnection()){
            return dbManager.getOrderById(connection, orderId);
        }catch (SQLException e) {
            log.error("cannot get order by id ", e);
            throw new DBException("Cannot get order by id", e);
        }
    }

    /**
     *
     * @param orderId to search
     * @param newStatus to update order by id
     * @throws DBException if something wrong with database
     */
    public void setNewOrderStatus(int orderId, String newStatus) throws DBException {
        try (Connection connection = dbManager.getConnection()){
            dbManager.setNewOrderStatus(connection, orderId, newStatus);
        }catch (SQLException e){
            log.error("cannot set new order status " + e);
            throw new DBException("Cannot set new order status", e);
        }

    }

    /**
     *
     * @param orderId to search in Db (int value)
     * @param damageCost to update damage cost (double value)
     * @param managersComment update info about damage (string value)
     * @throws DBException if something wrong with database
     */
    public void setInfoAboutDamage(int orderId, double damageCost, String managersComment) throws DBException {
        try (Connection connection = dbManager.getConnection()){
            dbManager.setInfoAboutDamage(connection, orderId, damageCost,managersComment);
        }catch (SQLException e){
            log.error("cannot set new order status " + e);
            throw new DBException("Cannot set new order status", e);
        }
    }

    /**
     *
     * @param req we need to get real path
     * @param orderId to search our order in Db
     * @return address pdf order
     * @throws DBException if something wrong with database
     */
    public String createPdfOrder(HttpServletRequest req, int orderId) throws DBException {
        try(Connection connection = dbManager.getConnection()){
            Order order = dbManager.getOrderById(connection,orderId);
            String firstLine = "Invoice to payment #" + orderId;
            String client = order.getUser().getFirstName() + " " + order.getUser().getLastName();
            String address = req.getSession().getServletContext().getRealPath("/ordersPdf");
            String path = address + "/order" + orderId + ".pdf";
            PdfWriter pdfWriter;
            try {
                pdfWriter = new PdfWriter(path);
            } catch (FileNotFoundException e) {
                throw new DBException("cannot create pdf invoice",e);
            }
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.addNewPage();
            Document document = new Document(pdfDocument);
            Paragraph paragraph1 = new Paragraph(firstLine);
            document.add(paragraph1);
            document.add(new Paragraph(" "));
            Paragraph paragraph2 = new Paragraph(PROVIDER_INTRO + ": " + PROVIDER);
            document.add(paragraph2);
            document.add(new Paragraph("iban provider: " + IBAN_PROVIDER));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(CLIENT_INTRO + " " + client));
            document.add(new Paragraph("Passport: " + order.getSeriesAndNumberOfThePassport()));

            float[] columnWidth = {20f, 280f, 50f};
            Table table = new Table(columnWidth);

            table.addCell(new Cell().add(new Paragraph("#")));
            table.addCell(new Cell().add(new Paragraph("Car")));
            table.addCell(new Cell().add(new Paragraph("Cost")));

            table.addCell(new Cell().add(new Paragraph("1")));
            table.addCell(new Cell().add(new Paragraph(order.getCar().getBrand() + " " + order.getCar().getModel())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getCost()))));

            document.add(table);
            document.close();
        }catch (SQLException e){
            log.error("cannot get create pdf " + e);
            throw new DBException("Cannot get create pdf order", e);
        }

        return "/ordersPdf/order" + orderId + ".pdf";
    }

    /**
     *
     * @param req we need to get real path
     * @param orderId to search our order in Db
     * @return address pdf order
     * @throws DBException if something wrong with database
     */
    public String createPdfDamageOrder(HttpServletRequest req, int orderId) throws DBException {

        try(Connection connection = dbManager.getConnection()){
            Order order = dbManager.getOrderById(connection,orderId);
            log.debug("order for damage pdf"+order);
            String firstLine = "Invoice to payment #" + orderId+"_1";
            String client = order.getUser().getFirstName() + " " + order.getUser().getLastName();
            String address = req.getSession().getServletContext().getRealPath("/ordersPdf");
            String path = address + "/order_damage" + orderId + "_1.pdf";
            PdfWriter pdfWriter = null;
            try {
                pdfWriter = new PdfWriter(path);
            } catch (FileNotFoundException e) {
                throw new DBException("cannot create pdf order ",e);
            }
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.addNewPage();
            Document document = new Document(pdfDocument);

            Paragraph paragraph1 = new Paragraph(firstLine);
            document.add(paragraph1);
            document.add(new Paragraph(" "));
            Paragraph paragraph2 = new Paragraph(PROVIDER_INTRO + ": " + PROVIDER);
            document.add(paragraph2);
            document.add(new Paragraph("iban provider: " + IBAN_PROVIDER));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(CLIENT_INTRO + " " + client));
            document.add(new Paragraph("Passport: " + order.getSeriesAndNumberOfThePassport()));

            float[] columnWidth = {20f, 280f, 80f};
            Table table = new Table(columnWidth);

            table.addCell(new Cell().add(new Paragraph("#")));
            table.addCell(new Cell().add(new Paragraph("Damage info")));
            table.addCell(new Cell().add(new Paragraph("Cost of damage")));

            table.addCell(new Cell().add(new Paragraph("1")));
            table.addCell(new Cell().add(new Paragraph(order.getManagersComment())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getDamageCost()))));
            log.debug("damage order "+order.getDamageCost());
            document.add(table);
            document.close();
        }catch (SQLException e){
            log.error("cannot get create pdf " + e);
            throw new DBException("Cannot get create pdf damage order", e);
        }

        return "/ordersPdf/order_damage" + orderId + "_1.pdf";
    }
}
