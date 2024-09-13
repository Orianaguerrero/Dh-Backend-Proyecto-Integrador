// GET (All)
window.addEventListener('load', function () {

    const url = 'http://localhost:8080/turnos';
    const settings = {
        method: 'GET'
    }

    fetch(url, settings)
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
                console.warn('No se encontraron turnos');

                let successAlert = '<div id="success-alert" class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert" ' +
                    'onclick="document.getElementById(\'success-alert\').classList.add(\'d-none\')">&times;</button>' +
                    '<strong> No se encontraron turnos</strong> </div>'

                document.querySelector('#table_response').innerHTML = successAlert;
                document.querySelector('#table_response').style.display = "block";
                return;
            } else {
                for (let turno of data) {
                    let table = document.getElementById("turnoTable");
                    let turnoRow = table.insertRow();
                    let tr_id = 'tr_' + turno.id;
                    turnoRow.id = tr_id;

                    let deleteButton = '<button' +
                        ' id=' + '\"' + 'btn_delete_' + turno.id + '\"' +
                        ' type="button" onclick="deleteBy('+turno.id+')" class="btn btn-danger btn_delete">' +
                        '&times' +
                        '</button>';

                    let updateButton = '<button' +
                        ' id=' + '\"' + 'btn_id_' + turno.id + '\"' +
                        ' type="button" onclick="findBy('+turno.id+')" class="btn btn-info btn_id">' +
                        turno.id +
                        '</button>';

                    let turno_paciente =
                        turno.paciente.nombre + ' ' +
                        turno.paciente.apellido + ' ' +
                        '(DNI: ' + turno.paciente.dni + ')';

                    let turno_odontologo =
                        turno.odontologo.nombre + ' ' +
                        turno.odontologo.apellido + ' ' +
                        '(MATRÍCULA: ' + turno.odontologo.matricula + ')';

                    turnoRow.innerHTML = '<td>' + updateButton + '</td>' +
                        '<td class=\"td_paciente\">' + turno_paciente + '</td>' +
                        '<td class=\"td_odontologo\">' + turno_odontologo + '</td>' +
                        '<td class=\"td_fecha\">' + turno.fecha + '</td>' +
                        '<td class=\"td_hora\">' + turno.hora + '</td>' +
                        '<td>' + deleteButton + '</td>';
                }

                console.log('Consulta de turnos exitosa');
            }
        })
        .catch(error => {
            let errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                '<button type="button" class="close" data-dismiss="alert" ' +
                'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                '<strong> Error al obtener turnos, intente nuevamente</strong> </div>'

            document.querySelector('#table_response').innerHTML = errorAlert;
            document.querySelector('#table_response').style.display = "block";

            console.log('Error fetching turnos:', error);
        })

});