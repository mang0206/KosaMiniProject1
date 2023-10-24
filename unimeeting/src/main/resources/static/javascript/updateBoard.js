const url=new URL(document.referrer);
const searchparam = new URLSearchParams(url.search);
let xhr = new XMLHttpRequest();

    xhr.onload = ()=>{
    let update = JSON.parse(xhr.responseText);
    document.getElementById("title").defaultValue=update['title'];
    document.getElementById("content_text").defaultValue=update['content_text'];
    document.getElementById("idx").value=update['idx'];
    };
    xhr.open("GET","board/updateJSON?idx=" + searchparam.get("idx"),true);
    xhr.send();