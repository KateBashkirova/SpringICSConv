function createJSON() {
    var object={};
    var formData = new FormData(document.forms.newMeetingForm); //ссылаемся на форму
    //формируем объект из формы
    formData.forEach(function (value,key) {
        object[key] = value;
    });
    var jsonStr = JSON.stringify(object);
}

/*function sendJSON(){
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
}*/
