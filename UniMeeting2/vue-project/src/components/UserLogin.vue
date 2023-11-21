<template>
<div th:replace="/nav ::nav"></div>
<div class="container" id="loginApp">
  <div class="row justify-content-center mt-5">
    <div class="col-md-6">
      <div id="id_card" class="card">
        <div class="card-header text-center">
          <h2>로그인</h2>
        </div>
        <div class="card-body">
          <form id="login_form" @submit.prevent="login">
            <div class="form-group">
              <label for="user_id">아이디</label>
              <input v-model="user_id" type="text" class="form-control" id="user_id" name="user_id" placeholder="아이디를 입력해 주세요" required>
            </div>
            <div class="form-group">
              <label for="password">비밀번호</label>
              <input v-model="password" type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력해 주세요" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">로그인</button>
          </form>
          <button id="register_button" class="btn btn-primary btn-block" @click="goToRegister"><span id="register_text">회원가입</span></button>
        </div>
      </div>
    </div>
  </div>
</div>
</template>
<script>
export default{

    data() {
      return{
        user_id: '',
        password: '',

      }
    },
    methods: {
      login() {
        const formData = new FormData();
        formData.append('user_id', this.user_id);
        formData.append('password', this.password);

        fetch('http://localhost:8090/user/login', {
          method: 'POST',
          body: formData,
        })
          .then(response => {
            if (response.ok) {
              // 로그인 성공 시 메인 페이지로
              window.location.href = '/mainPage';
            } else {
              // 로그인 실패 시 오류 메시지를 팝업 창으로 표시
              response.text().then(errorMessage => {
                alert(errorMessage);
              });
            }
          })
          .catch(error => {
            console.error(error);
          });
      },
      goToRegister() {
        location.href = '/userRegister.html';
      },
    },
}

</script>

<style scoped>
@import "@/assets/css/userLogin.css";
</style>