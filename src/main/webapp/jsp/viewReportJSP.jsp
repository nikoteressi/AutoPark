<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="application.dto.VehicleDto" %>
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
        %>
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
                <th>Доход с аренд</th>
                <th>Налог</th>
                <th>Итог</th>
            </tr>
            <tr>
                <%
                    double taxSum = 0;
                    double incomeSum = 0;
                    double total = 0;
                    for (VehicleDto dto : dtoList) {
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
                <td><%=dto.getIncome()%>
                </td>
                <td><%=dto.getTax()%>
                </td>
                <td><%=dto.getIncome() - dto.getTax()%>
                </td>
                <%
                        taxSum += dto.getTax();
                        incomeSum += dto.getIncome();
                        total += dto.getIncome() - dto.getTax();
                    }
                %>
            </tr>
        </table>
        <p>Средний налог за месяц: <%=taxSum / dtoList.size()%>
        </p>
        <p>Средний доход за месяц: <%=incomeSum / dtoList.size()%>
        </p>
        <p>Доход автопарка: <%=total%>
        </p>
    </div>
</div>
</body>
</html>
