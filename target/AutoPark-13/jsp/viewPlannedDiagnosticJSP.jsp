<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="application.dto.VehicleDto" %>
<%@ page import="application.dto.OrdersDto" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <title>Просмотр Машин</title>
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <%
            List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");
            List<OrdersDto> orders = (List<OrdersDto>) request.getAttribute("orders");
        %>
        <a class="ml-20" href="${pageContext.request.contextPath}/">На главную</a>
        <br/>
        <br/>
        <hr/>
        <br/>
        <p>Период  минут</p>
        <p>Последняя былы  минуты назад</p>
        <table>
            <caption>Машины</caption>
            <tr>
                <th>Тип</th>
                <th>Модель</th>
                <th>Номер</th>
                <th>Масса</th>
                <th>Дата выпуска</th>
                <th>Цвет</th>
                <th>Модель двигателя</th>
                <th>Пробег</th>
                <th>Бак</th>
                <th>Была исправлена</th>
                <th>Починена</th>
            </tr>
            <tr>
                <%
                    for (VehicleDto dto : dtoList) {
                        for (OrdersDto o : orders) {
                %>
                <td><%=dto.getTypeName()%>
                </td>
                <td><%=dto.getModelName()%>
                </td>
                <td><%=dto.getRegistrationNumber()%>
                </td>
                <td><%=dto.getWeight()%>
                </td>
                <td><%=dto.getManufactureYear()%>
                </td>
                <td><%=dto.getColor()%>
                </td>
                <td><%=dto.getEngineName()%>
                </td>
                <td><%=dto.getMileage()%>
                </td>
                <td><%=dto.getTankVolume()%>
                </td>
                <td><%=o.getVehicle_id() == dto.getId() ? "нет" : "да"%>
                </td>
                <td><%=o.getVehicle_id() == dto.getId() ? "нет" : "да"%>
                </td>
                <%
                        }
                    }
                %>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
