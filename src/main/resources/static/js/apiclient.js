//@author

apiclient = (function () {

    var getBlueprintsByAuthor = function (authname, callback) {
        fetch("http://localhost:8080/blueprints/" + authname)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error en la consulta de autor " + authname);
                }
                return response.json();
            })
            .then(data => callback(data))
            .catch(error => console.error("Error:", error));
    };

    var getBlueprintsByNameAndAuthor = function (authname, bpname, callback) {
        fetch("http://localhost:8080/blueprints/" + authname + "/" + bpname)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error en la consulta del plano " + bpname);
                }
                return response.json();
            })
            .then(data => callback(data))
            .catch(error => console.error("Error:", error));
    };

    return {
        getBlueprintsByAuthor: getBlueprintsByAuthor,
        getBlueprintsByNameAndAuthor: getBlueprintsByNameAndAuthor
    };

})();
