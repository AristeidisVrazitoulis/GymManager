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
document.getElementById("total-active").innerHTML = totalActive.toString();
