document.getElementById("add_new_odontologo").onsubmit=function(e) {
    e.preventDefault();
};

// POST
window.addEventListener('load', function () {

    const formulario = document.querySelector('#add_new_odontologo');
    formulario.addEventListener('submit', function (event) {

        const formData = {
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            matricula: document.querySelector('#matricula').value
        };

        const url = 'http://localhost:8080/odontologos';
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
                        '<strong> Odontólogo registrado</strong> </div>'

                    document.querySelector('#response').innerHTML = successAlert;
                    document.querySelector('#response').style.display = "block";
                    resetUploadForm();

                    console.log('Odontólogo registrado con éxito');
                }
            })
            .catch(error => {

                let error1 = "Status: 409 , Message: DataIntegrityViolationException";

                let errorAlert = '';
                if (error.message === error1) {
                    errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert" ' +
                        'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                        '<strong> Error al registrar odontólogo (la matrícula ya existe)</strong> </div>'
                } else {
                    errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert" ' +
                        'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                        '<strong> Error al registrar odontólogo, intente nuevamente</strong> </div>'
                }

                document.querySelector('#response').innerHTML = errorAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();

                console.log('Error post odontólogo:', error);
            })
    });

    function resetUploadForm(){
        document.querySelector('#nombre').value = "";
        document.querySelector('#apellido').value = "";
        document.querySelector('#matricula').value = "";
    }

});