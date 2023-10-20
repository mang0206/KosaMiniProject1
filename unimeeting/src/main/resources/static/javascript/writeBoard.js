window.onload = () => {
    const url = new URL(document.referrer);
    console.log(url.pathname == "/board/notice");
    if (url.pathname == "/board/notice") {
        document.getElementById("type").value='notice';
    } else {
        document.getElementById("type").value='free';
    }
}
