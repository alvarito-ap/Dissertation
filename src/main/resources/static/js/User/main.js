var idProjectG;
var idTeenagerG;
$(document).ready(function () {
    $("#sidebar").mCustomScrollbar({
        theme: "minimal"
    });

    $('#sidebarCollapse').on('click', function () {
        $('#sidebar, #content').toggleClass('active');
        $('.collapse.in').toggleClass('in');
        $('a[aria-expanded=true]').attr('aria-expanded', 'false');
    });

    $("#ShowProjects").click(function () {
        loadProjects();
    });


    //$("#sidebarCollapse").trigger('click');
});

function loadProjects() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200){
            var projectsList = JSON.parse(this.responseText);
            var projects = "<div class=\"container\">";
            projects += "<div class=\"row\">";
            for (var i = 0; i<projectsList.length; i++){
                projects += "<div class=\"card m-4\" style=\"width: 18rem;\">" +
                    "  <div class=\"card-body\">" +
                    "    <h5 class=\"card-title\">"+projectsList[i].name+"</h5>" +
                    "    <h6 class=\"card-subtitle mb-2 text-muted\">\n</h6>" +
                    "    <p class=\"card-text\">"+projectsList[i].description+"</p>" +
                    "    <a href=\"#\" class=\"card-link\"><p id=\""+projectsList[i].id+"\" class=\"btn btn-secondary projectButton\">Visualizar proyecto</p></a>" +
                    "  </div>\n" +
                    "</div>";
            }
            projects += "</div>";
            projects += "</div>";
            $("#mainContent").html(projects);
            $(".projectButton").click(function () {
                loadProject(this.id);
            });
        }
    };
    xhttp.open("GET", "/User/projects", true);
    xhttp.send();
}

function loadProject(id) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200){
            var html = "<div class=\"container\">" +
                "<div class=\"row\">"+
                "<div id=\"nodeInfo\" class=\"col-xs-0 col-sm-0 col-md-0 col-lg-0\"></div>"+
                "<div class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12\">"+
                "<div id=\"graph-container\" style=\"height: 700px\"></div>" +
                "</div>"+
                "</div>"+
                "</div>";
            $("#mainContent").html(html);
            sigmaUtils(this.responseText, id);
            idProjectG = id;
        }
    };
    xhttp.open("GET", "/User/getProjectData?id="+id, true);
    xhttp.send();
}

function showOneTeenagerRelation(idProject, idPrimaryTeenager) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var html = "<div class=\"container\">" +
                "<div class=\"row text-center justify-content-center m-4 p-4\">" +
                "   <div class=\"col-xs-2 col-sm-2 col-md-2 col-lg-2\">" +
                "       <button id=\"generalGraph\" class=\"btn btn-danger\">Vista General</button> " +
                "   </div>" +
                "   <div class=\"col-xs-5 col-sm-5 col-md-5 col-lg-5\">" +
                "       <div class=\"btn-group\">" +
                "            <button type=\"button\" class=\"btn btn-danger dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
                "               Filtrar relacion companeros" +
                "           </button>" +
                "           <div class=\"dropdown-menu\">\n" +
                "                <button class=\"dropdown-item relation-filter\" id=\"all\">Todas</button>\n" +
                "                <button class=\"dropdown-item relation-filter\" id=\"Alguna vez comparto mi tiempo libre\">Alguna vez comparto mi tiempo libre</button>\n" +
                "                <button class=\"dropdown-item relation-filter\" id=\"Pasamos bastante tiempo libre juntos\">Pasamos bastante tiempo libre juntos</button>\n" +
                "                <button class=\"dropdown-item relation-filter\" id=\"Casi siempre estamos juntos\">Casi siempre estamos juntos</button>\n" +
                "                <button class=\"dropdown-item relation-filter\" id=\"Siempre estamos juntos\">Siempre estamos juntos</button>\n" +
                "           </div>" +
                "       </div>"+
                "   </div>"+
                "   <div class=\"col-xs-5 col-sm-5 col-md-5 col-lg-5\">" +
                "       <div class=\"btn-group\">" +
                "            <button type=\"button\" class=\"btn btn-danger dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
                "               Filtrar Relacion Egocentrica" +
                "           </button>" +
                "           <div class=\"dropdown-menu\">\n" +
                "                <button class=\"dropdown-item egocentric-relation-filter\" id=\"all\">Todas</button>\n" +
                "                <button class=\"dropdown-item egocentric-relation-filter\" id=\"Comparto tiempo fuera de clase\">Comparto tiempo fuera de clase</button>\n" +
                "                <button class=\"dropdown-item egocentric-relation-filter\" id=\"Consumo fuera de clase\">Consumo fuera de clase</button>\n" +
                "                <button class=\"dropdown-item egocentric-relation-filter\" id=\"Algunas personas mas con las que consumo\">Algunas personas mas con las que consumo</button>\n" +
                "           </div>" +
                "       </div>"+
                "   </div>"+
                "</div>"+
                "<div class=\"row\">"+
                "<div id=\"nodeInfo\" class=\"col-xs-2 col-sm-2 col-md-2 col-lg-2\"></div>"+
                "<div class=\"col-xs-10 col-sm-10 col-md-10 col-lg-10\">"+
                "<div id=\"graph-container\" style=\"height: 700px\"></div>" +
                "</div>"+
                "</div>"+
                "</div>";
            $("#mainContent").html(html);
            sigmaUtils(this.responseText, idProject);
            nodeInformation(idPrimaryTeenager);
            $(".relation-filter").click(function(e){
                filterClass($(this).attr("id"));
            });
            $(".egocentric-relation-filter").click(function(e){
                showEgocentric($(this).attr("id"));
            });
            $("#generalGraph").click(function () {
                loadProject(idProject);
            });
            idProjectG = idProject;
            idTeenagerG = idPrimaryTeenager;
        }
    };
    xhttp.open("GET", "/User/getProjectDataTeenager?idProject="+idProject+"&idTeenager="+idPrimaryTeenager, true);
    xhttp.send();
}

