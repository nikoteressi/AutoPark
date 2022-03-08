<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="application.dto.VehicleDto" %>
<%@ page import="application.dto.VehicleRentsDto" %>
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
            int id = (int) request.getAttribute("id");
            List<VehicleRentsDto> rents = (List<VehicleRentsDto>) request.getAttribute("rents");
            List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");
            VehicleDto dto = null;
            for (VehicleDto v : dtoList) {
                if (v.getId() == id) dto = v;
            }
            assert dto != null;
        %>
        <a class="ml-20" href="${pageContext.request.contextPath}/viewCars">Назад</a>
        <a class="ml-20" href="${pageContext.request.contextPath}/">На главную</a>
        <br/>
        <br/>
        <hr/>
        <br/>
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
                <th>Расход</th>
                <th>Коэффицент налога</th>
                <th>KM На полный бак</th>
            </tr>
            <tr>
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
                <td><%=dto.getPer100km()%>
                </td>
                <td><%=dto.getTaxCoefficient()%>
                </td>
                <td><%=dto.getMaxKm()%>
                </td>
            </tr>
        </table>
        <p>Налог за месяц: <%=dto.getTax()%>
        </p>
        <br/>
        <hr/>
        <br/>
        <table>
            <caption>Аренды</caption>
            <tr>
                <th>Дата</th>
                <th>Стоимость</th>
            </tr>
            <tr>
                <%
                    double sum = 0;
                    for (VehicleRentsDto r : rents) {
                %>
                <td><%=r.getDate()%>
                </td>
                <td><%=r.getRentCost()%>
                </td>
                <%
                        sum += r.getRentCost();
                    }
                %>
            </tr>
        </table>
        <p>Сумма: <%=sum%>
        </p>
        <p>Доход: <%=sum - dto.getTax()%>
        </p>
        <br/>
        <div>
            <hr/>
            <br/>
            <br/>
            <hr/>
            <hr/>
        </div>
    </div>
</div>
</body>
</html>
