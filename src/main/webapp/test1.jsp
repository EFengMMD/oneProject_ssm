<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + "/" + request.getContextPath() + "/";
%>
<html>
<head>
    <title></title>
    <base href="<%=basePath%>"/>
</head>
<body>


$.ajax({
url : "",
data : {

},
type : "",
dataType : "json",
success : function(data){

}
})


$(".time").datetimepicker({
minView: "month",
language:  'zh-CN',
format: 'yyyy-mm-dd',
autoclose: true,
todayBtn: true,
pickerPosition: "bottom-left"
});


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</body>
</html>

