<template>
    <h1>자유게시판</h1>
  <hr>
  <BoardList></BoardList>
</template>

<script>
// @ is an alias to /src
import BoardList from '@/components/Boardlist.vue'

export default {
  components: {
  BoardList,
  }
}
</script>

<style>
.study {
  color: blue;
}
</style>
