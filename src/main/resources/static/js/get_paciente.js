// GET (All)
window.addEventListener('load', function () {

        const url = 'http://localhost:8080/pacientes';
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
                    console.warn('No se encontraron pacientes');

                    let successAlert = '<div id="success-alert" class="alert alert-success alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert" ' +
                        'onclick="document.getElementById(\'success-alert\').classList.add(\'d-none\')">&times;</button>' +
                        '<strong> No se encontraron pacientes</strong> </div>'

                    document.querySelector('#table_response').innerHTML = successAlert;
                    document.querySelector('#table_response').style.display = "block";
                    return;
                } else {
                    for (let paciente of data) {
                        let table = document.getElementById("pacienteTable");
                        let pacienteRow = table.insertRow();
                        let tr_id = 'tr_' + paciente.id;
                        pacienteRow.id = tr_id;

                        let deleteButton = '<button' +
                            ' id=' + '\"' + 'btn_delete_' + paciente.id + '\"' +
                            ' type="button" onclick="deleteBy(' + paciente.id + ')" class="btn btn-danger btn_delete">' +
                            '&times' +
                            '</button>';

                        let updateButton = '<button' +
                            ' id=' + '\"' + 'btn_id_' + paciente.id + '\"' +
                            ' type="button" onclick="findBy(' + paciente.id + ')" class="btn btn-info btn_id">' +
                            paciente.id +
                            '</button>';

                        let pacienteDomicilio =
                            paciente.domicilio.calle + ' ' +
                            paciente.domicilio.numero + ' ' +
                            paciente.domicilio.localidad + ' ' +
                            paciente.domicilio.provincia;

                        pacienteRow.innerHTML = '<td>' + updateButton + '</td>' +
                            '<td class=\"td_nombre\">' + paciente.nombre + '</td>' +
                            '<td class=\"td_apellido\">' + paciente.apellido + '</td>' +
                            '<td class=\"td_domicilio\">' + pacienteDomicilio + '</td>' +
                            '<td class=\"td_dni\">' + paciente.dni + '</td>' +
                            '<td class=\"td_fecha_alta\">' + paciente.fechaAlta + '</td>' +
                            '<td>' + deleteButton + '</td>';
                    }

                    console.log('Consulta de pacientes exitosa');
                }
            })
            .catch(error => {
                let errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert" ' +
                    'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                    '<strong> Error al obtener pacientes, intente nuevamente</strong> </div>'

                document.querySelector('#table_response').innerHTML = errorAlert;
                document.querySelector('#table_response').style.display = "block";

                //alert(`Error fetching pacientes: ${error.message}`);
                console.log('Error fetching pacientes:', error);
            })

});