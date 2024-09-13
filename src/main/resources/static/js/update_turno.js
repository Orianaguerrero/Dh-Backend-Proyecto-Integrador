// PUT, GET (by ID)
window.addEventListener('load', function () {

    const formulario = document.querySelector('#update_turno_form');

    formulario.addEventListener('submit', function (event) {
        const formData = {
            id: document.querySelector('#turno_id').value,
            paciente: {
                id: document.querySelector('#paciente').value
            },
            odontologo: {
                id: document.querySelector('#odontologo').value
            },
            fecha: document.querySelector('#fecha').value,
            hora: document.querySelector('#hora').value,
        }

        const url = 'http://localhost:8080/turnos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url,settings)
            .then(response => response.json())
        /*.then(response => {
            if (!response.ok) {
                throw new Error(`Status: ${response.status} ${response.statusText}`);
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

                console.log('Turno actualizado con éxito');
            }
        })
        .catch(error => console.error('Error update turno:', error));*/
    })
})

function findBy(id) {

    const url = 'http://localhost:8080/turnos/' + id;
    const settings = {
        method: 'GET'
    }

    fetch(url,settings)
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

                let turno = data;
                document.querySelector('#turno_id').value = turno.id;
                document.querySelector('#paciente').value = turno.paciente.id;
                document.querySelector('#odontologo').value = turno.odontologo.id;
                document.querySelector('#fecha').value = turno.fecha;
                document.querySelector('#hora').value = turno.hora;

                document.querySelector('#div_turno_updating').style.display = "block";

                console.log('Turno consultado con éxito');
            }
        })
        .catch(error => {
            let errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                '<button type="button" class="close" data-dismiss="alert" ' +
                'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                '<strong> Error al obtener turno, intente nuevamente</strong> </div>'

            document.querySelector('#response').innerHTML = errorAlert;
            document.querySelector('#response').style.display = "block";

            console.error('Error fetching turno:', error);
        })
}

document.addEventListener('DOMContentLoaded', function() {
    fetch('http://localhost:8080/pacientes')
        .then(response => response.json())
        .then(data => {
            const selectPaciente = document.getElementById('paciente');
            data.forEach(paciente => {
                const option = document.createElement('option');
                option.value = paciente.id;
                option.textContent = `${paciente.nombre} ${paciente.apellido} (DNI: ${paciente.dni})`;
                selectPaciente.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching pacientes:', error));

    fetch('http://localhost:8080/odontologos')
        .then(response => response.json())
        .then(data => {
            const selectOdontologo = document.getElementById('odontologo');
            data.forEach(odontologo => {
                const option = document.createElement('option');
                option.value = odontologo.id;
                option.textContent = `${odontologo.nombre} ${odontologo.apellido} (MATRÍCULA: ${odontologo.matricula})`;
                selectOdontologo.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching odontólogos:', error));
});