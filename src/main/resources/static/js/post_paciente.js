document.getElementById("add_new_paciente").onsubmit=function(e) {
    e.preventDefault();
};

// POST
window.addEventListener('load', function () {

    const today = new Date();

    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Meses son 0-indexados
    const day = String(today.getDate()).padStart(2, '0');

    const localDate = `${year}-${month}-${day}`;
    document.getElementById('fecha_alta').value = localDate;

    const formulario = document.querySelector('#add_new_paciente');
    formulario.addEventListener('submit', function (event) {

        const formData = {
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            domicilio: {
                calle: document.querySelector('#domicilio_calle').value,
                numero: document.querySelector('#domicilio_numero').value,
                localidad: document.querySelector('#domicilio_localidad').value,
                provincia: document.querySelector('#domicilio_provincia').value,
            },
            dni: document.querySelector('#dni').value,
            fechaAlta: document.querySelector('#fecha_alta').value
        };

        const url = 'http://localhost:8080/pacientes';
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
                        '<strong> Paciente registrado</strong> </div>'

                    document.querySelector('#response').innerHTML = successAlert;
                    document.querySelector('#response').style.display = "block";
                    resetUploadForm();

                    console.log('Paciente registrado con éxito');
                }
            })
            .catch(error => {
                let error1 = "Status: 409 , Message: DataIntegrityViolationException";

                let errorAlert = '';
                if (error.message === error1) {
                    errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert" ' +
                        'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                        '<strong> Error al registrar paciente (el DNI ya existe)</strong> </div>'
                } else {
                    errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert" ' +
                        'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                        '<strong> Error al registrar paciente, intente nuevamente</strong> </div>'
                }

                document.querySelector('#response').innerHTML = errorAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();

                console.log('Error post paciente:', error);
            })
    });

    function resetUploadForm(){
        document.querySelector('#nombre').value = "";
        document.querySelector('#apellido').value = "";
        document.querySelector('#domicilio_calle').value = "";
        document.querySelector('#domicilio_numero').value = "";
        document.querySelector('#domicilio_localidad').value = "";
        document.querySelector('#domicilio_provincia').value = "";
        document.querySelector('#dni').value = "";
        document.querySelector('#fecha_alta').value = "";

        document.getElementById('fecha_alta').value = localDate;
    }

});