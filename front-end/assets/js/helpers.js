function callListValidations() {
  validButtonLinkUpload();
  validButtonFile();
}

async function downloadFiles(id) {
  let files = await getFiles(id);

  const zip = new JSZip();

  files.forEach((element) => {
    const fileName = element.name;
    const decodedContent = atob(element.file);
    const bytes = new Uint8Array(decodedContent.length);

    for (let i = 0; i < decodedContent.length; i++) {
      bytes[i] = decodedContent.charCodeAt(i);
    }

    zip.file(fileName, new Blob([bytes], { type: "application/octet-stream" }));
  });

  zip.generateAsync({ type: "blob" }).then(function (content) {
    const url = URL.createObjectURL(content);

    const link = document.createElement("a");
    link.href = url;
    link.download = "files.zip";
    link.click();

    URL.revokeObjectURL(url);
  });

  redirectPage();
}

const fileToBase64 = (file) =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = (error) => reject(error);
  });

function generateLinkDownload(id) {
  linkDownload = `${window.location.href}?id=${id}`;
  let elementLink = document.getElementById("link-download");
  elementLink.href = linkDownload;
  elementLink.textContent = linkDownload;
}

function getFiles(id) {
  return fetch(`${URL_API}/${id}`)
    .then((dataset) => {
      return dataset.json();
    })
    .catch((error) => {
      console.log(error);
      return;
    });
}

function hasIdParam(param) {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.has(param);
}

function hiddenLoading() {
  document.getElementById("modal-loading").classList.remove("d-block");
  document.getElementById("modal-loading").classList.add("d-none");
}

function hiddenCardUploadSucess() {
  let valueLink = document.getElementById("link-download").textContent;
  navigator.clipboard.writeText(valueLink);
  document.getElementById("modal-success").classList.remove("show");
  document.getElementById("modal-success").classList.remove("d-block");
}

function showLoading() {
  document.getElementById("modal-loading").classList.remove("d-none");
  document.getElementById("modal-loading").classList.add("d-block");
}

function showCardUploadSucess() {
  document.getElementById("modal-success").classList.add("show");
  document.getElementById("modal-success").classList.add("d-block");
}

function sendFiles() {
  showLoading();
  return fetch(URL_API, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(listFiles),
    mode: "cors",
  })
    .then((dataset) => {
      hiddenLoading();
      return dataset.json();
    })
    .catch((error) => {
      hiddenLoading();
      console.log(error);
      return;
    });
}

function validButtonLinkUpload() {
  let buttonUpload = document.getElementById("link-upload");
  listFiles.length == 0
    ? (buttonUpload.disabled = true)
    : (buttonUpload.disabled = false);
}

function validButtonFile() {
  let file = document.getElementById("file-container");
  listFiles.length < 5
    ? file.classList.remove("d-none")
    : file.classList.add("d-none");
}

function resetPage() {
  clearListTemplate();
  listFiles = [];
  document.getElementById("arquivo").value = "";
}

function redirectPage(){
  const url = new URL(window.location.href);
  window.location.href = `${url.origin}${url.pathname}`;
}