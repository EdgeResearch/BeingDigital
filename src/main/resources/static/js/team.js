function cambiaStile(opzione) {


    if (opzione === "gruppo") {
        document.getElementById('Group').style.display = 'flex';
        document.getElementById('Group').style.flexDirection = 'column';
        document.getElementById('Classe').style.display = 'none';
        document.getElementById('confirmTeam').style.display = 'block';
    } else if (opzione === "classe") {
        document.getElementById('Group').style.display = 'none';
        document.getElementById('Classe').style.display = 'flex';
        document.getElementById('Classe').style.flexDirection = 'column';
        document.getElementById('confirmTeam').style.display = 'block';
    }
}