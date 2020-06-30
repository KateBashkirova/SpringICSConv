function sendJSON() {
    var object={};
    var formData = new FormData(document.forms.meetingForm); //ссылаемся на форму
    //формируем объект из формы
    formData.forEach(function (value,key) {
        object[key] = value;
    });
    var jsonStr = JSON.stringify(object); //преобразуем объект в JSON строку
    var request = new XMLHttpRequest();
    request.open("POST", "/createMeeting", false); //ассинхронный запрос на контроллер
    request.setRequestHeader("content-type", "application/json");
    request.send();
    //словить ошибку
    if (request.status != 200) {
        alert("Сломалось" + request.status + ': ' + request.statusText);
    }
}