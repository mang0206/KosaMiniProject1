<template>
    <h1>공지사항</h1>
  <hr />
  <NoticeList></NoticeList>
</template>

<script>
// @ is an alias to /src
import NoticeList from '@/components/Noticelist.vue'
// import NoticeList from '../../components/NoticeList.vue';

export default {
  components: {
    NoticeList
}
}
</script>

<style>
.study {
  color: blue;
}
</style>
