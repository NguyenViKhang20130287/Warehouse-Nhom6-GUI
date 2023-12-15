<%@ page import="java.util.List" %>
<%@ page import="entity.KQXS" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <title>Kết quả xổ số 3 miền</title>
</head>

<body>

<div class="container">
    <header>
        <h3>KẾT QUẢ XỔ SỐ 3 MIỀN</h3>
    </header>
    <div class="wrapper">
        <form action="GetDataWithDate" class="date">
            <label for="date">Ngày: </label>
            <input type="date" id="date" name="date" value="<%=request.getAttribute("date")%>">
            <input type="submit" value="Chọn ngày">
        </form>
<%--        <div class="update">--%>
<%--            <button class="update-btn">Cập nhật</button>--%>
<%--        </div>--%>
        <div class="result">


            <div class="result-bac">
                <table>
                    <thead>
                    <th colspan="2">Miền Bắc</th>
                    </thead>

                    <tbody>
                    <%List<KQXS> bac = (List<KQXS>) request.getAttribute("bac");%>
                    <tr>
                        <th><%=bac.get(0).getDate()%>
                        </th>
                        <th><%=bac.get(0).getProvince()%>
                        </th>
                    </tr>
                    <% for (KQXS kqmb : bac) {%>
                    <tr>
                        <td><%=kqmb.getAward()%>
                        </td>
                        <td><%=kqmb.getNumber()%>
                        </td>
                    </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>

            <div class="result-trung">
                <table>
                    <thead>
                    <th colspan="4">Miền Trung</th>
                    </thead>
                    <tbody>
                    <%
                        List<KQXS> trung = (List<KQXS>) request.getAttribute("trung");
                        List<String> provincesMT = (List<String>) request.getAttribute("provinceMT");
                        List<List<KQXS>> trungGrouped = (List<List<KQXS>>) request.getAttribute("trungGrouped");
                    %>
                    <tr>
                        <th><%=trung.get(0).getDate()%>
                                <%for (String province : provincesMT){%>
                        <th><%=province%>
                        </th>
                        <%}%>
                    </tr>
                    <%for (int i = 0; i < trungGrouped.get(0).size(); i++) {%>
                    <tr>
                        <td><%=trungGrouped.get(0).get(i).getAward()%>
                        </td>
                        <%
                            for (List<KQXS> kqmtList : trungGrouped) {
                        %>
                        <td><%=kqmtList.get(i).getNumber()%>
                        </td>
                        <%
                            }
                        %>
                    </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>
            <div class="result-nam">
                <table class="tab-nam">
                    <thead>
                    <th colspan="5">Miền Nam</th>
                    </thead>
                    <tbody>
                    <%
                        List<KQXS> nam = (List<KQXS>) request.getAttribute("nam");
                        List<String> provincesMN = (List<String>) request.getAttribute("provinceMN");
                        List<List<KQXS>> namnGrouped = (List<List<KQXS>>) request.getAttribute("namGrouped");
                    %>
                    <tr>
                        <th><%=nam.get(0).getDate()%>
                                <%for (String province : provincesMN){%>
                        <th><%=province%>
                        </th>
                        <%}%>
                    </tr>
                    <%for (int i = 0; i < namnGrouped.get(0).size(); i++) {%>
                    <tr>
                        <td><%=namnGrouped.get(0).get(i).getAward()%>
                        </td>
                        <%
                            for (List<KQXS> kqmtList : namnGrouped) {
                        %>
                        <td><%=kqmtList.get(i).getNumber()%>
                        </td>
                        <%
                            }
                        %>
                    </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>


        </div>
    </div>
</div>

</body>

</html>