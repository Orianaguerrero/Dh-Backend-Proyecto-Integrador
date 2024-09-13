document.getElementById("add_new_turno").onsubmit=function(e) {
    e.preventDefault();
};

// POST
window.addEventListener('load', function () {

    const formulario = document.querySelector('#add_new_turno');
    formulario.addEventListener('submit', function (event) {

        const fechaTurno = new Date(document.querySelector('#fecha').value + ' ' + document.querySelector('#hora').value);
        const horas = fechaTurno.getHours();

        // Verificar que la hora esté dentro del rango permitido (08:00 a 18:00)
        if (horas < 8 || horas >= 18) {
            alert('Los turnos solo se pueden agendar entre las 08:00 y las 18:00 horas.');
            event.preventDefault();
            return;
        }


        const formData = {
            paciente: {
                id: document.querySelector('#paciente').value
            },
            odontologo: {
                id: document.querySelector('#odontologo').value
            },
            fecha: document.querySelector('#fecha').value,
            hora: document.querySelector('#hora').value
        };

        const url = 'http://localhost:8080/turnos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
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

                    let successAlert = '<div id="success-alert" class="alert alert-success alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert" ' +
                        'onclick="document.getElementById(\'success-alert\').classList.add(\'d-none\')">&times;</button>' +
                        '<strong> Turno registrado</strong> </div>'

                    document.querySelector('#response').innerHTML = successAlert;

                    document.querySelector('#response').style.display = "block";
                    resetUploadForm();

                    console.log('Turno registrado con éxito');
                }
            })
            .catch(error => {
                let error1 = "Status: 400 , Message: El";
                let error2 = "Status: 400 , Message: La fecha";
                let error3 = "Status: 400 , Message: La hora";

                let errorAlert = '';
                if (error.message.substring(0, error1.length) == error1) {
                    errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert" ' +
                        'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                        '<strong> Error al registrar turno (El odontólogo no está disponible en esa fecha y hora)</strong> </div>'

                } else if (error.message.substring(0, error2.length) === error2 || error.message.substring(0, error3.length) === error3) {
                    errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert" ' +
                        'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                        '<strong> Error al registrar turno (la fecha y hora deben ser futuras)</strong> </div>'
                } else {
                    errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert" ' +
                        'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                        '<strong> Error al registrar turno, intente nuevamente</strong> </div>'
                }

                document.querySelector('#response').innerHTML = errorAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();

                console.log('Error post turno:', error);
            })
    });

    function resetUploadForm(){
        document.querySelector('#paciente').value = "";
        document.querySelector('#odontologo').value = "";
        document.querySelector('#fecha').value = "";
        document.querySelector('#hora').value = "";
    }

});

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