var activeList = document.querySelectorAll(".isactive");

var totalActive = 0;
for(var i = 0; i < activeList.length; i++)
{
    console.log("OK");
    if(activeList[i].innerHTML=="true"){
      activeList[i].innerHTML = "<span style=\"color:green\">Active</span>";
      totalActive++;
    }
    else{
        activeList[i].innerHTML = "<span style=\"color:red\">Inactive</span>";
    }
}
var element = document.getElementById("total-active")
if (element !== null){
    element.innerHTML = totalActive.toString();
}
