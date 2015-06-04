$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8090/api"
    }).then(function(data) {
        $('.greeting-id').append(data.id);
        $('.greeting-content').append(data.content);
    });
});