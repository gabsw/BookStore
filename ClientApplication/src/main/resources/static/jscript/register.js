function checkvalue(val)
{
    if(val==="Publisher") {
        document.getElementById('inputName').style.display='block';
        document.getElementById('inputTin').style.display='block'; }

    else{
        document.getElementById('inputName').style.display='none';
        document.getElementById('inputTin').style.display='none'; }
}