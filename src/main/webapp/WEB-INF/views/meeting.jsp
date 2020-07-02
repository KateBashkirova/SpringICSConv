<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Meeting creating</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>
<body>
<script>
    var jsonStr;

    function createJSON() {
        var object={};
        var formData = new FormData(document.forms.meetingForm); //ссылаемся на форму
        //формируем объект из формы
        formData.forEach(function (value,key) {
            object[key] = value;
        });
        jsonStr = JSON.stringify(object);
    }

    <!--   function sendJSON(){
           $.ajax({
               type: "POST",
               url: "/createMeeting",
               contentType: "application/json; charset=utf-8",
               data: jsonStr,
               async: false,
               cache: false,
               processingData: false,
               success: alert("Всё гуд")
           })
       }-->
</script>
<div class="container-lg">
    <form:form name="meetingForm" method="GET" onsubmit="createJSON()">
        <div class="form-group">
            <label>Location</label>
            <input class="form-control" id="location" type="text"/>
        </div>
        <div class="form-group">
            <label>Summary</label>
            <input class="form-control" id="summary" type="text"/>
        </div>
        <div class="form-group">
            <label>Description</label>
            <input class="form-control" id="description" type="text"/>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form:form>
</div>
</body>
</html>