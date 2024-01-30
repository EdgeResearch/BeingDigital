var container = document.querySelector("#unity-container");
var canvas = document.querySelector("#unity-canvas");
var loadingBar = document.querySelector("#unity-loading-bar");
var progressBarFull = document.querySelector("#unity-progress-bar-full");
var fullscreenButton = document.querySelector("#unity-fullscreen-button");
var warningBanner = document.querySelector("#unity-warning");

// Shows a temporary message banner/ribbon for a few seconds, or
// a permanent error message on top of the canvas if type=='error'.
// If type=='warning', a yellow highlight color is used.
// Modify or remove this function to customize the visually presented
// way that non-critical warnings and error messages are presented to the
// user.
function unityShowBanner(msg, type) {
    function updateBannerVisibility() {
        warningBanner.style.display = warningBanner.children.length ? 'block' : 'none';
    }

    var div = document.createElement('div');
    div.innerHTML = msg;
    warningBanner.appendChild(div);
    if (type == 'error') div.style = 'background: red; padding: 10px;';
    else {
        if (type == 'warning') div.style = 'background: yellow; padding: 10px;';
        setTimeout(function () {
            warningBanner.removeChild(div);
            updateBannerVisibility();
        }, 5000);
    }
    updateBannerVisibility();
}

var _script;
var _unityInstance;

function addGame(gioco) {

    if (_unityInstance != null) {
        _unityInstance.Quit();
        document.body.removeChild(_script);
    }

    var titolo = document.getElementById('giocoTitolo');
    titolo.innerText = gioco.innerText;
    console.log("Ho fatto " + titolo.innerText);
    var buildUrl = gioco.id;
    var loaderUrl = buildUrl + "/Build.loader.js";
    var config = {
        dataUrl: buildUrl + "/Build.data.unityweb",
        frameworkUrl: buildUrl + "/Build.framework.js.unityweb",
        codeUrl: buildUrl + "/Build.wasm.unityweb",
        streamingAssetsUrl: "StreamingAssets",
        companyName: "Unisa",
        productName: gioco.innerText,
        productVersion: "1.0",
        showBanner: unityShowBanner,
    };


    loadingBar.style.display = "block";

    _script = document.createElement("script");
    _script.src = loaderUrl;
    _script.onload = () => {
        createUnityInstance(canvas, config, (progress) => {
            progressBarFull.style.width = 100 * progress + "%";
        }).then((unityInstance) => {
            loadingBar.style.display = "none";
            fullscreenButton.onclick = () => {
                unityInstance.SetFullscreen(1);
            };
            _unityInstance = unityInstance;
        }).catch((message) => {
            alert(message);
        });
    };

    document.body.appendChild(_script);
}
