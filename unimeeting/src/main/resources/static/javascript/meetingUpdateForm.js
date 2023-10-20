let url = new URL(document.referrer);
let idx = new URLSearchParams(url.search);
let xhr = new XMLHttpRequest;
xhr.onload = () => {
    let json = JSON.parse(xhr.responseText);
    //for(let target in document.getElementsByName("category")){
    //    if(target.value)
    //}
    document.getElementsByName("title")[0].defaultValue = json['title'];
    document.getElementsByName("recruits")[0].defaultValue = json['recruits'];
    document.getElementsByName("start_datetime")[0].defaultValue = json['start_datetime'].substring(0, 10);
    document.getElementsByName("content_text")[0].defaultValue = json['content_text'];
    document.getElementsByName("writer_nickname")[0].defaultValue = json['writer_nickname'];
    document.getElementsByName("location")[0].defaultValue = json['location'];
    document.getElementsByName("writer_nickname")[0].defaultValue = json['writer_nickname'];
    document.getElementsByName("idx")[0].defaultValue = json['idx'];
}
xhr.open("GET", `/meeting/getMetJson?meeting_idx=${idx.get("meeting_idx")}`, true);
xhr.send();