package com.final_project2.command;


import com.final_project2.db.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class EditCarImageUrlCommand implements Command {
    private static final Logger log = Logger.getLogger(EditCarImageUrlCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        log.info("file " + req.getPart("file"));
        if (req.getPart("file") != null) {
            Part filePart = null;
            try {
                filePart = req.getPart("file");
                log.info("filePart " + filePart);
                String fileName = filePart.getSubmittedFileName();
                log.info("fileName " + fileName);
                InputStream is = filePart.getInputStream();
                log.info("input stream " + is);

                String imagesAddress = req.getSession().getServletContext().getRealPath("/car_image");
                log.info("image address  " + imagesAddress);
                Files.copy(is,
                        Paths.get(imagesAddress + "/" + fileName),
                        StandardCopyOption.REPLACE_EXISTING);

                req.getSession().setAttribute("newCarImageUrl", "/car_image/" + fileName);

            } catch (IOException | ServletException e) {
                throw new DBException("cannot edit car image", e);
            }
        }

        return "edit_car.jsp";
    }
}
