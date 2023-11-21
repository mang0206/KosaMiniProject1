<template>
    모임 페이지
    <div>
        <!-- 모임 상세페이지 이동 router -->
        <router-link :to="`meeting/${idx}`">{{ idx }}</router-link>
        <div class="모임 칸" v-for="(key, index) in 5"> <router-link :to="`meeting/${key+60}`">{{ key+60 }}</router-link> </div>
    </div>
</template>

<script setup>
    let idx = 55
</script>