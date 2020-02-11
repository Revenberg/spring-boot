function getSongs(item)
{
    document.getElementById("resultsBlockVerses").innerHTML = ""

    var btn =  document.getElementById('bundleid');
    btn.value = item.value;

    var url = '/songs/' + item.value;
    $("#resultsBlockSongs").load(url);
 
}
function getVerses(item)
{
    document.getElementById("resultsBlockVerses").innerHTML = ""

    var btn =  document.getElementById('songid');
    btn.value = item.value;

    var url = '/verses/' + item.value;
    $("#resultsBlockVerses").load(url); 
}
