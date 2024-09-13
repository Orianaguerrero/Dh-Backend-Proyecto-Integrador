// GET (All)
window.addEventListener('load', function () {

        const url = 'http://localhost:8080/odontologos';
        const settings = {
            method: 'GET'
        }

        fetch(url, settings)
            //.then(response => response.json())
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorMessage => {
                        throw new Error(`Status: ${response.status} ${response.statusText}, Message: ${errorMessage}`);
                    });
                }

                const contentType = response.headers.get('content-type');
                if (contentType && contentType.includes('application/json')) {
                    return response.json();
                } else {
                    return response.text();
                }
            })
            .then(data => {
                if (typeof data === 'string') {
                    console.log('Response text:', data);
                } else {
                    console.log('Response json:', data);
                }

                if (!data || data.length === 0) {
                    console.warn('No se encontraron odontólogos');

                    let successAlert = '<div id="success-alert" class="alert alert-success alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert" ' +
                        'onclick="document.getElementById(\'success-alert\').classList.add(\'d-none\')">&times;</button>' +
                        '<strong> No se encontraron odontólogos</strong> </div>'

                    document.querySelector('#table_response').innerHTML = successAlert;
                    document.querySelector('#table_response').style.display = "block";
                    return;
                } else {
                    for (let odontologo of data) {
                        let table = document.getElementById("odontologoTable");
                        let odontologoRow = table.insertRow();
                        let tr_id = 'tr_' + odontologo.id;
                        odontologoRow.id = tr_id;

                        let deleteButton = '<button' +
                            ' id=' + '\"' + 'btn_delete_' + odontologo.id + '\"' +
                            ' type="button" onclick="deleteBy('+odontologo.id+')" class="btn btn-danger btn_delete">' +
                            '&times' +
                            '</button>';

                        let updateButton = '<button' +
                            ' id=' + '\"' + 'btn_id_' + odontologo.id + '\"' +
                            ' type="button" onclick="findBy('+odontologo.id+')" class="btn btn-info btn_id">' +
                            odontologo.id +
                            '</button>';

                        odontologoRow.innerHTML = '<td>' + updateButton + '</td>' +
                            '<td class=\"td_nombre\">' + odontologo.nombre + '</td>' +
                            '<td class=\"td_apellido\">' + odontologo.apellido + '</td>' +
                            '<td class=\"td_matricula\">' + odontologo.matricula + '</td>' +
                            '<td>' + deleteButton + '</td>';
                    }

                    console.log('Consulta de odontólogos exitosa');
                }
            })
            .catch(error => {
                let errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert" ' +
                    'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                    '<strong> Error al obtener odontólogos, intente nuevamente</strong> </div>'

                document.querySelector('#table_response').innerHTML = errorAlert;
                document.querySelector('#table_response').style.display = "block";

                console.log('Error fetching odontólogos:', error);
            })

});