    let viewApplicant = (meeting_idx) => {
let xhr = new XMLHttpRequest;
xhr.onload = () => {
    let applicants = JSON.parse(xhr.responseText);
    let offcanvas = document.getElementById("offcanvas-body");
    console.log(applicants);
    // 신청자가 없을 경우
    if(applicants.length == 0){
        offcanvas.innerText = "아직 신청한 사람이 없어요!";
    }else{
        for(let ap in applicants){
            offcanvas.innerHTML += `<div class='card' style='width: 18rem;'>
                                      <div class='card-body'>
                                        <h5 class='card-title'>${ap.nickname}</h5>
                                        <p class='card-text'>${ap.category}</p>`;
            if(ap.accepted == 0){
                offcanvas.innerText +=  `<a href='/apply?meeting_idx=${ap.meeting_idx}&user_idx${ap.user_idx}' class="btn btn-primary">수락</a>
                                        <a href='/apply/delete?meeting_idx=${ap.meeting_idx}&user_idx${ap.user_idx}' class="btn btn-primary">거절</a>
              </div></div>`;
            }else{
                `<a href='/apply/cancel?meeting_idx=${ap.meeting_idx}&user_idx${ap.user_idx}' class="btn btn-primary">수락 취소</a>
              </div></div>`;
            }

        }
    }
};
xhr.open("GET", `/meeting/viewMeetingApplicant?meeting_idx=${meeting_idx}`, true);
}