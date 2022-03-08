package application.servlets;

import application.dto.service.DtoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/viewTypes")
public class viewCarTypesServlet extends HttpServlet {
    private DtoService service;
    @Override
    public void init() throws ServletException {
        super.init();
        service = new DtoService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("types", service.getTypes());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewTypesJSP.jsp");
        dispatcher.forward(req, resp);
    }
}
