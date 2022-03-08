package application.servlets;

import application.dto.service.DtoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/info")
public class ViewInfoServlet extends HttpServlet {
    private DtoService service;
    @Override
    public void init() throws ServletException {
        super.init();
        service = new DtoService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("cars", service.getVehicles().stream().filter(vehicleDto -> id == vehicleDto.getId()).collect(Collectors.toList()));
        req.setAttribute("rents", service.getRent(id));
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewCarInfoJSP.jsp");
        dispatcher.forward(req, resp);
    }
}
