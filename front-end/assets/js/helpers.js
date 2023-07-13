function isValidFile(file) {
  if (!file) {
    return false;
  }

  if (!isFileSizeValid(file.size)) {
    alert("O tamanho total dos arquivos ultrapassa o limite de 5 MB");
    resetInputFile();
    return false;
  }

  return true;
}

async function isValidAction(){
  const radioSelecionado = document.querySelector('input[name="flexRadioDefault"]:checked');
  const emailValue = document.getElementById("email-value").value;

  if (radioSelecionado.id == "radio-email" && !emailValue){
    alert("Informe para qual e-mail o link deverá ser enviado.")
    return false;
  }
  return true;
}

function clipBoard(conteudo){
  let textArea = document.createElement("textarea");
  textArea.value = conteudo;
  document.body.appendChild(textArea);
  textArea.select();
  document.execCommand('copy');
  document.body.removeChild(textArea);
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

  await zip.generateAsync({ type: "blob" }).then(function (content) {
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
  linkDownload = `${window.location.href}?id=${id}`.replace(/\?.*?id=/, '?id=');
  let elementLink = document.getElementById("link-download");
  elementLink.href = linkDownload;
  elementLink.textContent = linkDownload;
  return linkDownload
}

function getFiles(id) {
  return fetch(`${URL_API_UPLOAD}/${id}`)
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

function hiddenLoading(action = null) {
  if (action) {
    document.getElementById("modal-action").textContent = action;
  }

  document.getElementById("modal-loading").classList.remove("d-block");
  document.getElementById("modal-loading").classList.add("d-none");
}

function hiddenCardUploadSucess() {
  clipBoard(document.getElementById("link-download").textContent);
  document.getElementById("modal-success").classList.remove("show");
  document.getElementById("modal-success").classList.remove("d-block");
}

function showLoading(action = "upload") {
  if (action) {
    document.getElementById("modal-action").textContent = action;
  }

  document.getElementById("modal-loading").classList.remove("d-none");
  document.getElementById("modal-loading").classList.add("d-block");
}

function showCardUploadSucess() {
  document.getElementById("modal-success").classList.add("show");
  document.getElementById("modal-success").classList.add("d-block");
}

function sendFiles() {
  showLoading();
  return fetch(URL_API_UPLOAD, {
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

async function sendEmail(id) {
  const email = document.getElementById("email-value").value;
  const payload = {subject: "Cloud Share File: Link upload", email: email, content: `Link para download: ${generateLinkDownload(id)}`};
  showLoading("envio do link");
  return fetch(URL_API_EMAIL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
    mode: "cors",
  })
    .then((response) => {
      hiddenLoading();
      if (response.status === 200) {
        return response.json();
      } else {
        console.log('Requisição não bem-sucedida. status:', response.status);
        return false;
      }
    })
    .catch((error) => {
      hiddenLoading();
      console.log(error);
      return false;
    });
}

function showConfig(idElement, alertValue=false){
  if (idElement == "radio-link"){
    document.getElementById("link-upload").textContent = "Obter link";
    document.getElementById("upload-link").classList.remove("d-none");
    document.getElementById("input-email").classList.replace("d-block", "d-none")
    document.getElementById("upload-email").classList.add("d-none");
    document.getElementById("link-download").classList.remove("d-none");
  }

  if (idElement == "radio-email"){
    document.getElementById("link-upload").textContent = "Enviar link";
    document.getElementById("upload-link").classList.add("d-none");
    document.getElementById("input-email").classList.replace("d-none", "d-block")
    document.getElementById("upload-email").classList.remove("d-none");
    document.getElementById("link-download").classList.add("d-none");
  }

  if (alertValue){
    alert(alertValue);
  }
}

function validButtonLinkUpload() {
  let buttonUpload = document.getElementById("link-upload");
  listFiles.length == 0
    ? (buttonUpload.disabled = true)
    : (buttonUpload.disabled = false);
}

function isFileSizeValid(sizeFile) {
  let sizeFileKb = sizeFile / 1024;
  let sizeTotalList = getSizeTotalList();

  return sizeTotalList + sizeFileKb < 1024 * maxUploadMB;
}

function getSizeTotalList() {
  return (
    listFiles.reduce((accumulator, file) => accumulator + file.fileSize, 0) /
    1024
  );
}

function resetPage() {
  clearListTemplate();
  listFiles = [];
  resetInputFile();
  updateCountSize();
}

function resetInputFile() {
  document.getElementById("arquivo").value = "";
  document.getElementById("link-upload").disabled = true;
  document.getElementById("radio-link").checked = true;
}

function redirectPage() {
  const url = new URL(window.location.href);
  window.location.href = `${url.origin}${url.pathname}`;
}

function updateCountSize() {
  let countSize = document.getElementById("count-size");
  let countLength = document.getElementById("count-length");
  countSize.textContent = (getSizeTotalList() / 1024).toFixed(2);
  countLength.textContent = listFiles.length;
}
