// PUT, GET (by ID)
window.addEventListener('load', function () {

    const formulario = document.querySelector('#update_paciente_form');

    formulario.addEventListener('submit', function (event) {
        const formData = {
            id: document.querySelector('#paciente_id').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            domicilio: {
                id: document.querySelector('#domicilio_id').value,
                calle: document.querySelector('#domicilio_calle').value,
                numero: document.querySelector('#domicilio_numero').value,
                localidad: document.querySelector('#domicilio_localidad').value,
                provincia: document.querySelector('#domicilio_provincia').value,
            },
            dni: document.querySelector('#dni').value,
            fechaAlta: document.querySelector('#fecha_alta').value,
        }

        const url = 'http://localhost:8080/pacientes';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url,settings)
            .then(response => response.json())
    })
})

function findBy(id) {

    const url = 'http://localhost:8080/pacientes/' + id;
    const settings = {
        method: 'GET'
    }

    fetch(url,settings)
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

                let paciente = data;
                document.querySelector('#paciente_id').value = paciente.id;
                document.querySelector('#nombre').value = paciente.nombre;
                document.querySelector('#apellido').value = paciente.apellido;
                document.querySelector('#domicilio_id').value = paciente.domicilio.id;
                document.querySelector('#domicilio_calle').value = paciente.domicilio.calle;
                document.querySelector('#domicilio_numero').value = paciente.domicilio.numero;
                document.querySelector('#domicilio_localidad').value = paciente.domicilio.localidad;
                document.querySelector('#domicilio_provincia').value = paciente.domicilio.provincia;
                document.querySelector('#dni').value = paciente.dni;
                document.querySelector('#fecha_alta').value = paciente.fechaAlta;

                document.querySelector('#div_paciente_updating').style.display = "block";

                console.log('Paciente consultado con éxito');
            }
        })
        .catch(error => {
            let errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                '<button type="button" class="close" data-dismiss="alert" ' +
                'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                '<strong> Error al obtener paciente, intente nuevamente</strong> </div>'

            document.querySelector('#response').innerHTML = errorAlert;
            document.querySelector('#response').style.display = "block";

            console.error('Error fetching paciente:', error);
        })
}