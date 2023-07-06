var listFiles = []

document.getElementById("arquivo").addEventListener("change", function () {
  var file = document.getElementById("arquivo").files[0];

  addFile(file);
});

function addFile(file){
    if (!file){
        return;
    }

    var nameFile = file.name;
    var sizeFile = file.size;

    insertListFile(nameFile, sizeFile);

}

function insertListFile(nameFile, sizeFile){
    const file = {name: nameFile, size: sizeFile};
    listFiles.push(file);
    refreshList()
}

function refreshList(){
    let fileList = document.getElementById("list-files");

    //limpa a lista
    var childNodes = Array.from(fileList.childNodes);
    childNodes.forEach((child) => fileList.removeChild(child));

    this.listFiles.forEach((file, index) => {
        var itemList = document.createElement('li');
        var container = document.createElement('div');
        var title = document.createElement('h5');
        var paragraph = document.createElement('p');
        var button = document.createElement('button');

        itemList.classList.add("list-group-item", "d-flex", "justify-content-between");
        container.classList.add("d-flex", "flex-column", "justify-content-center");
        title.classList.add("fs-6");
        paragraph.classList.add("m-0");
        button.classList.add("btn")

        itemList.id=index
        title.innerText = file.name;
        paragraph.innerText = `${file.size} KB`;
        button.innerText = 'x';
        button.addEventListener("click", () => removeFileList(index));

        container.append(title, paragraph);
        itemList.append(container, button);
        fileList.append(itemList);
    });

    function removeFileList(idElement){
        listFiles.splice(idElement, 1);
        refreshList();
    }
}

