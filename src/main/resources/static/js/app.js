var app = (function () {
    var author = null;
    var blueprints = [];

    var api = apimock;

    var changeAuthor = function (newAuthor) {
        author = newAuthor;
        updateBlueprints();
    };

    var updateBlueprints = function () {
        api.getBlueprintsByAuthor(author, function (data) {
            blueprints = data.map(function (bp) {
                return {
                    name: bp.name,
                    points: bp.points.length
                };
            });

            $("#blueprintsTable tbody").empty();

            blueprints.map(function (bp) {
                $("#blueprintsTable tbody").append(
                    `<tr>
                        <td>${bp.name}</td>
                        <td>${bp.points}</td>
                        <td><button onclick="app.drawBlueprint('${author}','${bp.name}')">Open</button></td>
                    </tr>`
                );
            });

            var totalPoints = data.reduce(function (sum, bp) {
                return sum + bp.points.length;
            }, 0);
            $("#totalPoints").text(totalPoints);
        });
    };

    var drawBlueprint = function(author, bpname){
        api.getBlueprintsByNameAndAuthor(author, bpname, function(blueprint){
            var canvas = document.getElementById("blueprintCanvas");
            var ctx = canvas.getContext("2d");
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            if (blueprint && blueprint.points.length > 0) {
                ctx.beginPath();
                ctx.moveTo(blueprint.points[0].x, blueprint.points[0].y);
                for (let i = 1; i < blueprint.points.length; i++) {
                    ctx.lineTo(blueprint.points[i].x, blueprint.points[i].y);
                }
                ctx.stroke();
            }
        });
    };


    return {
        changeAuthor: changeAuthor,
        drawBlueprint: drawBlueprint
    };
})();
