function createJSON() {
    var object = {};
    var formData = new FormData(document.forms.newMeetingForm); //ссылаемся на форму
    //формируем объект из формы
    formData.forEach(function (value, key) {
        if (!key.includes("reminder")) {
            object[key] = value;
        }
    });
    if ("Yes" === formData.get("isReminderOn")) {
        object["reminder"] = {
            "value": formData.get("reminderValue"),
            "measurementUnit": formData.get("reminderMeasurementUnit")
        };
    }
    var json = JSON.stringify(object); //преобразуем в JSON-строку
    var request = new XMLHttpRequest();
    request.open("POST", "./createMeeting", true);
    request.responseType = "blob"; //blob - незименяемые, необработанные данные
    request.setRequestHeader("Content-Type", "application/json");
    //определение функции-обработчика ответа (не вызов!). Своеобразный handle, оболочка для помещения ответа.
    //эта функция будет вызвана после send() (или во время), но определена должна быть ДО, чтобы быть тут, когда
    //она понадобится xhr
    request.onload = function () {
        var blob = request.response; //получаем ответ сервера
        var link = document.createElement('a'); //создаём элемент
        link.href = window.URL.createObjectURL(blob); //привязываем на него URL ответа - сгенерированного сервером файла
         //назвать файл по summary из формы
        link.download = document.forms.newMeetingForm.elements.summary.value + ".ics";
        link.click(); //триггернуть нажатие
    }
    request.send(json);
    alert("JSON created / createJSON works");
    return false;
}