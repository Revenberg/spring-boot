function retrieveSongs(p) {
    document.getElementById("resultsBlockVerses").innerHTML = ""
    var bbtn =  document.getElementById('bundleid');    
    bbtn.value = p;

    var url = '/songs/' + p;
    $("#resultsBlockSongs").load(url);

    var btn =  document.getElementById('bundledropbtn');
    btn.innerHTML = document.getElementById("bundle_" + p).innerHTML;
}

function retrieveVerses(p) { 
    document.getElementById("resultsBlockVerses").innerHTML = ""

    var sbtn =  document.getElementById('songid');
    sbtn.value = p;

    var url = '/verses/' + p;
    $("#resultsBlockVerses").load(url);

    var btn =  document.getElementById('songdropbtn');
    btn.innerHTML = document.getElementById("song_" + p).innerHTML;
}