var lista = document.getElementById("list");
var conf = document.getElementById("conf");


function showConferma() {
    lista.style.display = "none";
    conf.style.display = "flex";
}

function hideConferma() {
    lista.style.display = "flex";
    conf.style.display = "none";
}

document.getElementById('cambiaImmagine').addEventListener('click', function() {
    document.getElementById('profileImageInput').click();
});

function previewImage(event) {
    const reader = new FileReader();
    reader.onload = function() {
        const output = document.getElementById('profileImagePreview');
        output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
}


