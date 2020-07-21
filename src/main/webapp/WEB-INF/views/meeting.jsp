<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Meeting creating</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>

<body>
<div class="container-lg">
    <form method="POST" action="/json_war/createMeeting" modelAttribute="meeting">
        <div class="form-group">
            <label for="location">Location</label>
            <form:input path="location" class="form-control" id="location" type="text" required=""/>
        </div>
        <div class="form-group">
            <label for="summary">Summary</label>
            <form:input path="summary" class="form-control" id="summary" type="text"/>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <form:input path="description" class="form-control" id="description" type="text"/>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>