function nodeInformation(idNode) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200){
            var info = JSON.parse(xhttp.responseText);
            var content = "<h4>Node Information</h4>"+
                "<p><h6>Nombre</h6>"+info['name']+"</p>"+
                "<p><h6>Apellidos</h6>"+info['surName']+"</p>"+
                "<p><h6>Lugar de nacimiento</h6>"+info['placeBirth']+"</p>"+
                "<p><h6>Nombre</h6>"+info['name']+"</p>"+
                "<p><h6>Genero</h6>"+info['gender']+"</p>"+
                "<p><h6>Instituto</h6>"+info['institute']+"</p>"+
                "<p><h6>Grupo</h6>"+info['groupe']+"</p>";
            $("#nodeInfo").html(content);
        }
    };
    xhttp.open("GET", "/User/getNodeInfo?idNode="+idNode, true);
    xhttp.send();
}

function filterClass(filter) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200){
            sigmaUtils(this.responseText, idProjectG);
        }
    };
    xhttp.open("GET", "/User/filterNodeClass?idProject="+idProjectG+"&idNode="+idTeenagerG+"&filter="+filter, true);
    xhttp.send();
}

function showEgocentric(type) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200){
            sigmaUtils(this.responseText, idProjectG);
        }
    };
    xhttp.open("GET", "/User/egocentricRelation?idProject="+idProjectG+"&idNode="+idTeenagerG+"&type="+type, true);
    xhttp.send();
}

function  updateNodePosition(idProject, idTeenager, x, y) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200){
        }
    };
    xhttp.open("GET", "/User/updateNodePosition?idProject="+idProject+"&idNode="+idTeenager+"&x="+x+"&y="+y, true);
    xhttp.send();
}

function sigmaUtils(json, id) {
    $("#graph-container").html("");
    var s = new sigma({
        graph: JSON.parse(json),
        renderer: {
            container: document.getElementById('graph-container'),
            type: 'canvas'
        },
        settings: {
            edgeLabels: true,
            minEdgeSize: 30,
            minArrowSize: 8,
            minNodeSize: 8,
            maxNodeSize: 30
        }
    });
    CustomShapes.init(s);
    s.refresh();
    s.bind('doubleClickNode', function(e) {
        showOneTeenagerRelation(id, e.data.node.id);
    });
    // Initialize the dragNodes plugin:
    var dragListener = sigma.plugins.dragNodes(s, s.renderers[0]);

    dragListener.bind('startdrag', function(event) {
        console.log(event);
    });
    dragListener.bind('drag', function(event) {
        console.log(event);
    });
    dragListener.bind('drop', function(event) {
        console.log(event);
        updateNodePosition(id ,event.data.node.id, event.data.node.x, event.data.node.y)
    });
    dragListener.bind('dragend', function(event) {
        console.log(event);
    });
}