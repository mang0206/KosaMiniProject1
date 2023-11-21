<template>
    <nav class="base-header-group">
        <div class="logo">
            <img alt="" src="@/assets/images/meeting.png" class="logo-image">
            <span class="logo-text">UniMeeting</span>
        </div>
        <div id="navbar" class="frame-3" aria-expanded="false">
            <div class="nav_div">
                <router-link class="nav_text" id="nav_main" to="/">메인라운지</router-link>
            </div>
            <div class="nav_div">
                <router-link class="nav_text" to="/meetings">모임</router-link>
            </div>
            <div class="nav_div">
                <router-link class="nav_text" to="/boards/type/free">자유게시판</router-link>
            </div>
            <div class="nav_div">
                <router-link class="nav_text" to="/boards/type/notice" >공지사항</router-link>
            </div>

            <div v-if="user">
                <div class="nav-button">
                    <button class="base-button side-button" onclick="location.href='/userRegister.html'">회원가입</button>
                    <button class="base-button" onclick="location.href='/userLogin.html'">로그인</button>
                </div>
            </div>
            <div v-else="user">
                <div class="nav-button">
                    <button class="base-button  side-button" onclick="location.href='/mypage'">마이페이지</button>
                    <button class="base-button" onclick="location.href='/logout'">로그아웃</button>
                </div>
            </div>
        </div>
    </nav>
</template>

<script setup>
    let user = false;
</script>

<style>
@import '@/assets/css/nav.css';
</style>