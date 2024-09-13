// PUT, GET (by ID)
window.addEventListener('load', function () {

    const formulario = document.querySelector('#update_odontologo_form');

    formulario.addEventListener('submit', function (event) {
        const formData = {
            id: document.querySelector('#odontologo_id').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            matricula: document.querySelector('#matricula').value,
        }

        const url = 'http://localhost:8080/odontologos';
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

    const url = 'http://localhost:8080/odontologos/' + id;
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

                let odontologo = data;
                document.querySelector('#odontologo_id').value = odontologo.id;
                document.querySelector('#nombre').value = odontologo.nombre;
                document.querySelector('#apellido').value = odontologo.apellido;
                document.querySelector('#matricula').value = odontologo.matricula;

                document.querySelector('#div_odontologo_updating').style.display = "block";

                console.log('Odontólogo consultado con éxito');
            }
        })
        .catch(error => {
            let errorAlert = '<div id="error-alert" class="alert alert-danger alert-dismissible">' +
                '<button type="button" class="close" data-dismiss="alert" ' +
                'onclick="document.getElementById(\'error-alert\').classList.add(\'d-none\')">&times;</button>' +
                '<strong> Error al obtener odontólogo, intente nuevamente</strong> </div>'

            document.querySelector('#response').innerHTML = errorAlert;
            document.querySelector('#response').style.display = "block";

            console.error('Error fetching odontólogo:', error);
        })
}