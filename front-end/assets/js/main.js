var listFiles = [];
var URL_API = "http://localhost:8082/api-ms/upload";

async function addFile(file) {
  if (!file) {
    return;
  }

  let base64Value;

  try {
    const result = await fileToBase64(file);
    base64Value = result.split(",")[1];
  } catch (err) {
    console.log(err);
    return alert(
      "Ocorreu um erro ao realizar o upload do arquivo: " + file.name
    );
  }

  const fileObject = {
    fileName: file.name,
    fileSize: file.size,
    base64: base64Value,
  };

  insertFileList(fileObject);
  refreshList();
}

function clearListTemplate() {
  let fileList = document.getElementById("list-files");
  let childNodes = Array.from(fileList.childNodes);
  childNodes.forEach((child) => fileList.removeChild(child));
}

function insertFileList(fileObject) {
  listFiles.push(fileObject);
}

function refreshList() {
  callListValidations();
  let fileList = document.getElementById("list-files");

  //clear list template
  clearListTemplate();

  this.listFiles.forEach((file, index) => {
    let itemList = document.createElement("li");
    let container = document.createElement("div");
    let title = document.createElement("h5");
    let paragraph = document.createElement("p");
    let button = document.createElement("button");

    itemList.classList.add(
      "list-group-item",
      "d-flex",
      "justify-content-between"
    );
    container.classList.add("d-flex", "flex-column", "justify-content-center");
    title.classList.add("fs-6");
    paragraph.classList.add("m-0");
    button.classList.add("btn");

    itemList.id = index;
    title.textContent = file.fileName;
    paragraph.textContent = `${Math.round(file.fileSize / 1024)} KB`;
    button.textContent = "x";
    button.addEventListener("click", () => removeFileList(index));

    container.append(title, paragraph);
    itemList.append(container, button);
    fileList.append(itemList);
  });
}

function removeFileList(idElement) {
  listFiles.splice(idElement, 1);
  refreshList();
}

async function executeUpload() {
  response = await sendFiles();
  console.log(response);
  if (!response.id) {
    return alert(
      "Ocorreu falha durante o upload dos arquivos, aguarde um momento e tente novamente!"
    );
  }

  resetPage()
  showCardUploadSucess();
  generateLinkDownload(response.id);
}

async function executeDownload(id){
  await downloadFiles(id);
  const url = new URL(window.location.href);
  window.location.href = `${url.origin}${url.pathname}`
}

document.getElementById("arquivo").addEventListener("change", function () {
  var file = document.getElementById("arquivo").files[0];
  addFile(file);
});

document.getElementById("link-upload").addEventListener("click", function () {
  executeUpload();
});

if (hasIdParam("id")) {
  const idValue = new URLSearchParams(window.location.search).get("id");
  executeDownload(idValue);
}
