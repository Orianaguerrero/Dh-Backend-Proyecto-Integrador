// DELETE
function deleteBy(id) {
    const url = 'http://localhost:8080/pacientes/'+ id;
    const settings = {
        method: 'DELETE'
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

            let successAlert = '<div id="success-alert" class="alert alert-success alert-dismissible">' +
                '<button type="button" class="close" data-dismiss="alert" ' +
                'onclick="document.getElementById(\'success-alert\').classList.add(\'d-none\')">&times;</button>' +
                '<strong> Paciente eliminado con éxito</strong> </div>'

            document.querySelector('#table_response').innerHTML = successAlert;
            document.querySelector('#table_response').style.display = "block";

            let row_id = "#tr_" + id;
            document.querySelector(row_id).remove();

            console.log("Paciente eliminado con éxito");
        })
        .catch(error => {
            let errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                '<button type="button" class="close" data-dismiss="alert" ' +
                'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                '<strong> Error al eliminar paciente, tiene turnos registrados</strong> </div>'

            document.querySelector('#table_response').innerHTML = errorAlert;
            document.querySelector('#table_response').style.display = "block";

            console.error('Error delete paciente:', error);
        })

}