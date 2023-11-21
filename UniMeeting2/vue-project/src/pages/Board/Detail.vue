<template>
    <h1>상세보기</h1>
  <hr />
  <Header></Header>
  <BoardDetail></BoardDetail>
  <hr />
</template>

<script>
// @ is an alias to /src
import BoardDetail from '@/components/BoardDetail.vue';
export default {
  name: 'Board',
  components: {
    BoardDetail
}
}
</script>

<style>
.study {
  color: blue;
}
</style>
