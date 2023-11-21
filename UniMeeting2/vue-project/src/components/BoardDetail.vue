<template>
  <div class="container mt-5">
    <h2 class="mt-3">{{ currentBoard.title }}</h2>
    <div id="content_box" class="mt-3">
      <p class="mt-4"></p>
      <p id="p_1" class="mt-4" v-if="currentBoard.user">
        <strong>작성자:</strong> <span>{{ currentBoard.user.nickname }}</span>
      </p>
      <p id="p_2" class="mt-4">
        <strong>작성일:</strong> <span>{{ currentBoard.createdDatetime }}</span>
      </p>
      <p class="mt-4">{{ currentBoard.content }}</p>s
    </div>

    <router-link @click="deleteCurrentBoard" class="btn btn-danger btn-delete" :to="{ path: `/boards/type/${currentBoard.type}` }" >삭제</router-link
    >
    <router-link class="btn btn-danger btn-delete" :to="{ path: `/updateWrite.html` }" >수정</router-link
    >
    <router-link class="btn btn-danger btn-delete" :to="{ path: `/boards/type/${currentBoard.type}` }" >목록</router-link
    >
  </div>
</template>
<script>//3333
import { useBoardListStore } from "@/stores/boardliststore.js";

export default {
    computed: {
    currentBoard() {
      // Vuex Store에서 현재 게시글의 상세 정보를 가져옵니다.
      return useBoardListStore().currentBoard;
    },
  },
  methods: {
    // 컴포넌트가 마운트될 때 Vuex Store에서 특정 게시글의 상세 정보를 가져오는 메서드 호출
    async fetchBoardDetail() {
      const boardListStore = useBoardListStore();
      await boardListStore.fetchBoardDetail(this.$route.params.idx);
    },
    async deleteCurrentBoard() {
      const boardListStore = useBoardListStore();
      const boardIdx = this.$route.params.idx;

      try {
        // Vuex 스토어를 통해 게시글 삭제
        await boardListStore.deleteBoard(boardIdx);
        
        // 삭제 후 목록 화면으로 이동
        this.$router.push(`/boards/type/${this.currentBoard.type}`);
      } catch (error) {
        console.error('게시글 삭제 동안 오류 발생:', error);
      }
    //   window.location.reload();//삭제하고 목록으로 돌아오면서 깜빡거림 시 발 
    },
  },

  async mounted() {
    // 게시글 상세 정보를 가져옵니다.
    await this.fetchBoardDetail();
  },
};
</script>

<style scoped>
@import "@/assets/css/boardDetail.css";
</style>
