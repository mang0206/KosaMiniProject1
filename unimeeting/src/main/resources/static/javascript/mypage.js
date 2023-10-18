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
console.log(menuItems)
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