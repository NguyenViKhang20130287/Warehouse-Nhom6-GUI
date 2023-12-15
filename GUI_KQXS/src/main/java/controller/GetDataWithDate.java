package controller;

import dao.LoadDataDAO;
import entity.KQXS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetDataWithDate", value = "/GetDataWithDate")
public class GetDataWithDate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date = request.getParameter("date");
//        response.getWriter().println(value);

        LoadDataDAO loadDataDAO = new LoadDataDAO();
//
        List<KQXS> bac = loadDataDAO.loadDataNewestMBByDate(date);
        List<KQXS> bacMerged = loadDataDAO.mergeRows(bac);

        //
        List<KQXS> trung = loadDataDAO.loadDataNewestMTByDate(date);
        List<KQXS> trungMerged = loadDataDAO.mergeAndSetNumber(trung);
        List<String> provincesMT = loadDataDAO.getProvinces(trungMerged);
        List<List<KQXS>> trungGrouped = loadDataDAO.splitByProvince(trungMerged);

//
        List<KQXS> nam = loadDataDAO.loadDataNewestMNByDate(date);
        List<KQXS> namMerged = loadDataDAO.mergeAndSetNumber(nam);
        List<String> provincesMN = loadDataDAO.getProvinces(namMerged);
        List<List<KQXS>> namGrouped = loadDataDAO.splitByProvince(namMerged);


        //
        request.setAttribute("bac", bacMerged);
        request.setAttribute("trung", trungMerged);
        request.setAttribute("provinceMT", provincesMT);
        request.setAttribute("trungGrouped", trungGrouped);
        request.setAttribute("nam", namMerged);
        request.setAttribute("provinceMN", provincesMN);
        request.setAttribute("namGrouped", namGrouped);
        request.setAttribute("date", date);
        request.getRequestDispatcher("resultOfDate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}