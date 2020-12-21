$(document).ready(function(){
    $("#sidebar").mCustomScrollbar({
        theme: "minimal"
    });

    $('#sidebarCollapse').on('click', function () {
        $('#sidebar, #content').toggleClass('active');
        $('.collapse.in').toggleClass('in');
        $('a[aria-expanded=true]').attr('aria-expanded', 'false');
    });

    $("#ShowUsers").click(function () {
        loadUsers();
    });
});

function loadUsers() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var users = JSON.parse(this.responseText);
            if($("#usersList").length){
                $("#usersList").html("");
            }else{
                $("#content").append("<div id=\"usersList\">" +
                    "</div>");
            }
            var usersList = "<div class=\"container\">";
            usersList += "<div class=\"row justify-content-center\">";
            for(i = 0; i < users.length; i++){
                usersList += "<div class=\"card m-4\" style=\"width: 18rem;\">" +
                    "  <div class=\"card-body\">" +
                    "    <h5 class=\"card-title\">"+users[i].username+"</h5>" +
                    "    <h6 class=\"card-subtitle mb-2 text-muted\">"+users[i].name+"</h6>" +
                    "    <p class=\"card-text\">Estado "+users[i].status+"</p>" +
                    "    <a href=\"#\" class=\"card-link\">Another link</a>" +
                    "  </div>\n" +
                    "</div>";
            }
            usersList += "</div>";
            usersList += "</div>";
            $("#usersList").html(usersList);
        }
    };
    xhttp.open("GET", "/Admin/usersInfo", true);
    xhttp.send();
}