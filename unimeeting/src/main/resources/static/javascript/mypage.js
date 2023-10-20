import {makeMeetingBlock} from "./meetingblock.js"

let btn_artical = document.getElementById("mypage_side");
btn_artical.addEventListener("click", sideSelect)
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
    // console.log(meetingObj.list)
    for(let o of meetingObj.list){
      makeMeetingBlock(o)
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
      
      result_div.innerHTML = `
      <form action='/mypage/update' method='post' class='form_group'>
      <input type="hidden" name='idx' value=${idx}>
      <span class='span_text'>아이디</span> <input type='text' name='id' class='input_box' disabled value = '${userObj.user_id}'> <br>
      <span class='span_text'>비밀번호</span> <input class='input_box input_chane_box' type='password' name='password' id='pwd' placeholder = '변경할 비밀번호 입력' required> <br>
      <span class='span_text'>비밀번호 확인</span> <input class='input_box input_chane_box' type='password' name='check_pwd' id='c_pwd' placeholder = '비밀번호 확인' required> <br>
      <span class='span_text'>닉네임</span> <input class='input_box input_chane_box' type='text' name='nickname' placeholder = '변경할 닉네임 입력' required> <br>
      <div class='edit-category'>
          <div class="span-height-div">
              <span class='span_text'>관심 카테고리</span>
          </div>
          <div class="custom-checkbox">
              <label>
                  <input type="checkbox" class="custom-checkbox-input" name="category" value="운동">
                  <div class="custom-checkbox-text">운동</div>
              </label>
              <label>
                  <input type="checkbox" class="custom-checkbox-input" name="category" value="코딩">
                  <div class="custom-checkbox-text">코딩</div>
              </label>
              <label>
                  <input type="checkbox" class="custom-checkbox-input" name="category" value="요리">
                  <div class="custom-checkbox-text">요리</div>
              </label>
              <label>
                  <input type="checkbox" class="custom-checkbox-input" name="category" value="게임">
                  <div class="custom-checkbox-text">게임</div>
              </label>
              <label>
                  <input type="checkbox" class="custom-checkbox-input" name="category" value="애니">
                  <div class="custom-checkbox-text">애니</div>
              </label>
              <label>
                  <input type="checkbox" class="custom-checkbox-input" name="category" value="영화">
                  <div class="custom-checkbox-text">영화</div>
              </label>
          </div>
      </div>
      <span class='span_text'>이메일</span> <input class='input_box' type='text' name='email' disabled value = '${userObj.email}'> <br>
      <span class='span_text'>휴대폰 번호</span> <input class='input_box' type='text' name='phone_num' disabled value = '${userObj.phone_number}'> <br>
      <div class='submit_btn'>
          <input type='submit' value='정 보 변 경' id='submit_button' disabled> </form>
      </div>`;
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
      // console.log(meetingObj);
      let result_div = document.getElementById("info_result");
      result_div.innerHTML = '';

      for(let o of meetingObj.list){
        // console.log(o);
        makeMeetingBlock(o);
      }
    }

    xhr.open('GET', `/mypage/${e.target.name}`, true);
    xhr.send();
  }
}


// var menuItems = document.querySelectorAll(".menu-item");
// var imageUrls = {
//   "side_attend": "./images/home-p.png",
//   "side_create": "./images/directory-p.png",
//   "side_scrap": "./images/tag-p.png",
//   "side_myInfo": "./images/users-p.png",
// };

// var previousImageUrls = {};

// // 각 버튼에 클릭 이벤트 리스너를 추가합니다.
// menuItems.forEach(function(item) {
//   item.addEventListener("click", function() {
//       // 모든 버튼에서 selected 클래스를 제거하고 이전 이미지로 복원합니다.
//       menuItems.forEach(function(otherItem) {
//           otherItem.classList.remove("selected");
//           const button = otherItem.querySelector("button");
//           if (button) {
//             button.classList.remove("selected");
//           }

//           // 복원할 이전 이미지 URL이 있는 경우 원래 이미지로 복원
//           const buttonId = button.id;
//           const img = otherItem.querySelector(".menu-icon");
//           if (previousImageUrls[buttonId] && img) {
//             img.src = previousImageUrls[buttonId];
//           }
//       });

//       // 현재 클릭한 버튼에만 selected 클래스를 추가하고 이미지 URL을 변경합니다.
//       item.classList.add("selected");
//       const button = item.querySelector("button");
//       if (button) {
//           button.classList.add("selected");
//           const buttonId = button.id;
//           const img = item.querySelector(".menu-icon");
//           if (img) {
//               // 현재 이미지 URL을 이전 이미지 URL로 저장하고 변경
//               previousImageUrls[buttonId] = img.src;
//               img.src = imageUrls[buttonId];
//           }
//       }
//   });
// });

var menuItems = document.querySelectorAll(".menu-item");
// 각 버튼에 클릭 이벤트 리스너를 추가합니다.
menuItems.forEach(function(item) {
  item.addEventListener("click", function() {
      // 모든 버튼에서 selected 클래스를 제거합니다.
      menuItems.forEach(function(otherItem) {
          otherItem.classList.remove("selected");
          const button = otherItem.querySelector("button");
          if (button) {
            button.classList.remove("selected");
        }
      });

      // 현재 클릭한 버튼에만 selected 클래스를 추가합니다.
      item.classList.add("selected");
      const button = item.querySelector("button");
      if (button) {
          button.classList.add("selected");
      }
  });
});

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