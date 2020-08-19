<html lang="en">
<head>
    <title>Create new meeting</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<%--    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/script.js"></script>--%>
<%--    <script type="text/css" src="${pageContext.request.contextPath}/resources/static/main.css"></script>--%>
</head>
<body>
<script>
    var jsonStr;
    function createJSON() {
        var object = {};
        var formData = new FormData(document.forms.newMeetingForm); //ссылаемся на форму
        //формируем объект из формы
        formData.forEach(function (value, key) {
            object[key] = value;
        });
        jsonStr = JSON.stringify(object); //преобразуем в JSON-строку
        var json = JSON.stringify(object); //преобразуем в JSON-строку
        var request = new XMLHttpRequest();
        request.open("POST","./createMeeting",true);
        request.responseType = "blob";
        request.setRequestHeader("Content-Type", "application/json");
        request.onload = function() {
            var blob = request.response;
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = 'meeting.ics';
            link.click();
        }
        request.send(json);
        alert("JSON created / createJSON works");
        return false;
    //    $.ajax({
    //         type: 'POST',
    //         url: './createMeeting',
    //         data: jsonStr,
    //         contentType: "application/json; charset=utf-8",
    //         dataType: "json",
    //         async: false,
    //         cache: false,
    //         onData: function(data) {
    //             alert("ajax works");
    //             location = data;
    //         }
    // });
    }
</script>
<div class="container-xl">
    <form name="newMeetingForm" method="POST" onsubmit="createJSON(); return false;">
        <label style="font-size: 20px;">New Meeting</label>
        <div class="form-group">
            <label for="summary" style="color: aqua;">Event title</label>
            <input name="summary" type="text" class="form-control" id="summary" required>
            <br>
            <label for="location" id="locationLb">Location</label>
            <input name="location" type="text" class="form-control" id="location">
            <br>
            <label for="description">Description</label>
            <textarea name="description" class="form-control" id="description" rows="5"></textarea>
            <br>
            <label for="selectTimezone">Select your timezone</label>
            <select name="timezone" id="selectTimezone" class="form-control">
                <option selected>+06:00 Asia/Omsk</option>
                <option>+00:00 UTC</option>
            </select>
            <br>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="startDate">Start date</label>
                    <small id="startDateHelp" class="form-text text-muted">Enter data as DD.MM.YYYY</small>
                    <input name="startDate" type="date" class="form-control" id="startDate" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="startTime">Start time</label>
                    <small id="startTimeHelp" class="form-text text-muted">Enter data as HH:MM</small>
                    <input name="startTime" type="time" class="form-control" id="startTime" pattern="(0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){2}" required>
                </div>
            </div>
            <br>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="endDate">End date</label>
                    <small id="endDateHelp" class="form-text text-muted">Enter data as DD.MM.YYYY</small>
                    <input name="endDate" type="date" class="form-control" id="endDate" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="endTime">End time</label>
                    <small id="endTimeHelp" class="form-text text-muted">Enter data as HH:MM</small>
                    <input name="endTime" type="time" class="form-control" id="endTime" pattern="(0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){2}" required>
                </div>
            </div>
            <br>
            <label for="selectReminder">Reminder</label>
            <select name="reminder" id="selectReminder" class="form-control">
                <option>None</option>
                <option selected>At time of event</option>
                <option>5 minutes before event</option>
                <option>15 minutes before event</option>
                <option>30 minutes before event</option>
                <option>1 hour before event</option>
                <option>1 day before event</option>
                <option>1 week before event</option>
                <!--<option>Custom</option>-->
            </select>
            <br>
            <label for="selectEventStatus">Event Status</label>
            <a href="#" data-target="#eventStatusModal" data-toggle="modal">(what's this?)</a>
            <!-- Modal -->
            <div class="modal fade" id="eventStatusModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="eventStatusModalLabel">Event status</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p>Event status is the characteristic of an event that determines whether it appears to consume time on a calendar.</p>
                            <p align="center">For all users</p>
                            <p>Choose <b>Busy</b> if this event consumes actual time in your schedule. Time of the event will be blocked in your calendar and you will not be able to schedule
                                another event at this time interval.</p>
                            <p>Choose <b>Free</b> if you don't want to block time of this event. You will be able to schedule another events at this time interval.</p>
                            <p align="center">For Microsoft Outlook users only</p>
                            <p>Choose <b>Tentative</b> if you are not sure whether the event will take place or weather you will be a part of it.</p>
                            <p>Choose <b>Out Of Office</b> if you will not be in office at time of the event.</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-dismiss="modal">Got it</button>
                        </div>
                    </div>
                </div>
            </div>
            <select name="eventStatus" id="selectEventStatus" class="form-control">
                <option selected>Free</option>
                <option>Busy</option>
                <option>Tentative</option>
                <option>Out Of Office</option>
            </select>
        </div>
        <button id="submit" type="submit" class="btn btn-primary">Create ics file</button>
    </form>
</div>
</body>
</html>