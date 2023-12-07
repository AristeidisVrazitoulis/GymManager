var table = document.getElementById("customersByPlan")
if(table.rows.length == 1){
    var newNode = document.createElement("p");
    newNode.textContent = "No customers subscribed"

    var tableSection = document.getElementById("tableSection")
    var parentElement = tableSection.parentNode
    parentElement.replaceChild(newNode, tableSection)
}