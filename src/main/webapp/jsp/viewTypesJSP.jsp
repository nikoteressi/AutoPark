<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="application.dto.VehicleTypesDto" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <title>Просмотр типов Машин</title>
</head>
<body>
    <div class="center flex full-vh">
        <div class="vertical-center">
            <a class="ml-20" href="${pageContext.request.contextPath}/">На главную</a>
            <br />
            <br />
            <hr />
            <br />
            <%
                String sortKey = null;
                String order = null;
                if (request.getParameter("name") != null) sortKey= "name";
                if (request.getParameter("tax") != null) sortKey= "tax";
                if (request.getParameter("asc") != null) order= "asc";
                if (request.getParameter("desc") != null) order= "desc";
            %>
            <%
                if (sortKey != null) {
            %>
            <%
                String clearPath = "/viewTypes";
                String ascPath = "?" + sortKey + "&asc";
                String descPath = "?" + sortKey + "&desc";
            %>
            <div>
                <a class="ml-20" href="<%=descPath%>">Сортировать по убыванию</a>
                <a class="ml-20" href="<%=ascPath%>">Сортировать по возрастанию.</a>
                <a class="ml-20" href="<%=clearPath%>">Очистить параметры поиска</a>
            </div>
            <br />
            <hr />
            <br />
            <%}%>
            <table>
                <caption>Типы машин</caption>
                <tr>
                    <th>Название</th>
                    <th>Коэффициент дорожного налога</th>
                </tr>
                <%
                    List<VehicleTypesDto> dtoList = (List<VehicleTypesDto>) request.getAttribute("types");
                    Comparator<VehicleTypesDto> comparator = null;
                    if (sortKey != null && sortKey.equalsIgnoreCase("name")) {
                        comparator = Comparator.comparing(VehicleTypesDto::getTypeName);
                    }
                    if (sortKey != null && sortKey.equalsIgnoreCase("tax")) {
                        comparator = Comparator.comparing(VehicleTypesDto::getTaxCoefficient);
                    }
                    if (order != null && comparator != null && order.equalsIgnoreCase("desc")) {
                        comparator = comparator.reversed();
                    }
                    if (comparator != null) {
                        dtoList.sort(comparator);
                    }
                    for (VehicleTypesDto dto : dtoList) {
                %>
                <tr>
                    <td><%=dto.getTypeName()%></td>
                    <td><%=dto.getTaxCoefficient()%></td>
                </tr>
                <%}%>
            </table>
            <%
                if (dtoList.size() > 0) {
            %>
            <p>Минимальный коэффициент: <strong><%=dtoList.stream().map(VehicleTypesDto::getTaxCoefficient).min(Double::compare).get()%></strong></p>
            <p>Максимальный коэффициент: <strong><%=dtoList.stream().map(VehicleTypesDto::getTaxCoefficient).max(Double::compare).get()%></strong></p>
            <%}%>
            <br />
            <hr />
            <br />
            <div>
                <%
                    if (request.getParameter("name") == null) {
                %>
                <a class="ml-20" href="${pageContext.request.contextPath}/viewTypes?name">Сортировать по названию</a>
                <%}%>
                <%
                    if (request.getParameter("tax") == null) {
                %>
                <a class="ml-20" href="${pageContext.request.contextPath}/viewTypes?name">Сортировать по коэффиценту</a>
                <%}%>
            </div>
        </div>
    </div>
</body>
</html>
