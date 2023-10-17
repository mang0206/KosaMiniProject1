let btn_artical = document.getElementById("mypage_side");
btn_artical.addEventListener("click", sideSelect)
//window.addEventListener("onload", sideSelect(document.getElementById("side_attend")))
window.addEventListener("load", initializePage)

// 처음 접속 시 참여 내역 불러오기
function initializePage() {
  let result_header = document.getElementById("info_header");
  result_header.textContent = "참여 내역";
  let xhr = new XMLHttpRequest();
  xhr.onload = function() {
    let meetingObj = JSON.parse(xhr.responseText);

    let result_div = document.getElementById("info_result");
    result_div.innerHTML = '';

    for(let o of meetingObj.list){
      result_div.innerHTML += `${o.title} ${o.category} ${o.writer_nickname} ${o.content_text}<br>`
    }
  }
  xhr.open('GET', '/mypage/attend', true);
  xhr.send();
}

// side 바에서 선택한 버튼에 대한 AJAX 함수
function sideSelect(e) {
  let result_header = document.getElementById("info_header");
  result_header.textContent = e.target.innerText;
  if(e.target.name=="myInfo"){
    let xhr = new XMLHttpRequest();
    xhr.onload = function() {
      let userObj = JSON.parse(xhr.responseText);
      let result_div = document.getElementById("info_result");
      let idx = result_div.getAttribute('data-suser');

      result_div.innerHTML = '';
      result_div.innerHTML += `<form action='/mypage/update' method='post'>
          <input type="hidden" name='idx' value=${idx}>
          아이디 <input type='text' name='id' disabled value = '${userObj.user_id}'> <br>
          비밀번호 <input type='password' name='password' id='pwd' placeholder = '변경할 비밀번호 입력' required> <br>
          비밀번호 확인 <input type='password' name='check_pwd' id='c_pwd' placeholder = '비밀번호 확인' required> <br>
          닉네임 <input type='text' name='nickname' placeholder = '변경할 닉네임 입력' required> <br>
          관심 카테고리 <br>
          운동 <input type="checkbox" name="category" value="운동">
          스터디 <input type="checkbox" name="category" value="스터디">
          게임 <input type="checkbox" name="category" value="게임"><br>
          카페 <input type="checkbox" name="category" value="카페">
          영화 <input type="checkbox" name="category" value="영화">
          독서 <input type="checkbox" name="category" value="독서"><br>
          이메일 <input type='text' name='email' disabled value = '${userObj.email}'> <br>
          휴대폰 번호 <input type='text' name='phone_num' disabled value = '${userObj.phone_number}'> <br>
          <input type='submit' value='정 보 변 경' id='submit_button' disabled> </form>`;
          //          <input type='submit' value='정 보 변 경'> </form>`;

      document.getElementById('pwd').addEventListener('input', validatePassword);
      document.getElementById('c_pwd').addEventListener('input', validatePassword);

      }
      xhr.open('GET', '/mypage/getSessionData', true);
      xhr.send();
  }
  else if(e.target.nodeName == "BUTTON"){
    let xhr = new XMLHttpRequest();
    xhr.onload = function() {
      let meetingObj = JSON.parse(xhr.responseText);
      console.log(meetingObj);
      let result_div = document.getElementById("info_result");
      result_div.innerHTML = '';

      for(let o of meetingObj.list){
        console.log(o);
        result_div.innerHTML += `${o.title} ${o.category} ${o.writer_nickname} ${o.content_text}<br>`
      }
    }

    xhr.open('GET', `/mypage/${e.target.name}`, true);
    xhr.send();
  }
}

// 비밀번호 유효성 검사 JS
function validatePassword() {
  let submitButton = document.getElementById('submit_button');

  var passwordValue = document.getElementById('pwd').value;
  var checkPwdValue = document.getElementById('c_pwd').value;

  if (passwordValue === checkPwdValue) {
     submitButton.disabled = false;
  } else {
     submitButton.disabled = true;
  }
}