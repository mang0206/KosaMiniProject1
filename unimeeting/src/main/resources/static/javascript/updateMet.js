let was_validated = () => {
console.log("test");
    let form = document.querySelector("#form");
    form.classList.add("was-validated");
};


let url = new URL(document.referrer);
console.log(url);
let searchParams = new URLSearchParams(url.search);
console.log(searchParams.get("meeting_idx"));
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
    document.getElementsByName("location")[0].defaultValue = json['location'];
    document.getElementsByName("writer_nickname")[0].defaultValue = json['writer_nickname'];
    document.getElementsByName("idx")[0].defaultValue = json['idx'];
}
xhr.open("GET", `/meeting/getMetJson?meeting_idx=${searchParams.get("meeting_idx")}`, true);
xhr.send();